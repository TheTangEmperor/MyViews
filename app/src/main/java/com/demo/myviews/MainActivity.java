package com.demo.myviews;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.demo.myviews.widget.MessageView;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private MessageView mv ;
    private int count = 0;
    private int tatol = 3219;
    private Random random = new Random();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (count < tatol){
                count = count + random.nextInt(300);
                if (count > tatol){
                    count = tatol;
                }
                mv.setProgress(count);
                this.sendEmptyMessageDelayed(1, 100);
            }else{
                this.removeMessages(1);
            }
        }
    };

    private EditText etTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mv = findViewById(R.id.mv);
        mv.setTotal(tatol);
        handler.sendEmptyMessageDelayed(1,100);
        findViewById(R.id.btnReload).setOnClickListener(this);
        etTotal = findViewById(R.id.etTotal);
    }

    @Override
    public void onClick(View view) {
        String text = etTotal.getText().toString();
        if (text != null && !text.isEmpty()){
            tatol =  Integer.parseInt(text);
            count = 0;
            mv.setTotal(tatol);
            handler.sendEmptyMessageDelayed(1,100);
        }
    }
}