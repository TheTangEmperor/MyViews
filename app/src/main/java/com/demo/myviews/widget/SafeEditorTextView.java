package com.demo.myviews.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.demo.myviews.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatEditText;

public class SafeEditorTextView extends AppCompatEditText {
    public SafeEditorTextView(Context context) {
        super(context);
    }

    public SafeEditorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SafeEditorTextView);
        mLength = array.getInt(R.styleable.SafeEditorTextView_lenth, 6);
        strokeWidth = array.getDimensionPixelOffset(R.styleable.SafeEditorTextView_strokeWidth, 3);
        space = array.getDimensionPixelOffset(R.styleable.SafeEditorTextView_space, 30);
        array.recycle();

        mSide = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSide.setStyle(Paint.Style.STROKE);
        mSide.setStrokeWidth(strokeWidth);

        mCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircle.setStyle(Paint.Style.FILL);
        mCircle.setColor(Color.BLACK);


        mLine = new Paint();
        mLine.setColor(Color.BLACK);
        mLine.setStyle(Paint.Style.FILL_AND_STROKE);
        mLine.setStrokeWidth(strokeWidth);

        setTextColor(Color.TRANSPARENT);
        setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        setBackground(null);
        setLongClickable(false);
        setTextIsSelectable(false);
        setCursorVisible(false);
        Log.d(TAG, "SafeEditorTextView: ");
    }
//    边框画笔
    private Paint mSide;
//    圆点画笔
    private Paint mCircle;
//    线画笔
    private Paint mLine;
//    用户输入的密码
    private String mText = "";
//    最长长度
    private int mLength = 6;
//    间距
    private int space = 30;
//    边框圆角度数
    private int round = 5;
//    线条宽度
    private int strokeWidth = 3;
//    存储边框临界值的容器
    private List<RectF> rectFList ;
    private static final String TAG = "SafeEditorTextView";
//    输入变化的监听器
    private OnInputChangeListener changeListener;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (rectFList == null){
            rectFList = new ArrayList<>();
            int height = getMeasuredHeight();
            int width = getMeasuredWidth();
            Log.d(TAG, "onMeasure: " + width + " height: " + height);
            int singleWidth = (width - (mLength - 1) * space) / mLength;
            for (int i = 0; i < mLength; i++) {
                int left = i == 0 ? i * singleWidth + strokeWidth: i * singleWidth + i * space;
                RectF rectF = new RectF(left, strokeWidth, i * singleWidth + singleWidth + i * space, height - strokeWidth);
                rectFList.add(rectF);

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: " + mText.length());
        for (int i = 0; i < mLength; i++) {
            RectF rectF = rectFList.get(i);
//            画需要高亮的边框
            if (i <= mText.length()){
                mSide.setColor(Color.BLACK);
            }else {
                mSide.setColor(Color.LTGRAY);
            }
            canvas.drawRoundRect(rectF, round,round, mSide);

            float singleWidth = rectF.right - rectF.left;
            float singleHeight = rectF.bottom - rectF.top;
//            画当前编辑的横线
            if (i == mText.length()){
                float lineScale = singleWidth / 4;
                float startY = rectF.top  + singleHeight / 4 * 3;
                canvas.drawLine(rectF.left + lineScale, startY, rectF.right - lineScale, startY, mLine);
            }
//            将已经输入的文字用圆代替
            if (i < mText.length()){

                canvas.drawCircle(rectF.left + singleWidth / 2, rectF.bottom - singleHeight / 2, singleWidth / 6, mCircle);
            }
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        Log.d(TAG, "onTextChanged: " + text);
        if (text.length() <= mLength){
            mText = text.toString();
        }

        if (text.length() == mLength){
            hideKeyboard();
            if (changeListener != null) changeListener.onFinish(mText);
        }
    }

    //隐藏软键盘并让editText失去焦点
    private void hideKeyboard() {
        clearFocus();
        //这里先获取InputMethodManager再调用他的方法来关闭软键盘
        //InputMethodManager就是一个管理窗口输入的manager
        InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null) {
            im.hideSoftInputFromWindow(getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void setInputChangeListener(OnInputChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface OnInputChangeListener{
        void onFinish(String text);
        void onChange(String text);
    }
}
