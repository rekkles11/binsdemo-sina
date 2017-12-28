package com.example.wangbin.binsdemo.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.LinearLayout;


/**
 * Created by momo on 2017/12/28.
 */

public class MyGridView extends GridView {
    int mNum;
    int mWidth;
    int mHeight;
    int mItemWidth =0;
    LinearLayout.LayoutParams layoutParams;

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setColumn(int num,int width){
        this.mNum = num;
        mWidth = width;
        if (num ==1){
            mWidth = mWidth/3*2-100;
            mHeight = mWidth;
            mItemWidth = mWidth;
            layoutParams = new LinearLayout.LayoutParams(mWidth,mHeight);
            setLayoutParams(layoutParams);
            super.setNumColumns(1);
        }else if (num<4&&num>1||num>4){
            int rmd =  num%3;
            if (rmd!=0)
                num = (num/3)+1;
            else
                num /=3;
            mHeight = mWidth/3*(num);
            mItemWidth = mWidth/3;
            layoutParams = new LinearLayout.LayoutParams(mWidth,mHeight);
            setLayoutParams(layoutParams);
            super.setNumColumns(3);
        }else if (num==4){
            mWidth = mWidth/3*2;
            mHeight = mWidth;
            mItemWidth = mWidth/2;
            layoutParams = new LinearLayout.LayoutParams(mWidth,mHeight);
            setLayoutParams(layoutParams);
            super.setNumColumns(2);
        }

    }

    public int getOneItemWidth(){
        return mItemWidth;
    }

    @Override
    public void setNumColumns(int numColumns) {
        super.setNumColumns(numColumns);
    }
}
