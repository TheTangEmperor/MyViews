package com.demo.myviews.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.myviews.R;
import com.demo.myviews.bean.CityData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.LetterViewHolder>{

    private List<CityData> list;
    private LayoutInflater inflater;

    public LetterAdapter(List<CityData> list, LayoutInflater inflater) {
        this.list = list;
        this.inflater = inflater;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getLayoutType();
    }

    @NonNull
    @Override
    public LetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LetterViewHolder(inflater.inflate(R.layout.letter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LetterViewHolder holder, int position) {
        CityData cityData = list.get(position);
        holder.tvName.setText(cityData.getName());
        if (cityData.getLayoutType() == 1){
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LetterViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        public LetterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
