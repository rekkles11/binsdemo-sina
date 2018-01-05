package com.example.wangbin.binsdemo.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

/**
 * Created by momo on 2018/1/4.
 */

public class BarManager {
    /**
     * 显示顶部导航栏
     */
    public void showTopBar(final View topBar) {
        topBar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    /**
     * 隐藏顶部导航栏
     */
    public void hideTopBar(final ViewGroup topBar, Context context) {
        int moveOffset;
        View toast = topBar.getChildAt(1);
        moveOffset = topBar.getHeight() - toast.getHeight();
        topBar.animate().translationY(-moveOffset).setInterpolator(new AccelerateInterpolator(2));
    }

    /**
     * 显示底部导航栏
     */
    public void showBottomBar(final View bottomBar) {
        bottomBar.animate().translationY(0).setInterpolator(new OvershootInterpolator(2)).start();
    }

    /**
     * 隐藏底部导航栏
     */
    public void hideBottomBar(final View bottomBar) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) bottomBar.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        bottomBar.animate().translationY(bottomBar.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }
}
