package com.demo.myviews.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.demo.myviews.R;

import androidx.annotation.Nullable;

public class LetterIndexView extends View {

    public interface OnLetterTouchListener{
        void touchChange(int position, String letter, boolean scrolling);
    }



    public LetterIndexView(Context context) {
        super(context);
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LetterIndexView);
        textSize = ta.getDimension(R.styleable.LetterIndexView_textSize, 25);
        strokeWidth = ta.getDimension(R.styleable.LetterIndexView_strokeWidth, 5);
        textColor = ta.getColor(R.styleable.LetterIndexView_textColor, Color.BLACK);
        ta.recycle();
        mpint.setStyle(Paint.Style.STROKE);
        mpint.setColor(textColor);
        mpint.setTextSize(textSize);
        mpint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private String[] mLetters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private Paint mpint = new Paint();
    private float textSize = 25;
    private int textColor = 0x0000;
    private float strokeWidth = 5;
    private static final String TAG = "LetterIndexView";
    private int curPosition;
    private OnLetterTouchListener listener;


    public void setListener(OnLetterTouchListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int textHeight = getHeight() / mLetters.length;
        int totalWidth = getWidth();
        Log.d(TAG, "textHeight: " + textHeight);
        Log.d(TAG, "totalWidth: " + totalWidth);
        for (int i = 0; i < mLetters.length; i++) {
            String text = mLetters[i];
            float textW = mpint.measureText(text);
            int textH = (int) (mpint.descent() + mpint.ascent());
            int centerH = textHeight / 2 - textH / 2;
            int curH = textHeight * i;
            if (i == 0){
                curH = centerH;
            }else {
                curH += centerH;
            }
            if (curPosition == i){
                mpint.setColor(Color.RED);
            }else {
                mpint.setColor(textColor);
            }
            canvas.drawText(text, totalWidth / 2 - textW / 2, curH, mpint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float curY = event.getY();
                int singH = getHeight() / mLetters.length;
                int index = (int) (curY / singH);
                Log.d(TAG, "curY: " + curY + "index: " + index);
                if (index != curPosition && curY > 0){
                    curPosition = index;
                    if (listener != null) listener.touchChange(curPosition, mLetters[curPosition],true);
                }else{
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (listener != null){
                    listener.touchChange(curPosition, mLetters[curPosition],false);
                }
            break;
        }
        invalidate();

        return true;
    }

    public void setCurPosition(int curPosition) {
        this.curPosition = curPosition;
        invalidate();
    }
}
