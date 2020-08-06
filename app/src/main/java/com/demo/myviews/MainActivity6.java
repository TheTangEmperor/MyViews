package com.demo.myviews;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.demo.myviews.fragments.RecommentFragment;
import com.demo.myviews.fragments.VideoFragment;
import com.demo.myviews.widget.TextColorChangeView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity6 extends AppCompatActivity {

    private ViewPager vpBody;
    private RelativeLayout rlNavigationBar;
    private LinearLayout llTitleContainer;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main6);
        vpBody = findViewById(R.id.vpBody);
        rlNavigationBar = findViewById(R.id.rlNavigationBar);
        llTitleContainer = findViewById(R.id.llTitleContainer);
        fragmentList = new ArrayList<>();
        fragmentList.add(RecommentFragment.newInstance(1));
        fragmentList.add(new VideoFragment());
        vpBody.setAdapter(new BodyPageAdater(getSupportFragmentManager()));


        vpBody.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                TextColorChangeView one = (TextColorChangeView) llTitleContainer.getChildAt(position);
                one.setAfter(false);
                one.setProgress(1-positionOffset);

                TextColorChangeView two = (TextColorChangeView) llTitleContainer.getChildAt(position + 1);
                if (two != null){
                    two.setAfter(true);
                    two.setProgress(positionOffset);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class BodyPageAdater extends FragmentPagerAdapter{

        public BodyPageAdater(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}