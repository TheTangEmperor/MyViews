package com.demo.myviews.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.demo.myviews.R;

import java.math.BigDecimal;

import androidx.annotation.Nullable;

public class DownloadBtn extends View {
    public DownloadBtn(Context context) {
        super(context);
    }

    public DownloadBtn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mpint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        color = context.getResources().getColor(R.color.detail_downloadbutton_processing);
        finishColor = context.getResources().getColor(R.color.detail_comment_user_level_titel_color);
    }

    public DownloadBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint mpint;
    private Path path;
//    1.默认状态， 2.下载中， 3.下载完成，4.暂停
    private int state = 1;
    private int prog = 0;
//    下载中的color
    private int color = 0x0000;
    private int finishColor = 0x0000;
    private int max = 100;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch(state){
            case 1:
                drawNormal(canvas);
            break;
            case 2:
                drawProgress(canvas);
                drawText(canvas, prog + "%", Color.MAGENTA);
            break;
            case 3:
                drawFinish(canvas);
            break;
            case 4:
                drawProgress(canvas);
                drawText(canvas,  "暂停中", Color.MAGENTA);
                break;
        }
    }

    private void drawFinish(Canvas canvas){
        mpint.setColor(finishColor);
        mpint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.clipPath(path);
        canvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()), 0,0 ,mpint);
        drawText(canvas,  "下载完成", Color.WHITE);
    }

    private void drawProgress(Canvas canvas){
        drawBgLine(canvas);
        mpint.setColor(color);
        mpint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.clipPath(path);
        int width = getWidth();
        BigDecimal b1 = new BigDecimal(prog);

        float toRight = b1.divide(new BigDecimal(max), 2, BigDecimal.ROUND_HALF_UP).floatValue() * width;
        Log.d(TAG, "drawProgress: " + toRight);
        RectF rectF = new RectF(0, 0 , toRight, getHeight());
        canvas.drawRoundRect(rectF, 0,0,mpint);
        if (prog == max){
            setState(3);
        }
    }

    private void drawNormal(Canvas canvas){
        drawBgLine(canvas);

//      绘制圆角内部填充色
        mpint.setStyle(Paint.Style.FILL_AND_STROKE);
        mpint.setColor(Color.BLUE);
        canvas.drawPath(path, mpint);
//        绘制中间文字
        drawText(canvas, "下载", Color.WHITE);
    }

    private void drawText(Canvas canvas, String text, int color){
        int width = getWidth();
        int height = getHeight();

        canvas.save();
        mpint.setTextSize(25);
        mpint.setColor(color);
        int textW = (int) mpint.measureText(text);
        int textH = (int) (mpint.descent() + mpint.ascent());
        canvas.drawText(text,  width / 2 - textW / 2, height / 2 - textH / 2, mpint);
        canvas.restore();
    }

    private void drawBgLine(Canvas canvas){
        //        先绘制最外层边框
        int strokeWidth = 0;
        mpint.setColor(color);
        mpint.setStyle(Paint.Style.STROKE);
        int width = getWidth();
        int height = getHeight();
//        float[] rids = {30.0f, 30.0f, 30.0f, 30.0f, 15.0f, 15.0f, 15.0f, 15.0f};
//        path.addRoundRect(new RectF(0 , 0 , width, height), rids, Path.Direction.CW);



        int arcWidth = height;
//        从0、0点开始
        path.moveTo(0,0);
//        左边圆角的上下左右坐标
        RectF leftRect = new RectF(strokeWidth, strokeWidth, arcWidth - strokeWidth, arcWidth - strokeWidth);
        path.addArc(leftRect, 90, 180);
//        顶部的连接线
        path.lineTo(width - (arcWidth / 2), strokeWidth);
//        右边圆角的上下左右坐标
        RectF rightRect = new RectF(width - arcWidth, strokeWidth, width - strokeWidth, arcWidth - strokeWidth);
        path.addArc(rightRect, -90, 180);
//        底部的连接线
        path.lineTo(arcWidth / 2, height - strokeWidth);
//        绘制圆角边框
        canvas.drawPath(path, mpint);
    }

    public void setState(int newState){
        if (state != newState){
            this.state = newState;
            invalidate();
        }
    }

    private static final String TAG = "DownloadBtn";
    public void setProgress(int newp){
        this.prog = newp;
        Log.d(TAG, "setProgress: " + prog);
        invalidate();
    }

    public void setMax(int nm){
        max = nm;
    }

    public int getState() {
        return state;
    }
}
