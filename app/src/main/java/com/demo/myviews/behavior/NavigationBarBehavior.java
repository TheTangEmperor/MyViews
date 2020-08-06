package com.demo.myviews.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.demo.myviews.R;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class NavigationBarBehavior extends CoordinatorLayout.Behavior<View> {

    public NavigationBarBehavior() {
    }

    public NavigationBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private static final String TAG = "NavigationBarBehavior";
    private RelativeLayout navigationBar;
    private int translationY = 0;


    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (navigationBar == null){
            ViewGroup rootView = (ViewGroup) parent.getParent().getParent();
            navigationBar = rootView.findViewById(R.id.rlNavigationBar);
            Log.d(TAG, "layoutDependsOn: " + navigationBar);
        }
        return dependency.getId() == R.id.list;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (translationY == 0){
            translationY = (int) dependency.getY() - child.getHeight();
            Log.d(TAG, "translationY: " + translationY);
        }
        float depY = dependency.getY() - child.getHeight();
        float bili = -(depY / translationY);
        float offset = bili * child.getHeight();
        Log.d(TAG, "depY: " + depY + "  bili: " + bili + " offset: " + offset);
        offset = offset > 0 ? 0 : offset;

        child.setTranslationY(offset);
//        navigationBar.setTranslationY(offset);
        return super.onDependentViewChanged(parent, child, dependency);
    }

}
