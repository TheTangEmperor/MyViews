package com.demo.myviews.recycler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface RefreshViewProtocol {

    View getView(Context context, ViewGroup parent);
    void pull(int touchHeight, int viewHeight, int touchStatus);
    void refresh();
    void closeRefresh();

}
