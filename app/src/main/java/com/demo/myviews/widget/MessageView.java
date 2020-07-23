package com.demo.myviews.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

public class MessageView extends View {
    public MessageView(Context context) {
        super(context);
    }

    public MessageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint mpaint = new Paint();
    private int sweepAngle = 360;
    private float percent = 0;
    private int prog = 0;
    private int total = 100;
    private int radius = 15;
    private static final String tag = "MessageView";
    private RectF oval = new RectF();
    private Rect textRect = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        mpaint.reset();

        mpaint.setColor(Color.CYAN);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeCap(Paint.Cap.ROUND);
        mpaint.setStrokeJoin(Paint.Join.ROUND);
        mpaint.setAntiAlias(true);
        mpaint.setStrokeWidth(radius);
        Log.d(tag,"==: " + percent * sweepAngle);
//        圆弧的大小 left和top的位置为画笔的宽度，right和bottom为当前view的大小减去画笔的宽度
        oval.setEmpty();
        oval.left = radius;
        oval.top = radius;
        oval.right = getWidth() - radius;
        oval.bottom = getHeight() - radius;
//       画第一个背景圆弧
        canvas.drawArc(oval, 90,  sweepAngle, false, mpaint);
//      画进度圆弧
        mpaint.setColor(Color.RED);
        canvas.drawArc(oval, 90, percent * sweepAngle, false, mpaint);

//        设置进度文本的大小和颜色
        mpaint.reset();
        mpaint.setColor(Color.MAGENTA);
        mpaint.setTextSize(30);
//        mpaint.setTypeface(Typeface.DEFAULT_BOLD);
        textRect.setEmpty();
        String text = String.valueOf(prog);
        mpaint.getTextBounds(text, 0, text.length(), textRect);
//        画进度
        canvas.drawText(text, (getWidth() / 2) - (textRect.width() / 2), (getHeight() / 2) + (textRect.height() / 2), mpaint);


    }

    public synchronized void setProgress(int prog){
        Log.d(tag,"prog: " + prog);
        this.prog  = prog;
        BigDecimal b1 = new BigDecimal(prog);
        BigDecimal b2 = new BigDecimal(total);
        percent = b1.divide(b2, 5, BigDecimal.ROUND_HALF_UP).floatValue();
        Log.d(tag,"per: " + percent);
        invalidate();
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
