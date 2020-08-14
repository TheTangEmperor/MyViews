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
    private Adapter mAdapter;
    private static final String TAG = "SwiftRecyclerView";
    private RefreshViewProtocol refreshViewSupport;
    private View refreshView;
    private int refreshViewHeight = -1;
    private int refreshStatus;
    private int actionDownY;
    private int currentMarginTop;

    public void addHeaderView(View view) {
        if (view == null) {
            return;
        }
        mHeaderViews.add(view);
        if (mAdapter != null && !(mAdapter instanceof HeaderViewAdapter)) {
            mAdapter = new HeaderViewAdapter(mHeaderViews, mFooterViews, mAdapter);
        }
    }

    public void addFooterView(View view) {
        if (view == null) {
            return;
        }
        mFooterViews.add(view);
        if (mAdapter != null && !(mAdapter instanceof HeaderViewAdapter)) {
            mAdapter = new HeaderViewAdapter(mHeaderViews, mFooterViews, mAdapter);
        }
    }

    public void setRefreshViewSupport(RefreshViewProtocol refreshViewProtocol) {
        this.refreshViewSupport = refreshViewProtocol;
        View view = refreshViewSupport.getView(getContext(), this);
        if (view != null) {
            this.refreshView = view;
            addHeaderView(refreshView);
        }
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        Log.d(TAG, "setAdapter: " + mHeaderViews.size() + " footer: " + mFooterViews.size());
        if (mHeaderViews.size() > 0 || mFooterViews.size() > 0) {
            mAdapter = new HeaderViewAdapter(mHeaderViews, mFooterViews, adapter);
        } else {
            mAdapter = adapter;
        }
        super.setAdapter(mAdapter);
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
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: " + canScrollVertically(-1));
                if (canScrollVertically(-1) || refreshStatus == REFRESHING) {
                    return super.onTouchEvent(e);
                }
                refreshStatus = REFRESHPULL;
                float rawY = e.getRawY();
//                Log.d(TAG, "rawY: " + rawY + " Y: " + e.getY() + " scrolY：" + this.getScrollY());
                int touchHeight = (int) ((rawY - actionDownY) * 0.38);
                if (touchHeight > refreshViewHeight) {
                    return super.onTouchEvent(e);
                }
                int value = touchHeight - refreshViewHeight;
                Log.d(TAG, "rawY: " + rawY + " actionDownY: " + actionDownY + " touchHeight: " + touchHeight + " value: " + value);
                setRefreshMarginTop(value);
                refreshViewSupport.pull(value, refreshViewHeight, refreshStatus);


                break;
            case MotionEvent.ACTION_DOWN:
                actionDownY = (int) e.getRawY();
//                    Log.d(TAG, "actionDownY: " + actionDownY);
                break;
            case MotionEvent.ACTION_UP:
                    Log.d(TAG, "ACTION_UP: " + Math.abs(currentMarginTop) + " height: " + refreshViewHeight / 2);
                if (Math.abs(currentMarginTop) < refreshViewHeight / 2){
                    refreshStatus = REFRESHING;
                    refreshViewSupport.refresh();
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            update();
                        }
                    }, 1000);
                }

                break;

        }


        return super.onTouchEvent(e);
    }

    private void update(){
        ValueAnimator animator = ObjectAnimator.ofFloat(currentMarginTop, -refreshViewHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
//                            Log.d(TAG, "onAnimationUpdate: " + animatedValue);
                setRefreshMarginTop((int) animatedValue);
                if (animatedValue == -refreshViewHeight){
                    refreshStatus = REFRESHSTOP;
                    refreshViewSupport.closeRefresh();
                }
            }
        });
        animator.start();
    }


    private void setRefreshMarginTop(int value) {
        Log.d(TAG, "setRefreshMarginTop: " + value);
        if (value < -refreshViewHeight + 1) {
            value = -refreshViewHeight + 1;
        }
        MarginLayoutParams layoutParams = (MarginLayoutParams) refreshView.getLayoutParams();
        layoutParams.topMargin = value;
        refreshView.setLayoutParams(layoutParams);
        currentMarginTop = value;
    }


}
