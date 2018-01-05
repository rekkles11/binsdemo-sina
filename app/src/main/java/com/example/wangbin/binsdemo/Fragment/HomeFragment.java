package com.example.wangbin.binsdemo.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by momo on 2018/1/2.
 */

public class HomeFragment extends Fragment implements UserTimelineCallBack,VideoCallBack{

    Context mContext;
    public View mView;

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
    int mPage =1;

    public EndLessOnScrollListener mScrollListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_usertimeline,container,false);
        mContext = getContext();
        initView();
        getUserTimeline(mPage);
        return mView;

    }

    public void initView() {
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.header_item,null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.layout_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new RefreshListener());
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_usertimeline);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mScrollListener = new EndLessOnScrollListener(playPostion,mLayoutManager,mContext,HomeFragment.this,true) {
            @Override
            public void onLoadMore() {
                LogManager.d("load:::::1",mWrapper.mInnerAdapter.getItemCount());
                getUserTimeline(mPage);
                mAddNum=0;
            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);
        map = new HashMap<>();
        map.put("access_token", AccessTokenKeeper.readAccessToken(mContext).getToken());
        map.put("count","10");
    }
    public void getUserTimeline(int page) {
        LogManager.d("page1::",page);
        map.put("page", String.valueOf(page));
        mPage++;
        PublicTimelineTask publicTimelineTask = new PublicTimelineTask();
        WritePermission.verifystoragePermissons((Activity) mContext);
        try {
            publicTimelineTask.execute(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void isPlay(int postion) {
        this.playPostion = postion;
        LogManager.d("explayer::",postion);
        if(mList.get(postion).getPicUrls().size()==0){
            View view = mLayoutManager.findViewByPosition(postion);
            final LinearLayout layout = (LinearLayout) view;
            TextureView textureView = (TextureView) layout.findViewById(R.id.view_exoplayer);
            ExoPlayerInstance.getInstance().getPlayer(mContext);
            Uri playerUri = Uri.parse("https://storage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%20Hangin'%20with%20the%20Google%20Search%20Bar.mp4");
            ExoPlayerInstance.getInstance().setmExoPlayer(playerUri, textureView, true, mContext);
        }
    }

    @Override
    public void getResult(List<Status> list, Boolean isSuccess) {
        if (isFirst){
            if (list!=null) {
                mList = (list);
                LogManager.d("list::::1",list.size());
                int width = (int) getResources().getDisplayMetrics().widthPixels;
                mAdapter = new RecyclerAdapter(mList,mContext, width);
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
                mWrapper.notifyItemRangeInserted(mList.size()-list.size(), list.size());
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


    private class PublicTimelineTask extends AsyncTask<Map<String, String>, Integer, List<Status> > {
        public PublicTimelineTask() {
            super();
        }
        @Override
        protected List<com.example.wangbin.binsdemo.Entity.Status> doInBackground(Map<String, String>[] maps) {
            UserTimelineModel userTimelineModel = new UserTimelineModel(mContext);
            try {
                userTimelineModel.getStatus(maps[0], HomeFragment.this,"publictimeline");
            } catch (IOException e) {
                e.printStackTrace();
            }


            return userTimelineModel.getStatusList();
        }
    }




}
