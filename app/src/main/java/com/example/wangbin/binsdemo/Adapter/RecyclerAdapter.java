package com.example.wangbin.binsdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangbin.binsdemo.Activity.ImageActivity;
import com.example.wangbin.binsdemo.Activity.WeiboDataActivity;
import com.example.wangbin.binsdemo.Entity.PicUrl;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.ExoPlayerInstance;
import com.example.wangbin.binsdemo.Utils.Image.GlideLoader;
import com.example.wangbin.binsdemo.Utils.MyGridView;
import com.example.wangbin.binsdemo.Utils.Image.PicSize;
import com.example.wangbin.binsdemo.Utils.weiboContent.WeiBoContentTextUtil;
import com.github.lisicnu.log4android.LogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 2017/12/12.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<Status> mList;
    Context mContext;
    int width;
    List<ImageView> mImageViews = new ArrayList<>();

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Status status = mList.get(position);
        holder.text.setText(new WeiBoContentTextUtil().getWeiBoContent(status.getText(),mContext,holder.text));
        holder.reposts.setText(status.getRepostsCount().toString());
        holder.comments.setText(status.getCommentsCount().toString());
        holder.attltudes.setText(status.getAttitudesCount().toString());
        holder.createdAt.setText(status.getCreatedAt());
        holder.source.setText(status.getSource());
        holder.screenName.setText(status.getUser().getScreenName());
        holder.weiboLayout.setOnClickListener(new ClickLinsetener(status));
        mImageViews.add(holder.imageView);
        new GlideLoader().displayCircleImg(mContext,status.getUser().getAvatarLarge(),
                width/5,width/5,holder.imageView);
        if (status.getPicUrls().size()==0||status.getPicUrls() == null) {
            holder.gridView.setVisibility(View.GONE);
            holder.frameLayout.setVisibility(View.VISIBLE);
            holder.pauseView.setVisibility(View.VISIBLE);
        }
        else {
            holder.frameLayout.setVisibility(View.GONE);
            holder.gridView.setVisibility(View.VISIBLE);
            if (status.getPicUrls() != null) {
                if (status.getPicUrls().size() == 1)
                    status.getPicUrls().get(0).setThumbnailPic(status.getOriginalPic());
                setGridView(new PicSize().getMiddlePicUrls(status.getPicUrls()), holder.gridView);
                holder.gridView.setOnItemClickListener(new GridItemClickListener(holder.getLayoutPosition()));
            }
        }

    }
    private class ClickLinsetener implements View.OnClickListener {
        Status mStatus;
        ClickLinsetener(Status status){
            this.mStatus = status;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, WeiboDataActivity.class);
            intent.putExtra("weiboitem",mStatus);
            mContext.startActivity(intent);
        }
    }

    public void setGridView(List<PicUrl> picUrls,MyGridView view) {
        view.setColumn(picUrls.size(),width);
        view.setAdapter(new GridAdapter(mContext,picUrls,view.getOneItemWidth(),false));
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView text;
        public TextView reposts;
        public TextView comments;
        public TextView attltudes;
        public TextView createdAt;
        public TextView source;
        public MyGridView gridView;
        public TextView screenName;
        public ImageView imageView;
        public TextureView textureView;
        public ImageView pauseView;
        public FrameLayout frameLayout;
        public LinearLayout weiboLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_text);
            reposts = (TextView) itemView.findViewById(R.id.tv_reposts);
            comments = (TextView) itemView.findViewById(R.id.tv_comments);
            attltudes = (TextView) itemView.findViewById(R.id.tv_attltudes);
            createdAt = (TextView) itemView.findViewById(R.id.tv_usertimeline_created_at);
            source = (TextView) itemView.findViewById(R.id.tv_usertimeline_source);
            gridView = (MyGridView) itemView.findViewById(R.id.grid_usertimeline_pic_ids);
            screenName = (TextView) itemView.findViewById(R.id.tv_usertimeline_screen_name);
            imageView = (ImageView) itemView.findViewById(R.id.img_usertimeline_head);
            textureView = (TextureView) itemView.findViewById(R.id.view_exoplayer);
            pauseView = (ImageView) itemView.findViewById(R.id.img_pause);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.fram_palyer);
            weiboLayout = (LinearLayout) itemView.findViewById(R.id.layout_weibo);
        }
    }

    private class GridItemClickListener implements AdapterView.OnItemClickListener {


        int mHodlerPostion;
        public GridItemClickListener(int pos){
            this.mHodlerPostion = pos;
        }
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(mContext,ImageActivity.class);

            List<PicUrl> list = new PicSize().getLaragePicUrls(mList.get(mHodlerPostion).getPicUrls());
            String[] strings = new String[list.size()];
            for (int i =0;i<list.size();i++){
                strings[i] = list.get(i).getThumbnailPic();
            }
            intent.putExtra("pics",strings);
            intent.putExtra("index",position);
            LogManager.d("index:::",position);
            mContext.startActivity(intent);
        }
    }


}
