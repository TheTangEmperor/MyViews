package com.demo.myviews.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public
class SizeTool {


    /**
     * 获取屏幕宽
     * @param context
     * @return
     */
    public static int getSceneWidth(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕高
     * @param context
     * @return
     */
    public static int getSceneHeight(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }
}
