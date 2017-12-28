package com.example.wangbin.binsdemo.Utils;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Transformation;

/**
 * Created by momo on 2017/12/27.
 */

public class dropOutAnimation extends Animation {
    int mWidth;
    int mHeight;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.mWidth = width;
        this.mHeight = height;
        setDuration(200);
        setInterpolator(new AnticipateInterpolator());
        setFillAfter(true);


    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Matrix matrix = t.getMatrix();
//        matrix.sc


        matrix.preTranslate(-mWidth,-mHeight);
        matrix.postTranslate(mWidth,mHeight);


    }
}
