package com.example.wangbin.binsdemo.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.wangbin.binsdemo.Adapter.RecyclerAdapter;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.Model.ShareCallBack;
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
 * Created by momo on 2017/12/12.
 */

public class UserTimeLineActivity extends AppCompatActivity  implements UserTimelineCallBack{
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerAdapter mAdapter;
    ProgressBar mProgressBar;
    Map<String, String> map;

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
        mProgressBar = (ProgressBar) findViewById(R.id.pb_usertimeline);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_usertimeline);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        map = new HashMap<>();
        map.put("access_token", AccessTokenKeeper.readAccessToken(this).getToken());
        map.put("page","1");
        map.put("count","3");
    }



    @Override
    public void getResult(List<Status> list) {
        if(list!=null) {
            int width = (int)getResources().getDisplayMetrics().widthPixels;
            mAdapter = new RecyclerAdapter(list,UserTimeLineActivity.this,width);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int page) {
                    map.put("count","2");
                    getUserTimeline(String.valueOf(page));
                    mAdapter.notifyDataSetChanged();

                }
            });
        }
    }


    private class UserTimelineTask extends AsyncTask<Map<String, String>, Integer, List<Status> > {
        public UserTimelineTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<com.example.wangbin.binsdemo.Entity.Status> doInBackground(Map<String, String>[] maps) {
            UserTimelineModel userTimelineModel = new UserTimelineModel(UserTimeLineActivity.this);
            try {
                userTimelineModel.getStatus(maps[0],UserTimeLineActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return userTimelineModel.getStatusList();
        }



        @Override
        protected void onPostExecute(List<com.example.wangbin.binsdemo.Entity.Status> list) {
            super.onPostExecute(list);
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            mProgressBar.setProgress(progress[0]);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 100){
                getUserTimeline("1");
            }
        }
    }
}
