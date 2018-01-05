package com.example.wangbin.binsdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.wangbin.binsdemo.Entity.PicUrl;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.GlideLoader;
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
    public GridAdapter( Context context,  List<PicUrl> mList,int height,Boolean isShare) {
        this.mContext =  context;
        this.mList = mList;
        this.height = height;
        this.isShare = isShare;

    }


    @Override
    public int getCount() {
        if (isShare)
            return mList.size()+1;
        else
            return mList.size();
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
                new GlideLoader().displayNormalImg(mContext, mList.get(position).getThumbnailPic(),
                        height, height, viewHolder.imageView);
                if (isShare){
                    viewHolder.deleteImg.setVisibility(View.VISIBLE);
                    viewHolder.deleteImg.setOnClickListener(new DeleteClickListener(position));
                }
            }else if(mList.size() == position&&mList.size()>0){
                if(isShare) {
                    new GlideLoader().displayNormalImg(mContext,R.drawable.more_img,
                            height, height, viewHolder.imageView);
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
        int pos;
        DeleteClickListener(int pos){
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            mList.remove(pos);
            notifyDataSetChanged();

        }
    }


}
