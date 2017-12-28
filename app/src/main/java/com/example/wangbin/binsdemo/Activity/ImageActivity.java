package com.example.wangbin.binsdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wangbin.binsdemo.Adapter.ImagePageAdapter;
import com.example.wangbin.binsdemo.Entity.PicUrl;
import com.example.wangbin.binsdemo.Model.UserTimelineModel;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.GlideLoader;
import com.example.wangbin.binsdemo.Utils.MyTouchView;
import com.example.wangbin.binsdemo.Utils.TouchImageView;
import com.github.lisicnu.log4android.LogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 2017/12/27.
 */

public class ImageActivity extends AppCompatActivity {

    MyTouchView mMyTouchView;
    List<PicUrl> mPicUrls = new ArrayList<>();
    ViewPager mViewPager;
    List<MyTouchView> mImageViews;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        int index = intent.getIntExtra("index",1);


        String[] path = intent.getStringArrayExtra("pics");
         for (int i =0;i<path.length;i++){
             PicUrl picUrl = new PicUrl();
             picUrl.setThumbnailPic(path[i]);
             mPicUrls.add(picUrl);

         }
        mImageViews = new ArrayList<>();
        for (int i = 0;i<path.length;i++){
            MyTouchView touchImageView = new MyTouchView(this);
            Display display = this.getWindowManager().getDefaultDisplay();
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(display.getWidth(), display.getHeight());
            touchImageView.setLayoutParams(marginLayoutParams);
            mImageViews.add(touchImageView);
        }
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ImagePageAdapter(mImageViews,ImageActivity.this,mPicUrls));
        mViewPager.setCurrentItem(index);


    }
}
