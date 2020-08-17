package com.demo.myviews.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.demo.myviews.R;

public class RefreshViewSupport implements RefreshViewProtocol{

    private View rotateView;
    private TextView tvRefreshTip;
    private  final String TAG = "SwiftRecyclerView";

    @Override
    public View getView(Context context, ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.refresh_header, parent, false);
        rotateView = inflate.findViewById(R.id.rotateView);
        tvRefreshTip = inflate.findViewById(R.id.tvRefreshTip);
        return inflate;
    }

    @Override
    public void pull(int touchHeight, int viewHeight, int touchStatus) {
        float scale = ((float)touchHeight) / viewHeight;
//        Log.d(TAG, "pull: " + scale);
        rotateView.setRotation(scale * 360);
        if (Math.abs(scale) <= 0.5){
            tvRefreshTip.setText("松开刷新");
        }else {
            tvRefreshTip.setText("下拉刷新");
        }
    }

    @Override
    public void refresh() {
        Log.d(TAG, "refresh: ");
//        RotateAnimation animator = new RotateAnimation(0, 360, 0.5f,0.5f);
        // 刷新的时候不断旋转
        RotateAnimation animator = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animator.setDuration(1200);
        animator.setRepeatCount(-1);
        rotateView.startAnimation(animator);

    }

    @Override
    public void closeRefresh() {
        Log.d(TAG, "closeRefresh: ");
        rotateView.clearAnimation();
    }
}
