package com.demo.myviews;

import android.os.Bundle;
import android.widget.Toast;

import com.demo.myviews.widget.ViewPatternUnlock;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity7 extends AppCompatActivity implements ViewPatternUnlock.OnPatternMakeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        ViewPatternUnlock patternUnlock = (ViewPatternUnlock) findViewById(R.id.patternUnlock);
        patternUnlock.setMakeListener(this);
    }

    @Override
    public void onMakeError(String result) {
        Toast.makeText(this, "手势密码太短：" + result, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMakeSuccess(String result) {
        Toast.makeText(this, "手势绘制成功：" + result, Toast.LENGTH_SHORT).show();

    }
}