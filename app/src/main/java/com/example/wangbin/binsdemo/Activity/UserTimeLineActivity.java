package com.example.wangbin.binsdemo.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.example.wangbin.binsdemo.Adapter.RecyclerAdapter;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.Model.UserTimelineCallBack;
import com.example.wangbin.binsdemo.Model.UserTimelineModel;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.EndLessOnScrollListener;
import com.example.wangbin.binsdemo.Utils.VideoCallBack;
import com.example.wangbin.binsdemo.Utils.WritePermission;
import com.github.lisicnu.log4android.LogManager;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by momo on 2017/12/12.
 */

public class UserTimeLineActivity extends AppCompatActivity  implements UserTimelineCallBack,VideoCallBack{
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerAdapter mAdapter;
    Map<String, String> map;
    List<Status> mList;
    Boolean isFirst = true;
    int mAddNum=0;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertimeline);
        intView();
        getUserTimeline("1");
    }

    public void getUserTimeline(String page) {
        map.put("page",page);
        UserTimelineTask userTimelineTask = new UserTimelineTask();
        WritePermission.verifystoragePermissons(this);
        try {
             userTimelineTask.execute(map);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void intView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new RefreshListener());
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_usertimeline);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(mEndLessOnScrollListener);
        map = new HashMap<>();
        map.put("access_token", AccessTokenKeeper.readAccessToken(this).getToken());
        map.put("page","1");
        map.put("count","3");
    }


    @Override
    public void getResult(List<Status> list,Boolean isSuccess) {

        if (isFirst){
            if (list!=null) {
                mList = (list);
                LogManager.d("list::::",list.size());
                int width = (int) getResources().getDisplayMetrics().widthPixels;
                mAdapter = new RecyclerAdapter(mList, UserTimeLineActivity.this, width);
                mRecyclerView.setAdapter(mAdapter);
                isFirst = false;
            }
        }else {
            if (isSuccess&&list!=null) {
                mAddNum = list.size();
                    mList.addAll(list);
                    LogManager.d("slist::::", list.size() + "+" + mList.size());
                    mAdapter.notifyItemRangeInserted(mAddNum, list.size());
            }

        }
//        if(mList!=null) {
//            if (list !=null)
//                mList.addAll(list);
//        }else{
//            if (list!=null)
//                mList = list;
//        }
//        if (mList!=null){
//            if (isFirst) {
//                int width = (int) getResources().getDisplayMetrics().widthPixels;
//                mAdapter = new RecyclerAdapter(mList, UserTimeLineActivity.this, width);
//                mRecyclerView.setAdapter(mAdapter);
//                isFirst = false;
//            }else {
//                LogManager.d("size:::::",mList.size()+"+"+list.size());
//                if (list!=null)
//                    mAdapter.notifyItemRangeInserted(mList.size()-list.size()+1,list.size());
//            }
//        }

    }

    public EndLessOnScrollListener mEndLessOnScrollListener =
            new EndLessOnScrollListener(mLayoutManager,UserTimeLineActivity.this,UserTimeLineActivity.this) {
        @Override
        public void onLoadMore() {

            getUserTimeline("1");
            LogManager.d("addNum:::",mAddNum);
            mRecyclerView.scrollToPosition(mList.size()-mAddNum);
            mAddNum=0;
        }


    };

    /**
     * 播放本地视频
     */

    private String getLocalVideoPath(String name) {
        String sdCard = Environment.getExternalStorageDirectory().getPath();
        String uri = sdCard + File.separator + name;
        return uri;
    }

    @Override
    public void isPlay(Boolean bool,int postion) {
        mAdapter.setVideo(bool,postion);

    }


    private class UserTimelineTask extends AsyncTask<Map<String, String>, Integer, List<Status> > {
        public UserTimelineTask() {
            super();
        }

        @Override
        protected List<com.example.wangbin.binsdemo.Entity.Status> doInBackground(Map<String, String>[] maps) {
            UserTimelineModel userTimelineModel = new UserTimelineModel(UserTimeLineActivity.this);
            try {
                userTimelineModel.getStatus(maps[0],UserTimeLineActivity.this,"usertimeline");
            } catch (IOException e) {
                e.printStackTrace();
            }


            return userTimelineModel.getStatusList();
        }

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
                getUserTimeline("1");
            }
        }
    }

    class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            mAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);

        }
    }


}
