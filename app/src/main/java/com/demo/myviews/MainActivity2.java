package com.demo.myviews;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.demo.myviews.widget.DownloadBtn;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private DownloadBtn btnDownload;
    int progress = 0;
    Random random = new Random();
    int total = 100;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (progress < total){
                progress += random.nextInt(5);
                if (progress > total){
                    progress = total;
                }
                btnDownload.setProgress(progress);
                handler.sendEmptyMessageDelayed(1, 300);
            }else {
                this.removeMessages(1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnDownload = findViewById(R.id.btnDownload);
        findViewById(R.id.btnReload).setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        btnDownload.setMax(total);
    }

    private static final String TAG = "MainActivity2";
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnReload){
            btnDownload.setState(1);
            progress = 0;
            btnDownload.setProgress(progress);
            handler.removeMessages(1);
        }else {
            int state = btnDownload.getState();
            if (state == 2){
                btnDownload.setState(4);
                handler.removeMessages(1);
            }else if (state == 1 || state == 4){
                btnDownload.setState(2);
                handler.sendEmptyMessageDelayed(1, 300);
            }
            Log.d(TAG, "onClick: ");

        }

    }
}