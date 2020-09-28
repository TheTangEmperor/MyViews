package com.demo.myviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onClick(View v) {
        int tag = Integer.parseInt(v.getTag().toString());
        System.out.println("tag----->: " + tag);
        switch (tag) {
            case 1:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, MainActivity2.class));
                break;
            case 3:
                startActivity(new Intent(this, MainActivity3.class));
                break;
            case 4:
                startActivity(new Intent(this, MainActivity4.class));
                break;
            case 5:
                startActivity(new Intent(this, MainActivity5.class));
                break;
            case 6:
                startActivity(new Intent(this, MainActivity6.class));
                break;
            case 7:
                startActivity(new Intent(this, MainActivity7.class));
                break;
            case 8:
                startActivity(new Intent(this, MainActivity8.class));
                break;
            case 9:
                startActivity(new Intent(this, MainActivity9.class));
                break;
            case 10:
                startActivity(new Intent(this, MainActivity10.class));
                break;
        }
    }
}