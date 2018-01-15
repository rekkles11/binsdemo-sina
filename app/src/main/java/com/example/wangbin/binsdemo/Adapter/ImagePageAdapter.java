package com.example.wangbin.binsdemo.Adapter;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.wangbin.binsdemo.Entity.PicUrl;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.Image.GlideLoader;
import com.example.wangbin.binsdemo.Utils.Image.ZoomImageView;
import com.github.lisicnu.log4android.LogManager;

import java.util.List;

/**
 * Created by momo on 2017/12/27.
 */

public class ImagePageAdapter extends PagerAdapter {

    List<ZoomImageView> mList;
    Context mContext;
    List<PicUrl> mPicUrls;
    float mWidth;
    float mHeigh;
    int[] mLocal;

    public ImagePageAdapter(List<ZoomImageView> mList, Context context,List<PicUrl> list,int w,int h,int[] local){
        this.mList = mList;
        mContext = context;
        mPicUrls = list;
        mWidth= Float.valueOf(w);
        mHeigh = Float.valueOf(h);
        mLocal = local;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ZoomImageView imageView = (ZoomImageView) object;
        container.removeView(imageView);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ZoomImageView imageView = mList.get(position);
            new GlideLoader().displayLargeImg(mContext,mPicUrls.get(position).getThumbnailPic(),imageView);
            container.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doAnimation(v);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((Activity)mContext).finish();
                        }
                    },300);

                }
            });

            return imageView;
    }

    private void doAnimation(View v) {
        float width = Float.valueOf(v.getWidth());
                    float heigh = Float.valueOf(v.getHeight());

                    ScaleAnimation scale = new ScaleAnimation(1.0f,mWidth/width,
                            1.0f,mHeigh/heigh,
                            Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    int[] local = new int[2];
                    v.getLocationInWindow(local);
                    TranslateAnimation translate = new TranslateAnimation(
                            Animation.ABSOLUTE,
                            0f,
                            Animation.ABSOLUTE,
                            Float.valueOf(mLocal[0])+(mWidth-width)/2.0f - Float.valueOf(local[0]),
                            Animation.ABSOLUTE,
                            0f,
                            Animation.ABSOLUTE,
                            Float.valueOf(mLocal[1])+(mHeigh-heigh)/2.0f - Float.valueOf(local[1]));
                    AnimationSet set = new AnimationSet(true);
                    set.addAnimation(scale);
                    set.addAnimation(translate);
                    set.setDuration(300);
                    set.setFillAfter(true);
                    v.startAnimation(set);
    }

    @Override
    public int getCount() {
        if (mList == null|| mList.size()<=0) {
            return 0;
        }else
            return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
