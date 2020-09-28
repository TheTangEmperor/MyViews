package com.demo.myviews;

import android.os.Bundle;
import android.widget.Toast;

import com.demo.myviews.widget.SafeEditorTextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity9 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        SafeEditorTextView setPassword = findViewById(R.id.setPassword);
        setPassword.setInputChangeListener(new SafeEditorTextView.OnInputChangeListener() {
            @Override
            public void onFinish(String text) {
                Toast.makeText(MainActivity9.this, "success", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onChange(String text) {

            }
        });
    }
}