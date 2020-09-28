package com.demo.myviews.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;

public class AutoSwitchBanner extends Gallery implements View.OnTouchListener {


    private int lenth = -1;
    private int delayTime = 3000;
    private Timer timer;
    private int layoutId = -1;
    private BannerAdapter mAdapter;
    private ArrayList<String> datas;
    private MyBannerCircle circle;

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

    @Override
    public void setAdapter(SpinnerAdapter adapter) {

    }

    public void setAutoAdapter(BannerAdapter adapter) {
        if (layoutId == -1 || datas == null){
            return;
        }
        circle.setCount(lenth);
        super.setAdapter(adapter);
        setOnItemSelectedListener(itemSelectedListener);
    }

    public void setAutoAdapter(){
        setAutoAdapter(new BannerAdapter());
    }

    public void setDatas(@NonNull ArrayList<String> datas) {
        this.datas = datas;
        lenth = datas.size();
    }

    public void setCircle(MyBannerCircle circle) {
        this.circle = circle;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (circle == null || lenth == -1){
                return;
            }
            int iindex = position % lenth;
            circle.setSelectIndex(iindex);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    class BannerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int position) {
            return "cur: " + position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = LayoutInflater.from(AutoSwitchBanner.this.getContext()).inflate(layoutId, null);
            }
//            TextView lable = convertView.findViewById(R.id.tvFindLable);
//            int iindex = position % lenth;
//            lable.setText("current: " + position + "\r\n" + "%= " + iindex);
            return convertView;
        }
    }




}
