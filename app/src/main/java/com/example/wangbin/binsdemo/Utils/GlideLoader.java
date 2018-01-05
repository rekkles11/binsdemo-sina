package com.example.wangbin.binsdemo.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wangbin.binsdemo.R;

/**
 * Created by momo on 2017/12/25.
 */

public class GlideLoader implements com.yancy.imageselector.ImageLoader {

    //展示GridView的图片
    public void displayNormalImg(Context context, String url, int width, int height, ImageView imageView){
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .thumbnail(0.1f)
                .centerCrop()
                .override(width,height)
                .crossFade()
                .into(imageView);
    }
    //记载resource资源
    public void displayNormalImg(Context context, Integer url, int width, int height, ImageView imageView){
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .thumbnail(0.1f)
                .centerCrop()
                .override(width,height)
                .crossFade()
                .into(imageView);
    }

    //viewPager的大图
    public void displayLargeImg(Context context,String url, ZoomImageView imageView){
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .thumbnail(0.1f)
                .fitCenter()
                .crossFade()
                .into(imageView);
    }
    //圆形头像
    public void displayCircleImg  (Context mContext ,String picPath,int width,int height,ImageView imgView){
        Glide.with(mContext).load(picPath)
                .override(width,height)
                .transform(new  GlideCircleTransform(mContext)).into(imgView);

    }

    //重写imageselector的展示图片方法
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(com.yancy.imageselector.R.mipmap.imageselector_photo)
                .centerCrop()
                .into(imageView);
    }
}
