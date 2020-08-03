package com.demo.myviews.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.demo.myviews.utils.SizeTool;

public class SlidingMenu extends HorizontalScrollView {
    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        menuWidth = (SizeTool.getSceneWidth(context) / 3) * 2;
        shadowView = new View(getContext());
        shadowView.setBackgroundColor(Color.parseColor("#99000000"));
    }

//    菜单view的宽，占屏幕的三分之二
    private int menuWidth;
//    菜单view
    private View menuView;
//    内容view
    private ViewGroup contentView;
    //    阴影view
    private View shadowView;


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewGroup group = (ViewGroup) this.getChildAt(0);
//        获取左边菜单view
        if (group.getChildCount() > 0){
            menuView = group.getChildAt(0);
        }
        /**
         * 1.获取右边内容view
         * 2.从原布局中移除
         * 3.创建新的contentView
         * 4.将旧的contentview和阴影view添加到新的contentView中
         * 5.重新添加到group中
         */
        if (group.getChildCount() > 1){
            View oldView = group.getChildAt(1);
            group.removeView(oldView);
            contentView = new FrameLayout(getContext());
            contentView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            contentView.addView(oldView);
            contentView.addView(shadowView);
            group.addView(contentView);
        }
//        设置两个view的宽
        menuView.getLayoutParams().width = menuWidth;
        contentView.getLayoutParams().width = SizeTool.getSceneWidth(getContext());
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        根据left值除以菜单宽度计算出阴影显示的明暗度
        float scale = l * 1.0f / menuWidth;
        System.out.println("left: " + l  + " 显示比例： " + scale);
        shadowView.setAlpha(1 - scale);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        如果布局发生了改变 则滚动到contenview完全露出
        if (changed){
            scrollTo(menuWidth, 0);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_UP:
//                获取到滚动的x轴的位置，超过了菜单的一半则打开菜单，否则则关闭菜单
                float x = getScrollX();
                System.out.println("x: " + x + " menuWidth: " + menuWidth);
                if (x > menuWidth / 2){
                    smoothScrollTo(menuWidth, 0);
                }else {
                    smoothScrollTo(0, 0);
                }
                return false;
        }
        return super.onTouchEvent(ev);
    }
}
