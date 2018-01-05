package com.example.wangbin.binsdemo.Activity;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.wangbin.binsdemo.Adapter.HeaderAndFooterWrapper;
import com.example.wangbin.binsdemo.Adapter.RecyclerAdapter;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.Model.UserTimelineCallBack;
import com.example.wangbin.binsdemo.Model.UserTimelineModel;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.EndLessOnScrollListener;
import com.example.wangbin.binsdemo.Utils.ExoPlayerInstance;
import com.example.wangbin.binsdemo.Utils.VideoCallBack;
import com.example.wangbin.binsdemo.Utils.WritePermission;
import com.github.lisicnu.log4android.LogManager;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by momo on 2017/12/26.
 */

public class PublicTimelineActivity extends AppCompatActivity implements UserTimelineCallBack,VideoCallBack{


    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerAdapter mAdapter;
    HeaderAndFooterWrapper mWrapper;
    Map<String, String> map;
    List<Status> mList;
    Boolean isFirst = true;
    int mAddNum=0;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mHeaderView;
    int playPostion =0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertimeline);
        intView();
        getUserTimeline("1");

    }

    public void intView() {
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.header_item,null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new RefreshListener());
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_usertimeline);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(mEndLessOnScrollListener);
        map = new HashMap<>();
        map.put("access_token", AccessTokenKeeper.readAccessToken(this).getToken());
        map.put("page","1");
        map.put("count","5");
    }

    public void getUserTimeline(String page) {
        map.put("page", page);
        PublicTimelineTask publicTimelineTask = new PublicTimelineTask();
        WritePermission.verifystoragePermissons(this);
        try {
            publicTimelineTask.execute(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResult(List<Status> list,Boolean isSuccess) {

        if (isFirst){
            if (list!=null) {
                mList = (list);
                LogManager.d("list::::1",list.size());
                int width = (int) getResources().getDisplayMetrics().widthPixels;
                mAdapter = new RecyclerAdapter(mList, PublicTimelineActivity.this, width);
                mWrapper = new HeaderAndFooterWrapper(mAdapter);
                mRecyclerView.setAdapter(mWrapper);
                isFirst = false;
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }else {
            if (list!=null) {
                mAddNum = list.size();
                mList.addAll(list);
                LogManager.d("slist::::", list.size() + "+" + mList.size());
                mWrapper.notifyItemRangeInserted(mAddNum, list.size());
            }

        }

    }

    @Override
    public void isPlay(int postion) {
        this.playPostion = postion;
        LogManager.d("explayer::",postion);
        if(mList.get(postion).getPicUrls().size()==0){
            View view = mLayoutManager.findViewByPosition(postion);
            LinearLayout layout = (LinearLayout) view;
            TextureView textureView = (TextureView) layout.findViewById(R.id.view_exoplayer);
            ExoPlayerInstance.getInstance().getPlayer(PublicTimelineActivity.this);
            Uri playerUri = Uri.parse("https://storage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%20Hangin'%20with%20the%20Google%20Search%20Bar.mp4");
            ExoPlayerInstance.getInstance().setmExoPlayer(playerUri, textureView, true, PublicTimelineActivity.this);
        }
    }

    private class PublicTimelineTask extends AsyncTask<Map<String, String>, Integer, List<Status> > {
        public PublicTimelineTask() {
            super();
        }
        @Override
        protected List<com.example.wangbin.binsdemo.Entity.Status> doInBackground(Map<String, String>[] maps) {
            UserTimelineModel userTimelineModel = new UserTimelineModel(PublicTimelineActivity.this);
            try {
                userTimelineModel.getStatus(maps[0],PublicTimelineActivity.this,"publictimeline");
            } catch (IOException e) {
                e.printStackTrace();
            }


            return userTimelineModel.getStatusList();
        }



    }

    public EndLessOnScrollListener mEndLessOnScrollListener =
            new EndLessOnScrollListener(playPostion,mLayoutManager,PublicTimelineActivity.this,PublicTimelineActivity.this) {
                @Override
                public void onLoadMore() {
                    LogManager.d("load:::::1",mWrapper.mInnerAdapter.getItemCount());
                    getUserTimeline("1");
                    mWrapper.notifyDataSetChanged();
                    mAddNum=0;
                }


            };

    class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            isFirst = true;
            getUserTimeline("1");


        }
    }

}