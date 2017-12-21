package com.example.wangbin.binsdemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wangbin.binsdemo.Entity.PicUrl;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 2017/12/12.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<Status> mList;
    Context mContext;
    int width;

    public RecyclerAdapter(List<Status> list, Context context,int x){
        this.mList = list;
        this.mContext = context;
        this.width = x;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usertimeline_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Status status = mList.get(position);
        holder.text.setText(status.getText());
        holder.reposts.setText(status.getRepostsCount().toString());
        holder.comments.setText(status.getCommentsCount().toString());
        holder.attltudes.setText(status.getAttitudesCount().toString());
        holder.createdAt.setText(status.getCreatedAt());
        holder.source.setText(status.getSource());
        holder.screenName.setText(status.getUser().getScreenName());
        setImageView(holder.imageView,status.getUser().getAvatarLarge());
        if(status.getPicUrls()!=null) {
            if (status.getPicUrls().size() == 1)
                status.getPicUrls().get(0).setThumbnailPic(status.getOriginalPic());
            setGridView(status.getPicUrls(), holder.gridView);
        }

    }

    public void setGridView(List<PicUrl> picUrls,GridView view) {
        GridView gridView = new GridView(mContext);
        LinearLayout.LayoutParams layoutParams;
        switch (picUrls.size()){
            case 1:
                layoutParams = new LinearLayout.LayoutParams(width/3*2-100,width/3*2-100);
                view.setLayoutParams(layoutParams);
                view.setNumColumns(1);
                view.setAdapter(new GridAdapter(mContext,picUrls,width/3*2-100));
                break;
            case 2:
            case 3:
                layoutParams = new LinearLayout.LayoutParams(width,width/3);
                view.setLayoutParams(layoutParams);
                view.setNumColumns(3);
                view.setAdapter(new GridAdapter(mContext,picUrls,width/3));
                break;
            case 4:
                layoutParams = new LinearLayout.LayoutParams(width/3*2,width/3*2);
                view.setLayoutParams(layoutParams);
                view.setNumColumns(2);
                view.setAdapter(new GridAdapter(mContext,picUrls,width/3));
                break;
            case 5:
            case 6:
                layoutParams = new LinearLayout.LayoutParams(width,width/3*2);
                view.setLayoutParams(layoutParams);
                view.setNumColumns(3);
                view.setAdapter(new GridAdapter(mContext,picUrls,width/3));
                break;
            case 7:
            case 8:
            case 9:
                layoutParams = new LinearLayout.LayoutParams(width,width);
                view.setLayoutParams(layoutParams);
                view.setNumColumns(3);
                view.setAdapter(new GridAdapter(mContext,picUrls,width/3));
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setImageView(ImageView imageView,String url) {
        Glide.with(mContext).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .thumbnail(0.1f)
                .centerCrop()
                .dontAnimate()
                .into(imageView);
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView text;
        public TextView reposts;
        public TextView comments;
        public TextView attltudes;
        public TextView createdAt;
        public TextView source;
        public GridView gridView;
        public TextView screenName;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_text);
            reposts = (TextView) itemView.findViewById(R.id.tv_reposts);
            comments = (TextView) itemView.findViewById(R.id.tv_comments);
            attltudes = (TextView) itemView.findViewById(R.id.tv_attltudes);
            createdAt = (TextView) itemView.findViewById(R.id.tv_usertimeline_created_at);
            source = (TextView) itemView.findViewById(R.id.tv_usertimeline_source);
            gridView = (GridView) itemView.findViewById(R.id.grid_usertimeline_pic_ids);
            screenName = (TextView) itemView.findViewById(R.id.tv_usertimeline_screen_name);
            imageView = (ImageView) itemView.findViewById(R.id.img_usertimeline_head);
        }
    }
}
