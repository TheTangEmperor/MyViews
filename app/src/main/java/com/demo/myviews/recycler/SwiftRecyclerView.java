package com.demo.myviews.recycler;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class SwiftRecyclerView extends RecyclerView {
    public SwiftRecyclerView(@NonNull Context context) {
        super(context);
    }

    public SwiftRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwiftRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private final int REFRESHNORMAL = 1;
    private final int REFRESHING = 2;
    private final int REFRESHPULL = 3;
    private final int REFRESHSTOP = 4;

    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();
    private Adapter headerAdapter;
    private Adapter mAdapter;
    private static final String TAG = "SwiftRecyclerView";
    private RefreshViewProtocol refreshViewSupport;
    private OnRefreshListener onRefreshListener;
    private View refreshView;
    private int refreshViewHeight = -1;
    private int refreshStatus;
    private int actionDownY;
    private int currentMarginTop;
    private float touchScale = 0.35f;

    public void addHeaderView(View view) {
        if (view == null) {
            return;
        }
        mHeaderViews.add(view);
        if (headerAdapter != null && !(headerAdapter instanceof HeaderViewAdapter)) {
            headerAdapter = new HeaderViewAdapter(mHeaderViews, mFooterViews, headerAdapter);
        }
    }

    public void addFooterView(View view) {
        if (view == null) {
            return;
        }
        mFooterViews.add(view);
        if (headerAdapter != null && !(headerAdapter instanceof HeaderViewAdapter)) {
            headerAdapter = new HeaderViewAdapter(mHeaderViews, mFooterViews, headerAdapter);
        }
    }

    public void setRefreshViewSupport(@NonNull RefreshViewProtocol refreshViewProtocol) {
        if (this.refreshViewSupport != null){
            throw new IllegalStateException("refreshViewProtocol is exist");
        }

        View view = refreshViewProtocol.getView(getContext(), this);
        if (view != null) {
            this.refreshViewSupport = refreshViewProtocol;
            this.refreshView = view;
            mHeaderViews.add(0, view);
            if (headerAdapter != null && !(headerAdapter instanceof HeaderViewAdapter)){
                headerAdapter = new HeaderViewAdapter(mHeaderViews, mFooterViews, headerAdapter);
            }
        }
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        Log.d(TAG, "setAdapter: " + mHeaderViews.size() + " footer: " + mFooterViews.size());
        if (mHeaderViews.size() > 0 || mFooterViews.size() > 0) {
            headerAdapter = new HeaderViewAdapter(mHeaderViews, mFooterViews, adapter);
        } else {
            headerAdapter = adapter;
        }
        mAdapter = adapter;
        super.setAdapter(headerAdapter);
    }

    @Nullable
    @Override
    public Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        Log.d(TAG, "onLayout: " + changed);
        if (changed && refreshView != null) {
            refreshViewHeight = refreshView.getMeasuredHeight();
            setRefreshMarginTop(-refreshViewHeight);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        float rawY = e.getRawY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG, "onTouchEvent: " + canScrollVertically(-1) + " refreshStatus: " + refreshStatus);
                if (canScrollVertically(-1) || refreshStatus == REFRESHING || refreshView == null) {
                    return super.onTouchEvent(e);
                }
                refreshStatus = REFRESHPULL;

//                Log.d(TAG, "rawY: " + rawY + " Y: " + e.getY() + " scrolY：" + this.getScrollY());
                int touchHeight = (int) ((rawY - actionDownY) * touchScale);
//                if (touchHeight > refreshViewHeight) {
//                    return super.onTouchEvent(e);
//                }
                int value = touchHeight - refreshViewHeight;
//                Log.d(TAG, "rawY: " + rawY + " actionDownY: " + actionDownY + " touchHeight: " + touchHeight + " value: " + value);
                setRefreshMarginTop(value);
                refreshViewSupport.pull(value, refreshViewHeight, refreshStatus);


                break;
            case MotionEvent.ACTION_DOWN:
                actionDownY = (int) rawY;
                Log.d(TAG, "actionDownY: " + actionDownY);
                break;
            case MotionEvent.ACTION_UP:
                if (refreshViewSupport != null && refreshStatus == REFRESHPULL){
                    Log.d(TAG, "ACTION_UP: " + currentMarginTop + " height: " + refreshViewHeight / 2);
                    refreshStatus = REFRESHING;
//                    小于0代表滑动地距离没有超过整个refreshView的高度
                    if (currentMarginTop < 0){
                        /**
                         *  取当前滑动距离的绝对值看是否已经超过了整个refreshView的二分之一
                         *  超过二分之一则进行正常的刷新操作
                         *  没有超过则还原原貌
                         */
                        if (Math.abs(currentMarginTop) < refreshViewHeight / 2){
                            setRefreshMarginTop(-(refreshViewHeight / 2));
                            refreshViewSupport.refresh();
                            if (onRefreshListener != null) {
                                onRefreshListener.onRefreshing();
                            }
                        }else {
                            restoreRefreshView(currentMarginTop, -refreshViewHeight, false);
                            refreshStatus = REFRESHNORMAL;
                        }

//                        大于0之后代表滑动距离已经超过了整个refreshView的高度了 需要手动将其滑动到二分之一位置
                    }else {
                        setRefreshMarginTop(-(refreshViewHeight / 2));
                        refreshViewSupport.refresh();
                        if (onRefreshListener != null) {
                            onRefreshListener.onRefreshing();
                        }
                    }

                }

                break;

        }

        return super.dispatchTouchEvent(e);
    }



    private void setRefreshMarginTop(int value) {
//        Log.d(TAG, "setRefreshMarginTop: " + value);
        if (value < -refreshViewHeight + 1) {
            value = -refreshViewHeight + 1;
        }
        MarginLayoutParams layoutParams = (MarginLayoutParams) refreshView.getLayoutParams();
        layoutParams.topMargin = value;
        refreshView.setLayoutParams(layoutParams);
        currentMarginTop = value;
    }

    private void restoreRefreshView(float fromValue, float toValue, final boolean callback){
        ValueAnimator animator = ObjectAnimator.ofFloat(fromValue, toValue);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                setRefreshMarginTop((int) animatedValue);
                if (callback){
                    if (animatedValue == -refreshViewHeight){
                        refreshStatus = REFRESHSTOP;
                        refreshViewSupport.closeRefresh();
                        if (onRefreshListener != null) onRefreshListener.closeRefresh();
                    }
                }
            }
        });
        animator.start();
    }


    public void closeRefresh(){
        restoreRefreshView(currentMarginTop, -refreshViewHeight, true);
//        ValueAnimator animator = ObjectAnimator.ofFloat(currentMarginTop, -refreshViewHeight);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float animatedValue = (float) animation.getAnimatedValue();
//                setRefreshMarginTop((int) animatedValue);
//                if (animatedValue == -refreshViewHeight){
//                    refreshStatus = REFRESHSTOP;
//                    refreshViewSupport.closeRefresh();
//                    if (onRefreshListener != null) onRefreshListener.closeRefresh();
//                }
//            }
//        });
//        animator.start();
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public interface OnRefreshListener{
        void onRefreshing();
        void closeRefresh();
    }
}
