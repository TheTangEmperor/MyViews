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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import androidx.annotation.Nullable;

public class ViewPatternUnlock extends View {
    public ViewPatternUnlock(Context context) {
        super(context);
    }

    public ViewPatternUnlock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);

    }

    public ViewPatternUnlock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private List<Point> circles = new ArrayList<>();
    private int lineCount = 3;
    private int radius;
    private static final String TAG = "ViewPatternUnlock";
    private Paint mPaint;
    private LinkedHashSet<Integer> selectCircles = new LinkedHashSet<>();
    private Point motionPoint;
    private boolean isError = false;
    private OnPatternMakeListener makeListener;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        始终都使用最小值来进行半径取值
        int measuredWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        radius = measuredWidth / (lineCount * 3 + 1);
        circles.clear();
        Log.d(TAG, "radius: " + radius);
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < lineCount; j++) {
                circles.add(new Point(radius * (3 * j + 2), radius * (3 * i + 2)));
//                Log.d(TAG, "onMeasure: " + i + " J: " + j);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBigAndSmall(canvas);
        drawConnectLine(canvas);
    }

    void drawBigAndSmall(Canvas canvas) {
//        Log.d(TAG, "drawBigAndSmall: ----------------" + selectCircles.size());
        for (int i = 0; i < circles.size(); i++) {
            Point point = circles.get(i);
            mPaint.setColor(Color.LTGRAY);
            canvas.drawCircle(point.x, point.y, radius, mPaint);
            mPaint.setColor(Color.DKGRAY);
            canvas.drawCircle(point.x, point.y, radius / 4, mPaint);
        }
        Iterator<Integer> iterator = selectCircles.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            Point point = circles.get(next);
            if (isError){
                mPaint.setColor(Color.RED);
            }else {
                mPaint.setColor(Color.BLUE);
            }
            canvas.drawCircle(point.x, point.y, radius, mPaint);
            canvas.drawCircle(point.x, point.y, radius / 4, mPaint);
        }

    }

    void drawConnectLine(Canvas canvas){
        Iterator<Integer> iterator = selectCircles.iterator();
        Point currentPoint = null;
        if (isError){
            mPaint.setColor(Color.RED);
        }else {
            mPaint.setColor(Color.GREEN);
        }

        while (iterator.hasNext()){
            Integer next = iterator.next();
            if (currentPoint == null){
                currentPoint = circles.get(next);
            }else {
                Point toPoint = circles.get(next);
                canvas.drawLine(currentPoint.x, currentPoint.y, toPoint.x, toPoint.y,mPaint);
                currentPoint = toPoint;
            }
        }
        if (motionPoint != null && currentPoint != null){
            canvas.drawLine(currentPoint.x, currentPoint.y, motionPoint.x, motionPoint.y, mPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int moveX = (int) event.getX();
        int moveY = (int) event.getY();


        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP: -------------");
                if (selectCircles.size() < 1)
                    return true;
                Iterator<Integer> iterator = selectCircles.iterator();
                String result = "";
                while (iterator.hasNext()){
                    result = result + iterator.next();
                }
//                小于4组表示太短 提示错误
                if (selectCircles.size() < 5){

                    makeError(result);
                }else {
                    makeSuccess(result);
                }

                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                motionPoint = new Point(moveX, moveY);
                addSelectPosition(moveX, moveY);
                invalidate();
            break;
        }


        return true;
    }

    void makeError(String result){
        isError = true;
        invalidate();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                selectCircles.clear();
                motionPoint = null;
                isError = false;
                invalidate();
            }
        },500);
        if (makeListener != null)makeListener.onMakeError(result);
    }

    void  makeSuccess(String result){
        selectCircles.clear();
        motionPoint = null;
        invalidate();
        if (makeListener != null)makeListener.onMakeSuccess(result);
    }

    void addSelectPosition(int xMove, int yMove){
//        Log.d(TAG, "onTouchEvent: ------------------------");
        for (int i = 0; i < circles.size(); i++) {
            Point point = circles.get(i);
//            Log.d(TAG, "onTouchEvent: " + point.toString() + " index: " + i + " moveX: " + xMove + " moveY: " + yMove);
            float left = Math.abs(xMove - point.x);
            float top = Math.abs(yMove - point.y);
//            Log.d(TAG, "onTouchEvent: left: " + left + "  top: " + top + " radius: " + radius);
            if (left < radius && top < radius){
//                Log.d(TAG, "onTouchEvent:  " + i);
                selectCircles.add(i);
            }
        }

    }

    public void setMakeListener(OnPatternMakeListener makeListener) {
        this.makeListener = makeListener;
    }

    public interface OnPatternMakeListener {
        void onMakeError(String result);
        void onMakeSuccess(String result);
    }
}
