package com.demo.myviews.recycler;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HeaderViewAdapter extends RecyclerView.Adapter {

    private final int HEADER_TYPE = -10;
    private final int FOOTER_TYPE = -11;
    private ArrayList<View> mHeaderViewInfos = new ArrayList<View>();
    private ArrayList<View> mFooterViewInfos = new ArrayList<View>();
    private RecyclerView.Adapter mAdapter;
    private int takeOutHeader = 0;
    private int takeOutFooter = 0;
    private static final String TAG = "HeaderViewAdapter";


    public HeaderViewAdapter(ArrayList<View> headerViewInfos, ArrayList<View> footerViewInfos, RecyclerView.Adapter adapter) {
        if (headerViewInfos != null){
            this.mHeaderViewInfos = headerViewInfos;
        }
        if (footerViewInfos != null){
            this.mFooterViewInfos = footerViewInfos;
        }
        this.mAdapter = adapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d(TAG, "onCreateViewHolder: " + viewType);
        RecyclerView.ViewHolder holder = null;
        if (viewType == HEADER_TYPE){
            holder = new BaseViewHolder(parent.getContext(), mHeaderViewInfos.get(takeOutHeader));
            takeOutHeader++;
        }else if (viewType == FOOTER_TYPE){
            holder = new BaseViewHolder(parent.getContext(), mFooterViewInfos.get(takeOutFooter));
            takeOutFooter++;
        }else {
            holder = mAdapter.onCreateViewHolder(parent, viewType);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder: " + position);
        int headerCount = mHeaderViewInfos.size();
        if (position < headerCount){
            return;
        }
        int justPosition = position - headerCount;
        if (mAdapter != null && justPosition < mAdapter.getItemCount()){
            mAdapter.onBindViewHolder(holder, justPosition);
        }

    }

    @Override
    public int getItemViewType(int position) {
        int headerCount = mHeaderViewInfos.size();
        if (position < headerCount){
            return HEADER_TYPE;
        }
        int justPosition = position - headerCount;
        if (mAdapter != null && justPosition < mAdapter.getItemCount()){
            return mAdapter.getItemViewType(justPosition);
        }

        return FOOTER_TYPE;
    }

    @Override
    public int getItemCount() {
        if (mAdapter == null){
            return mHeaderViewInfos.size() + mFooterViewInfos.size();
        }
        return mHeaderViewInfos.size() + mFooterViewInfos.size() + mAdapter.getItemCount();
    }


}
