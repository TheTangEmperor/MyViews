package com.demo.myviews.recycler;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {


    private Context mContext ;
    private SparseArray<View> mViews ;

    public static BaseViewHolder createViewHolder(Context context, View itemView){
        BaseViewHolder holder = new BaseViewHolder(context,itemView) ;
        return holder ;
    }

    public static BaseViewHolder createViewHolder(Context context , ViewGroup parent, int layoutId){
        View itemView = LayoutInflater.from(context).inflate(layoutId,parent,false) ;
        BaseViewHolder holder = new BaseViewHolder(context,itemView) ;
        return holder ;
    }

    public BaseViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        mViews = new SparseArray<>();
    }

    public <T extends View>T getView(int id){
        View view = mViews.get(id);
        if (view == null){
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

    public BaseViewHolder setImageUrl(int viewId, String url) {
        ImageView view = getView(viewId);
        Glide.with(view).asBitmap().load(url);
        return this;
    }

    public BaseViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public BaseViewHolder setVisibility(int viewId, int visibility){
        getView(viewId).setVisibility(visibility);
        return this;
    }

    /**
     * 设置ImageView的资源
     */
    public BaseViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * 设置条目点击事件
     */
    public void setOnIntemClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }

    /**
     * 设置条目长按事件
     */
    public void setOnIntemLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }
}
