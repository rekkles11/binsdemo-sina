package com.example.wangbin.binsdemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangbin.binsdemo.Entity.Comments;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.Image.GlideLoader;
import com.example.wangbin.binsdemo.Utils.weiboContent.WeiBoContentTextUtil;

import java.util.List;

/**
 * Created by momo on 2018/1/5.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    Context mContext;
    List<Comments> mList;
    public CommentsAdapter(Context context,List<Comments> list){
        this.mContext = context;
        mList = list;
    }

    @Override
    public CommentsAdapter.CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mainfragment_weiboitem_detail_commentbar_comment_item,parent,
                        false);
        CommentsViewHolder viewHolder = new CommentsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        if (mList!=null&&position<mList.size()) {
            Comments comments = mList.get(position);
            new GlideLoader().displayCircleImg(mContext, comments.getUser().getProfileImageUrl(),
                    50, 50, holder.profileImg);
            holder.commentName.setText(comments.getUser().getName());
            holder.commentTime.setText(comments.getCreatedAt());
            holder.commentContent.setText(new WeiBoContentTextUtil().getWeiBoContent(comments.getText(),
                    mContext, holder.commentContent));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class CommentsViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImg;
        TextView commentName;
        TextView commentTime;
        TextView commentContent;



        public CommentsViewHolder(View itemView) {
            super(itemView);
            profileImg = (ImageView) itemView.findViewById(R.id.profile_img);
            commentName = (TextView) itemView.findViewById(R.id.comment_profile_name);
            commentTime = (TextView) itemView.findViewById(R.id.comment_profile_time);
            commentContent = (TextView) itemView.findViewById(R.id.comment_content);
        }
    }
}
