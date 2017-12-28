package com.example.wangbin.binsdemo.Activity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wangbin.binsdemo.Adapter.RecyclerAdapter;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.Model.UserTimelineCallBack;
import com.example.wangbin.binsdemo.Model.UserTimelineModel;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.EndLessOnScrollListener;
import com.example.wangbin.binsdemo.Utils.WritePermission;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by momo on 2017/12/26.
 */

public class PublicTimelineActivity extends AppCompatActivity implements UserTimelineCallBack{

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    Map<String,String> map;
    List<Status> mList;
    RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publictimeline);
        intView();
        getUserTimeline("1");

    }

    public void intView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_publictimeline);
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

        if(mList!=null) {
            if (list !=null)
                mList.addAll(list);
        }else{
            if (list!=null)
                mList = list;
        }
        if (mList!=null){
            int width = (int) getResources().getDisplayMetrics().widthPixels;
            mAdapter = new RecyclerAdapter(list, PublicTimelineActivity.this, width);
            mRecyclerView.setAdapter(mAdapter);
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

    public EndLessOnScrollListener mEndLessOnScrollListener = new EndLessOnScrollListener(mLayoutManager,PublicTimelineActivity.this) {
        @Override
        public void onLoadMore() {
            getUserTimeline("1");
            mRecyclerView.scrollToPosition(2);
        }
    };

}
