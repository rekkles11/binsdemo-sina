package com.example.wangbin.binsdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wangbin.binsdemo.Entity.PicUrl;
import com.example.wangbin.binsdemo.R;

import java.util.List;

/**
 * Created by momo on 2017/12/15.
 */

public class GridAdapter extends ArrayAdapter{
    private Context mContext;
    private List<PicUrl> mList;
    private int height;

    public GridAdapter( Context context,  List<PicUrl> mList,int height) {
        super(context, R.layout.gridview_item, mList);
        this.mContext =  context;
        this.mList = mList;
        this.height = height;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            convertView =LayoutInflater.from(mContext).inflate(R.layout.gridview_item,parent,false);
            imageView = (ImageView) convertView.findViewById(R.id.img_usertimeline_pic);
        }else{
            imageView = (ImageView) convertView;
        }

        Glide.with(mContext).load(mList.get(position).getThumbnailPic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .thumbnail(0.1f)
                .centerCrop()
                .override(height,height)
                .crossFade()
                .into(imageView);
        return imageView;
    }
}
