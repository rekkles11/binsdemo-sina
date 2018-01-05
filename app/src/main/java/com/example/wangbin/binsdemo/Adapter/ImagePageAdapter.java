package com.example.wangbin.binsdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wangbin.binsdemo.Entity.PicUrl;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.GlideLoader;
import com.example.wangbin.binsdemo.Utils.ZoomImageView;

import java.util.List;

/**
 * Created by momo on 2017/12/27.
 */

public class ImagePageAdapter extends PagerAdapter {

    List<ZoomImageView> mList;
    Context mContext;
    List<PicUrl> mPicUrls;

    public ImagePageAdapter(List<ZoomImageView> mList, Context context,List<PicUrl> list){
        this.mList = mList;
        mContext = context;
        mPicUrls = list;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ZoomImageView imageView = (ZoomImageView) object;
        container.removeView(imageView);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ZoomImageView imageView = mList.get(position);
            new GlideLoader().displayLargeImg(mContext,mPicUrls.get(position).getThumbnailPic(),imageView);
            container.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity)mContext).finish();
                    ((Activity)mContext).overridePendingTransition(0, R.anim.push_left_out);
                }
            });

            return imageView;
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
