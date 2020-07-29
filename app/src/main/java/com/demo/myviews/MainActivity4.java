package com.demo.myviews;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.myviews.widget.TextColorChangeView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity4 extends AppCompatActivity {


    private ViewPager vpPage;
    private LinearLayout llTabContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main4);
        vpPage = findViewById(R.id.vpPage);
        llTabContainer = findViewById(R.id.llTabContainer);
        setData();


    }


    void setData(){
        final List<TextView> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            TextView textView = new TextView(this);
            textView.setText("page: " + i);
            textView.setTextSize(38);
            textView.setTextColor(Color.BLUE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            params.leftMargin = 100;
            params.topMargin = 200;
            textView.setLayoutParams(params);
            list.add(textView);
        }


        vpPage.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(list.get(position));

                return list.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        vpPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("position: " + position);
                System.out.println("positionOffset: " + positionOffset);
                System.out.println("positionOffsetPixels: " + positionOffsetPixels);
                TextColorChangeView cur = (TextColorChangeView) llTabContainer.getChildAt(position);
                cur.setProgress(positionOffset);

                TextColorChangeView next = (TextColorChangeView) llTabContainer.getChildAt(position + 1);
                next.setProgress(positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TextColorChangeView childAt = (TextColorChangeView) llTabContainer.getChildAt(0);
        childAt.setProgress(1.0f);
    }
}