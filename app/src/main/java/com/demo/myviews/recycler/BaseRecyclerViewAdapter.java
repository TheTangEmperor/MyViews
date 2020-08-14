package com.demo.myviews.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {


    private int layoutId;
    private List<T> datas;
    private MoreTypeProtocol typeSupport;
    private Context context;

    public BaseRecyclerViewAdapter(int layoutId, List<T> datas, Context context) {
        this.layoutId = layoutId;
        this.datas = datas == null ? new ArrayList<T>() : datas;
        this.context = context;
    }

    public BaseRecyclerViewAdapter(List<T> datas, MoreTypeProtocol typeSupport, Context context) {
        this.datas = datas == null ? new ArrayList<T>() : datas;
        this.typeSupport = typeSupport;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return typeSupport != null ? typeSupport.getLayoutId(position) : super.getItemViewType(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (typeSupport != null){
            layoutId = viewType;
        }
        return new BaseViewHolder(context, LayoutInflater.from(context).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        convert(holder, datas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public abstract void convert(BaseViewHolder holder, T item, int position);

    public T getItemData(int position){
        return datas.get(position);
    }
}
