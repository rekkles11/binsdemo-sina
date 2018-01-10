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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wangbin.binsdemo.Activity.UserTimeLineActivity;
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
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mHeaderView;
    int playPostion =0;
    int mPage =1;
    public EndLessOnScrollListener mScrollListener;
    private RefreshListener mRefreshListener;


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
        mRefreshListener = new RefreshListener();
        mSwipeRefreshLayout.setOnRefreshListener(mRefreshListener);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_usertimeline);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mScrollListener = new EndLessOnScrollListener(playPostion,mLayoutManager,mContext,HomeFragment.this,true) {
            @Override
            public void onLoadMore() {
                LogManager.d("load:::::1",mWrapper.mInnerAdapter.getItemCount());
                getUserTimeline(mPage);
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
//        加载下一页
//        mPage++;
        WritePermission.verifystoragePermissons((Activity) mContext);
        try {
            new TimelineTask(mContext,"friendstimeline").execute(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void isPlay(int postion) {
        this.playPostion = postion;
        LogManager.d("explayer::",postion);
        if(mList!=null&&(mList.get(postion).getPicUrls().size()==0||mList.get(postion).getPicUrls()== null)){
            View view = mLayoutManager.findViewByPosition(postion);
            final LinearLayout layout = (LinearLayout) view;
            try {
                TextureView textureView = (TextureView) layout.findViewById(R.id.view_exoplayer);
                ImageView imageView = (ImageView) layout.findViewById(R.id.img_pause);
                ExoPlayerInstance instance = ExoPlayerInstance.getInstance(mContext.getApplicationContext());
                instance.getPlayer();
                Uri playerUri = Uri.parse("https://storage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%20Hangin'%20with%20the%20Google%20Search%20Bar.mp4");
                instance.setmExoPlayer(playerUri, textureView, true, imageView);
            }catch (Exception e){
                LogManager.d("isplay",e.getMessage());
            }
        }
    }

    @Override
    public void getResult(List<Status> list) {
        if (isFirst){
            if (list!=null&&list.size()!=0) {
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
                mList.addAll(list);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ExoPlayerInstance.getInstance(mContext.getApplicationContext()).releasePlayer();
    }
}
