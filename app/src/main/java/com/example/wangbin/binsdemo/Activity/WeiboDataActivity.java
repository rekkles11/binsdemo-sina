package com.example.wangbin.binsdemo.Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.wangbin.binsdemo.Adapter.HeaderAndFooterWrapper;
import com.example.wangbin.binsdemo.Adapter.CommentsAdapter;
import com.example.wangbin.binsdemo.Entity.Comments;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.Model.CommentsCallBack;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.EndLessOnScrollListener;
import com.example.wangbin.binsdemo.Utils.OriginPicTextHeaderView;
import com.example.wangbin.binsdemo.Utils.TimelineTask;
import com.example.wangbin.binsdemo.Utils.WeiboDataClickLinstener;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by momo on 2018/1/4.
 */

public class WeiboDataActivity extends AppCompatActivity implements CommentsCallBack {

    Status mStatus;
    public RecyclerView mRecyclerView;
    private HeaderAndFooterWrapper mWrapper;
    private CommentsAdapter mAdapter;
    Map<String,String> mMap;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String mWeiboId;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout mHeaderView;
    private Boolean isFirst = true;
    private List<Comments> mList;
    private EndLessOnScrollListener mScrollListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibodata);
        getSupportActionBar().setTitle("微博详情");
        initView();
        getData();
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.base_swipe_refresh_widget);
        mSwipeRefreshLayout.setOnRefreshListener(new DataRefreshLisenter());
        mRecyclerView = findViewById(R.id.base_RecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mStatus = (Status) getIntent().getSerializableExtra("weiboitem");
        mHeaderView  = new OriginPicTextHeaderView(WeiboDataActivity.this,mStatus,"comments");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mHeaderView.setLayoutParams(layoutParams);
        ((OriginPicTextHeaderView)mHeaderView).setWeiboDataClickLinstener(mWeiboDataClickLinstener);
        mMap = new HashMap<>();
        mMap.put("access_token", AccessTokenKeeper.readAccessToken(
                this).getToken());
        mWeiboId = String.valueOf(mStatus.getId());
        mMap.put("id",mWeiboId);
        mMap.put("page","1");
        mMap.put("count","10");
        mScrollListener = new EndLessOnScrollListener(WeiboDataActivity.this) {
            @Override
            public void onLoadMore() {
                getData();
            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);
    }


    public void getData() {
        new TimelineTask(this,"comments",mWeiboId).execute(mMap);

    }
    private class DataRefreshLisenter implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            isFirst = true;
            getData();
        }
    }


    @Override
    public void getComments(List<Comments> list) {
        if (isFirst) {
            if (list!=null&&list.size()!=0)
                mList = list;
            mAdapter = new CommentsAdapter(WeiboDataActivity.this, list);
            mWrapper = new HeaderAndFooterWrapper(mAdapter);
            mWrapper.addHeaderView(mHeaderView);
            mRecyclerView.setAdapter(mWrapper);
            isFirst = false;
            mSwipeRefreshLayout.setRefreshing(false);
        }else {
            if (list!=null){
                if (mList==null)
                    mList = list;
                else
                    mList.addAll(list);
                mWrapper.notifyItemRangeInserted(mList.size()-list.size(), list.size());

            }

        }
//        ((OriginPicTextHeaderView)mHeaderView).mInstance.getPlayer().setPlayWhenReady(true);
    }
    public WeiboDataClickLinstener mWeiboDataClickLinstener = new WeiboDataClickLinstener() {
        @Override
        public void onComment() {
            new TimelineTask(WeiboDataActivity.this,"comments",mWeiboId).execute(mMap);
        }

        @Override
        public void onResport() {
            mWrapper.notifyDataSetChanged();

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
//        ((OriginPicTextHeaderView)mHeaderView).mInstance.releasePlayer();
    }
}
