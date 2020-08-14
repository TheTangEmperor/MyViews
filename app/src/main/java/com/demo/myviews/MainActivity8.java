package com.demo.myviews;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.demo.myviews.recycler.BaseRecyclerViewAdapter;
import com.demo.myviews.recycler.BaseViewHolder;
import com.demo.myviews.recycler.MoreTypeProtocol;
import com.demo.myviews.recycler.RefreshViewSupport;
import com.demo.myviews.recycler.SwiftRecyclerView;
import com.demo.myviews.taptap.fragments.dummy.DummyContent;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity8 extends AppCompatActivity implements MoreTypeProtocol {

    private static final String TAG = "MainActivity8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        SwiftRecyclerView recyTest = findViewById(R.id.recyTest);
        recyTest.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        ThereAdapter adapter = new ThereAdapter(DummyContent.ITEMS, this, this);
//        View header = LayoutInflater.from(this).inflate(R.layout.banner_header,null);
//        recyTest.addHeaderView(header);
        recyTest.setRefreshViewSupport(new RefreshViewSupport());

        View footer = LayoutInflater.from(this).inflate(R.layout.banner_header,null);
        recyTest.addFooterView(footer);
        recyTest.setAdapter(adapter);


    }
    @Override
    public int getLayoutId(int position) {
//        Log.d(TAG, "getLayoutId: ");
        if (position % 2 == 0){
            return R.layout.fragment_recomment2;
        }
        return R.layout.fragment_recomment;
    }

    class ThereAdapter extends BaseRecyclerViewAdapter<DummyContent.DummyItem> {

        public ThereAdapter(List<DummyContent.DummyItem> datas, Context context) {
            super(R.layout.fragment_recomment, datas, context);
        }

        public ThereAdapter(List<DummyContent.DummyItem> datas, MoreTypeProtocol typeSupport, Context context) {
            super(datas, typeSupport, context);
        }

        @Override
        public void convert(BaseViewHolder holder, DummyContent.DummyItem item, int position) {
//            Log.d(TAG, "convert: " + item.content);
            if (position % 2 == 0){
                holder.setText(R.id.item_time, "title: " + item.id);
            }else {
                holder.setText(R.id.item_number, item.id);
                holder.setText(R.id.content, item.content);
            }

        }


    }
}