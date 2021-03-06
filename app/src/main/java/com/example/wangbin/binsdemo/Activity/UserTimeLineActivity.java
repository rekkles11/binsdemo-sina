package com.example.wangbin.binsdemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wangbin.binsdemo.Adapter.HeaderAndFooterWrapper;
import com.example.wangbin.binsdemo.Adapter.RecyclerAdapter;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.Model.UserTimelineCallBack;
import com.example.wangbin.binsdemo.Model.UserTimelineModel;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.EndLessOnScrollListener;
import com.example.wangbin.binsdemo.Utils.ExoPlayerInstance;
import com.example.wangbin.binsdemo.Utils.TimelineTask;
import com.example.wangbin.binsdemo.Utils.VideoCallBack;
import com.example.wangbin.binsdemo.Utils.WritePermission;
import com.github.lisicnu.log4android.LogManager;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by momo on 2017/12/12.
 */

public class UserTimeLineActivity extends AppCompatActivity  implements UserTimelineCallBack{
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerAdapter mAdapter;
    HeaderAndFooterWrapper mWrapper;
    Map<String, String> map;
    List<Status> mList;
    Boolean isFirst = true;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mHeaderView;
    private String mName;
    private int mPage = 1;
    private EndLessOnScrollListener mEndLessOnScrollListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertimeline);
        intView();
        getUserTimeline(mPage);
    }

    public void getUserTimeline(int page) {
        map.put("page",String.valueOf(page));
        try {
            new TimelineTask(UserTimeLineActivity.this,mName).execute(map);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void intView() {
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.header_item,null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new RefreshListener());
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_usertimeline);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mName = getIntent().getStringExtra("name");
        if (mName.equals("usertimeline"))
            getSupportActionBar().setTitle("微博");
        else
            getSupportActionBar().setTitle("热门微博");

        map = new HashMap<>();
        map.put("access_token", AccessTokenKeeper.readAccessToken(this).getToken());
        map.put("page","1");
        map.put("count","4");
    }


    @Override
    public void getResult(List<Status> list) {
        if (isFirst){
            if (list!=null) {
                mList = (list);
                LogManager.d("list::::1",list.size());
                int width = (int) getResources().getDisplayMetrics().widthPixels;
                mAdapter = new RecyclerAdapter(mList, UserTimeLineActivity.this, width);
                mWrapper = new HeaderAndFooterWrapper(mAdapter);
                mRecyclerView.setAdapter(mWrapper);
                isFirst = false;
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }else {
            if (list!=null) {
                mList.addAll(list);
                LogManager.d("slist::::", list.size() + "+" + mList.size());
                mWrapper.notifyItemRangeInserted(mList.size()-list.size(), list.size());
            }

        }
        mEndLessOnScrollListener = new EndLessOnScrollListener(mList, UserTimeLineActivity.this.getApplication(),false) {
                    @Override
                    public void onLoadMore() {
                        getUserTimeline(mPage);
                    }
                };

        mRecyclerView.addOnScrollListener(mEndLessOnScrollListener);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.isCheckable()){
            item.setChecked(true);
        }
        switch (item.getItemId()){
            case R.id.menu_add:
                startActivityForResult(new Intent(UserTimeLineActivity.this,ShareActivity.class),100);
                default:
                    break;
        }
        return true;
    }

    //分享之后刷新
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 100){
                getUserTimeline(mPage);
            }
        }
    }

    class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            isFirst = true;
            getUserTimeline(mPage);


        }
    }

}
