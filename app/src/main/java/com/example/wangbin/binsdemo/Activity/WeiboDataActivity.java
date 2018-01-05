package com.example.wangbin.binsdemo.Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.wangbin.binsdemo.Adapter.HeaderAndFooterWrapper;
import com.example.wangbin.binsdemo.Adapter.CommentsAdapter;
import com.example.wangbin.binsdemo.Entity.Comments;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.Model.CommentsCallBack;
import com.example.wangbin.binsdemo.Model.CommentsModel;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.OriginPicTextHeaderView;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibodata);
        initView();
        getData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.base_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }


    public void getData() {
        mStatus = (Status) getIntent().getSerializableExtra("weiboitem");
        mMap = new HashMap<>();
        mMap.put("access_token", AccessTokenKeeper.readAccessToken(
                this).getToken());
        mMap.put("id",String.valueOf(mStatus.getId()));
        new CommentsTask(this).execute(mMap);
    }

    @Override
    public void getComments(List<Comments> list) {
        mAdapter = new CommentsAdapter(WeiboDataActivity.this,list);
        mWrapper = new HeaderAndFooterWrapper(mAdapter);
        LinearLayout headerView  = new OriginPicTextHeaderView(WeiboDataActivity.this,mStatus,"comments");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(layoutParams);
        ((OriginPicTextHeaderView)headerView).setWeiboDataClickLinstener(mWeiboDataClickLinstener);
        mWrapper.addHeaderView(headerView);
        mRecyclerView.setAdapter(mWrapper);
    }
    public WeiboDataClickLinstener mWeiboDataClickLinstener = new WeiboDataClickLinstener() {
        @Override
        public void onComment() {
            new CommentsTask(WeiboDataActivity.this).execute(mMap);
        }

        @Override
        public void onResport() {
            mWrapper.notifyDataSetChanged();

        }
    };

    private class CommentsTask extends AsyncTask<Map<String, String>, Integer, Comments> {
        Context mContext;
        public CommentsTask(Context context){
            this.mContext = context;
        }

        @Override
        protected Comments doInBackground(Map<String, String>[] maps) {
            new CommentsModel(mContext).getComments(maps[0],"comments", WeiboDataActivity.this,mStatus.getIdstr());
            return null;
        }
    }


}
