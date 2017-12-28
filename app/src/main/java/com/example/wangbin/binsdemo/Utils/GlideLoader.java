package com.example.wangbin.binsdemo.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wangbin.binsdemo.R;

/**
 * Created by momo on 2017/12/25.
 */

public class GlideLoader {
    public void displayImg(Context context,String url, int width, int height, ImageView imageView){
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

    public void displayLargeImg(Context context,String url, ImageView imageView){
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .thumbnail(0.1f)
                .fitCenter()
                .crossFade()
                .into(imageView);
    }

    public void displayCircleImg  (Context mContext ,String picPath,int width,int height,ImageView imgView){
        Glide.with(mContext).load(picPath)
                .override(width,height)
                .transform(new  GlideCircleTransform(mContext)).into(imgView);

    }


}
