package com.demo.myviews.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.jetbrains.annotations.Nullable;

public class MyBannerCircle extends View {


    private int radius = 10;
    private int count = 2;
    private int space = 10;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int selectIndex = 0;
    private static final String tag = "MyBannerCircle";

    public MyBannerCircle(Context context) {
        super(context);
    }

    public MyBannerCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBannerCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int totalWidth = ((radius * 2) * count) + (space * (count - 1));
        mPaint.setColor(Color.LTGRAY);
        int width = getWidth();
        int left = width / 2 - totalWidth / 2;
            Log.d(tag, "selectIndex: " + selectIndex);
        for (int i = 0; i < count; i++) {
            if (i == selectIndex){
                mPaint.setColor(Color.RED);
            }else{
                mPaint.setColor(Color.LTGRAY);
            }
            if (i == 0){
                canvas.drawCircle(left, radius, radius, mPaint);
            }else {
                canvas.drawCircle(left + (radius * 2 * i) + (space * i), radius, radius, mPaint);
            }
        }

    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public void setCount(int count) {
        this.count = count;
        invalidate();
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        invalidate();
    }
}
