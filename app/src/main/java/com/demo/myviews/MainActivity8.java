package com.demo.myviews;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.demo.myviews.recycler.BaseRecyclerViewAdapter;
import com.demo.myviews.recycler.BaseViewHolder;
import com.demo.myviews.recycler.MoreTypeProtocol;
import com.demo.myviews.recycler.RefreshViewSupport;
import com.demo.myviews.recycler.SwiftRecyclerView;
import com.demo.myviews.taptap.fragments.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity8 extends AppCompatActivity implements MoreTypeProtocol {

    private static final String TAG = "MainActivity8";
    private SwiftRecyclerView recyTest;
    SwiftRecyclerView.OnRefreshListener refreshListener = new SwiftRecyclerView.OnRefreshListener() {
        @Override
        public void onRefreshing() {
            recyTest.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyTest.closeRefresh();
                    List<DummyContent.DummyItem> items = new ArrayList<>();
                    int count = recyTest.getAdapter().getItemCount();
                    for (int i = 0; i < count + 3; i++) {
                        int index = i;
                        items.add(new DummyContent.DummyItem("" + index, "item " + index, "item " + index));

                    }
                    ThereAdapter adapter = (ThereAdapter) recyTest.getAdapter();
                    Log.d(TAG, "run: " + adapter);
                    adapter.setDatas(items);
//                    items.add(new DummyContent.DummyItem("27", "item 27", "item 27"));
                }
            }, 4000);
        }

        @Override
        public void closeRefresh() {
            Toast.makeText(MainActivity8.this, "on success", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        recyTest = findViewById(R.id.recyTest);
        recyTest.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        ThereAdapter adapter = new ThereAdapter(DummyContent.ITEMS, this, this);
        View header = LayoutInflater.from(this).inflate(R.layout.banner_header,null);
        recyTest.addHeaderView(header);
        View header2 = LayoutInflater.from(this).inflate(R.layout.banner_header,null);
        recyTest.addHeaderView(header2);


        recyTest.setOnRefreshListener(refreshListener);
        View footer = LayoutInflater.from(this).inflate(R.layout.banner_header,null);
        recyTest.addFooterView(footer);
        recyTest.setAdapter(adapter);
        recyTest.setRefreshViewSupport(new RefreshViewSupport());


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