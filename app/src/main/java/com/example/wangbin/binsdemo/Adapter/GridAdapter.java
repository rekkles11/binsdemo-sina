package com.example.wangbin.binsdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.wangbin.binsdemo.Activity.ImageActivity;
import com.example.wangbin.binsdemo.Entity.PicUrl;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.GlideLoader;
import com.example.wangbin.binsdemo.Utils.PicSize;
import com.github.lisicnu.log4android.LogManager;

import java.util.List;

/**
 * Created by momo on 2017/12/15.
 */

public class GridAdapter extends BaseAdapter{
    private Context mContext;
    private List<PicUrl> mList;
    private int height;
    Boolean isShare;
    int mPostion;
    public GridAdapter( Context context,  List<PicUrl> mList,int height,Boolean isShare) {
        this.mContext =  context;
        this.mList = mList;
        this.height = height;
        this.isShare = isShare;

    }


    @Override
    public int getCount() {
        return mList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, parent,
                    false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.img_usertimeline_pic);
            viewHolder.deleteImg = (ImageView) view.findViewById(R.id.img_delete);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
            if (position<mList.size()) {
                LogManager.d("1isShare::",position+"+"+mList.size());
                new GlideLoader().displayImg(mContext, mList.get(position).getThumbnailPic(),
                        height, height, viewHolder.imageView);
                if (isShare){
                    viewHolder.deleteImg.setVisibility(View.VISIBLE);
                    LogManager.d("in11dex:::",position);
                    mPostion = position;
                    viewHolder.deleteImg.setOnClickListener(new DeleteClickListener());
                }
            }else if(mList.size() == position&&mList.size()>0){
                if(isShare) {
                    LogManager.d("isShare::",position+"+"+mList.size());
                    viewHolder.imageView.setImageResource(R.drawable.more_img);
                    viewHolder.deleteImg.setVisibility(View.GONE);
                }
            }
        return view;
    }

    class ViewHolder{
        ImageView imageView;
        ImageView deleteImg;
    }


    private class DeleteClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            mList.remove(mPostion);
            notifyDataSetChanged();

        }
    }


}
