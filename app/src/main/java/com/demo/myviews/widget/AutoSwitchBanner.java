package com.demo.myviews.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;

import java.util.Timer;
import java.util.TimerTask;

public class AutoSwitchBanner extends Gallery implements View.OnTouchListener {


    private int lenth;
    private int delayTime = 3000;
    private Timer timer;

    public AutoSwitchBanner(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public AutoSwitchBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public AutoSwitchBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    public void setLenth(int lenth) {
        this.lenth = lenth;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                stopTimer();
            break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                startTimer();
            break;
        }
        return false;
    }

    public void startTimer(){
        if (lenth > 0 && timer == null){
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (lenth > 0){
                        post(new Runnable() {
                            @Override
                            public void run() {
//                                JLog.d("right");
                                onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                            }
                        });

                    }
                }
            }, delayTime, delayTime);
        }

    }

    public void stopTimer(){
        if (timer != null){
            timer.cancel();
        }
        timer = null;
    }



}
