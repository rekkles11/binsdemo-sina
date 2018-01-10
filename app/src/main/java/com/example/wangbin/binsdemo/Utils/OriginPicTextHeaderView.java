package com.example.wangbin.binsdemo.Utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangbin.binsdemo.Adapter.GridAdapter;
import com.example.wangbin.binsdemo.Entity.PicUrl;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.Image.GlideLoader;
import com.example.wangbin.binsdemo.Utils.weiboContent.WeiBoContentTextUtil;
import com.github.lisicnu.log4android.LogManager;

import java.util.List;

/**
 * Created by momo on 2018/1/4.
 */

public class OriginPicTextHeaderView extends LinearLayout {
    private final String COMMENTS = "comments";
    private final String RESPORTS = "resports";

    private View mView;
    Context mContext;
    LinearLayout origin_weibo_layout;

    ImageView img_head;
    TextView screen_name;
    TextView create_at;
    TextView weibo_content;
    MyGridView myGridView;
    FrameLayout playerView;
    TextView reports;
    TextView comments;
    TextView attltudes;
    TextView commentView;
    TextView retweetView;
    TextView likeView;
    ImageView mCommentIndicator;
    ImageView mRetweetIndicator;

    WeiboDataClickLinstener mWeiboDataClickLinstener;

    public OriginPicTextHeaderView(Context context, Status status,String type){
        super(context);
        init(context,status);
        switch (type){
            case COMMENTS:
                commentLight();
                break;
            case RESPORTS:
                resportLight();
                break;
        }

    }
    private void init(Context context, Status status) {
        mContext = context;
        mView = inflate(context, R.layout.weibo_data_header_view,this);
        origin_weibo_layout = (LinearLayout)mView.findViewById(R.id.origin_weibo_layout);
        img_head = (ImageView) mView.findViewById(R.id.img_usertimeline_head);
        screen_name = (TextView) mView.findViewById(R.id.tv_usertimeline_screen_name);
        create_at = (TextView) mView.findViewById(R.id.tv_usertimeline_created_at);
        weibo_content = (TextView) mView.findViewById(R.id.tv_text);
        myGridView = (MyGridView) mView.findViewById(R.id.grid_usertimeline_pic_ids);
        playerView = (FrameLayout)findViewById(R.id.fram_palyer);
        reports = (TextView) mView.findViewById(R.id.tv_reposts);
        comments = (TextView) mView.findViewById(R.id.tv_comments);
        attltudes = (TextView) mView.findViewById(R.id.tv_attltudes);
        commentView = (TextView) mView.findViewById(R.id.commentBar_comment);
        retweetView = (TextView) mView.findViewById(R.id.commentBar_retweet);
        likeView = (TextView) mView.findViewById(R.id.commentBar_like);
        mCommentIndicator = (ImageView) findViewById(R.id.comment_indicator);
        mRetweetIndicator = (ImageView) findViewById(R.id.retweet_indicator);
        initWeiBoContent(context, status);


    }

    public void setWeiboDataClickLinstener(WeiboDataClickLinstener clickLinstener){
        this.mWeiboDataClickLinstener = clickLinstener;

    }

    private void initWeiBoContent(Context context, Status status) {
        new GlideLoader().displayNormalImg(mContext,status.getUser().getProfileImageUrl(),50
                                            ,50,img_head);
        screen_name.setText(status.getUser().getName());
        create_at.setText(status.getCreatedAt());
        weibo_content.setText(new WeiBoContentTextUtil().getWeiBoContent(status.getText()
                ,mContext,weibo_content));
        retweetView.setText("转发  "+String.valueOf(status.getRepostsCount()));
        commentView.setText("评论  "+String.valueOf(status.getCommentsCount()));
        likeView.setText("赞  "+String.valueOf(status.getAttitudesCount()));
        List<PicUrl> picUrls =  status.getPicUrls();
        if (picUrls.size()!=0||picUrls!= null) {
            myGridView.setVisibility(View.VISIBLE);
            int width = (int) getResources().getDisplayMetrics().widthPixels;
            myGridView.setColumn(picUrls.size(), width);
            myGridView.setAdapter(new GridAdapter(mContext, picUrls, myGridView.getOneItemWidth(), false));
        }
        playerView.setVisibility(View.GONE);

        retweetView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogManager.d("resports:::","click");
                resportLight();
                mWeiboDataClickLinstener.onResport();

            }
        });



        comments.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogManager.d("comments:::","click");
                commentLight();
                mWeiboDataClickLinstener.onComment();
            }
        });
    }

    private void commentLight() {
        commentView.setTextColor(Color.parseColor("#000000"));
        mCommentIndicator.setVisibility(View.VISIBLE);
        retweetView.setTextColor(Color.parseColor("#828282"));
        mRetweetIndicator.setVisibility(View.INVISIBLE);
    }

    private void resportLight() {
        retweetView.setTextColor(Color.parseColor("#000000"));
        mRetweetIndicator.setVisibility(View.VISIBLE);
        commentView.setTextColor(Color.parseColor("#828282"));
        mCommentIndicator.setVisibility(View.INVISIBLE);
    }

}
