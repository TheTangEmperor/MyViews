package com.demo.myviews.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import androidx.annotation.Nullable;

public class ViewPatternUnlock extends View {
    public ViewPatternUnlock(Context context) {
        super(context);
    }

    public ViewPatternUnlock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bigPaint = new Paint();
        bigPaint.setAntiAlias(true);
        bigPaint.setStrokeWidth(5);
        bigPaint.setStyle(Paint.Style.STROKE);
        bigPaint.setColor(Color.BLUE);

        smallPaint = new Paint();
        smallPaint.setColor(Color.RED);
        smallPaint.setStrokeWidth(5);
        smallPaint.setStyle(Paint.Style.STROKE);
        smallPaint.setAntiAlias(true);
    }

    public ViewPatternUnlock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private List<Point> circles = new ArrayList<>();
    private int lineCount = 3;
    private int radius;
    private static final String TAG = "ViewPatternUnlock";
    private Paint bigPaint;
    private Paint smallPaint;
    private LinkedHashSet<Integer> selectCircles = new LinkedHashSet<>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        始终都使用最小值来进行半径取值
        int measuredWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        radius = measuredWidth / (lineCount * 3 + 1);
        Log.d(TAG, "radius: " + radius);
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < lineCount; j++) {
                circles.add(new Point(radius * (3 * i + 2), radius * (3 * j + 2)));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBigAndSmall(canvas);

    }

    void drawBigAndSmall(Canvas canvas) {
        Log.d(TAG, "drawAll_Cicle: ----------------------------------");
        for (int i = 0; i < circles.size(); i++) {
            Point point = circles.get(i);
            canvas.drawCircle(point.x, point.y, radius, bigPaint);
//            Log.d(TAG, "x: " + point.x + " y: " + point.y);
            canvas.drawCircle(point.x, point.y, radius / 4, smallPaint);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float moveX = event.getX();
        float moveY = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_UP:

            break;
            case MotionEvent.ACTION_MOVE:
                addSelectPosition(moveX, moveY);
            break;
        }


        return true;
    }

    void addSelectPosition(float xMove, float yMove){
        for (int i = 0; i < circles.size(); i++) {
            Point point = circles.get(i);
                Log.d(TAG, "moveX: " + xMove + " moveY: " + yMove + " x: " + point.x + " y: " + point.y);
            if (point.x > xMove && point.y > yMove){
                selectCircles.add(i);
                Log.d(TAG, "addSelectPosition: " + i);
                break;
            }
        }

    }
}
