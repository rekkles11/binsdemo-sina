package com.example.wangbin.binsdemo.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
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

public class HomeFragment extends Fragment implements UserTimelineCallBack{

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
    int mPage =1;
    public EndLessOnScrollListener mScrollListener;
    private RefreshListener mRefreshListener;
    private String mFrom;
    ExoPlayerInstance mInstance;


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
        mFrom = getArguments().getString("comeFrom");
        map = new HashMap<>();
        map.put("access_token", AccessTokenKeeper.readAccessToken(mContext).getToken());
        map.put("count","10");
        mInstance = ExoPlayerInstance.getInstance(mContext.getApplicationContext());
    }
    public void getUserTimeline(int page) {
        map.put("page", String.valueOf(page));
//        加载下一页
//        mPage++;
        WritePermission.verifystoragePermissons((Activity) mContext);
        try {
            new TimelineTask(mContext,mFrom).execute(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 静态工厂方法需要一个int型的值来初始化fragment的参数，
     * 然后返回新的fragment到调用者
     */
    public static HomeFragment newInstance(String from) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("comeFrom", from);
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    public void getResult(List<Status> list) {
        if (isFirst){
            if (list!=null&&list.size()!=0) {
                mList = (list);
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
        mScrollListener = new EndLessOnScrollListener(mList,mContext,true) {
            @Override
            public void onLoadMore() {
                getUserTimeline(mPage);
            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);

    }

    class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            isFirst = true;
            getUserTimeline(mPage);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mInstance.releasePlayer();
    }
}
