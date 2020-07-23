package com.demo.myviews;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.demo.myviews.adapter.LetterAdapter;
import com.demo.myviews.bean.CityData;
import com.demo.myviews.bean.Province;
import com.demo.myviews.widget.LetterIndexView;
import com.google.gson.Gson;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity3 extends AppCompatActivity {


    private TextView tvCurrent;
    private LetterIndexView letterView;
    private RecyclerView rvCityView;
    private String[] mLetters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private List<CityData> list;
    private static final String TAG = "MainActivity3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().hide();
        tvCurrent = findViewById(R.id.tvCurrent);
        letterView = findViewById(R.id.letterView);
        rvCityView = findViewById(R.id.rvCityView);
        letterView.setListener(touchListener);
        list = new ArrayList<>();
        Province province = new Gson().fromJson(getJson(this, "city.json"), Province.class);
        List<Province.DataBean.AreasBean> areas = province.getData().getAreas();
//        1. 获取所有城市的拼音添加到集合中
        for (int i = 0; i < areas.size(); i++) {
            Province.DataBean.AreasBean areasBean = areas.get(i);
            for (Province.DataBean.AreasBean.ChildrenBeanX child : areasBean.getChildren()) {
                list.add(new CityData(2, child.getName(), getPinYin(child.getName())));
            }
        }
//        2.将layouttype为1的索引字母添加进集合
        for (String s : mLetters) {
            list.add(new CityData(1, s, s));
        }
//        3.根据字母进行排序
        Collections.sort(list, new Comparator<CityData>() {
            @Override
            public int compare(CityData s, CityData t1) {
                return s.getLetter().compareTo(t1.getLetter());
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvCityView.setLayoutManager(manager);
        rvCityView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvCityView.setAdapter(new LetterAdapter(list, LayoutInflater.from(this)));

        rvCityView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                Log.d(TAG, "onScrollStateChanged: " + newState);
//                if ()
//            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                 LinearLayoutManager  layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                Log.d(TAG, "onScrolled: " + list.get(visibleItemPosition).getName());
                for (int i = 0; i < mLetters.length; i++) {
                    if ( list.get(visibleItemPosition).getLetter().equals(mLetters[i])){
                        letterView.setCurPosition(i);
                        tvCurrent.setText(mLetters[i]);
                        break;
                    }
                }
            }
        });
    }

    private LetterIndexView.OnLetterTouchListener touchListener = new LetterIndexView.OnLetterTouchListener() {
        @Override
        public void touchChange(int position,String letter, boolean scrolling) {
            tvCurrent.setText(letter);
            int to = 0;
            for (int i = 0; i < list.size(); i++) {
                CityData cityData = list.get(i);
                if (cityData.getLayoutType() == 1 && letter.equals(cityData.getLetter())){
                    to = i;
                    break;
                }
            }
            LinearLayoutManager layoutManager = (LinearLayoutManager) rvCityView.getLayoutManager();
            layoutManager.scrollToPositionWithOffset(to, 0);

        }
    };

    public static StringBuffer sb = new StringBuffer();

    /**
     * 获取汉字字符串的汉语拼音，英文字符不变
     */
    public static String getPinYin(String chines) {
        sb.setLength(0);
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                sb.append(nameChar[i]);
            }
        }
        return sb.toString().toUpperCase();
    }

    //读取方法
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}