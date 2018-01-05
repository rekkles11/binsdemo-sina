package com.example.wangbin.binsdemo.Utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.example.wangbin.binsdemo.Activity.HomeActivity;
import com.example.wangbin.binsdemo.Fragment.HomeFragment;
import com.github.lisicnu.log4android.LogManager;

/**
 * Created by momo on 2017/12/22.
 */

public abstract class EndLessOnScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLinearLayoutManager;
    RecyclerView.LayoutManager mLayoutManager;
    //子item的总数
    private int totalItemCount;
    //item显示的数量
    private int visibleItemCount;
    //第一个显示的item位置
    private int firstVisibleItemPostion;
    //最后一个item的位置
    private int lastVisibleItemPosition;
    private Boolean loading = false;
    //记录滑动的距离
    int mScrolledDistance =0;
    Boolean mBarVisible = true;

    Context mContext;
    VideoCallBack mVideoCallBack;
    int playPostion = 0;
    int perPostion;
    Boolean isHome =false;


    public EndLessOnScrollListener(int pos, LinearLayoutManager linearLayoutManager, Context context, VideoCallBack videoCallBack) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.mContext = context;
        mVideoCallBack = videoCallBack;
        this.playPostion = pos;
        this.perPostion = pos;
    }
    public EndLessOnScrollListener(int pos, LinearLayoutManager linearLayoutManager, Context context, VideoCallBack videoCallBack,Boolean bool) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.mContext = context;
        mVideoCallBack = videoCallBack;
        this.playPostion = pos;
        this.perPostion = pos;
        isHome = bool;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mLayoutManager = recyclerView.getLayoutManager();
        firstVisibleItemPostion = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        View view = mLayoutManager.findViewByPosition(playPostion);

        //判断视频的播放
        if (view != null) {
            int height = view.getHeight();
            int[] locations = new int[2];
            view.getLocationInWindow(locations);
            int y = locations[1];
            if (mContext!=null) {
                WindowManager wm = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
                DisplayMetrics dm = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(dm);
                int screenHeight = dm.heightPixels;
                if (y < 0) {
                    if ((height + y) * 10 / height < 4) {
                        ExoPlayerInstance.getInstance().releasePlayer();
                        playPostion++;
                    }
                } else {
                    if ((screenHeight - y) * 10 / height < 4) {
                        ExoPlayerInstance.getInstance().releasePlayer();
                        playPostion--;
                    }
                }
            }
        }

        //判断底部bar的情况
        if (isHome) {
            //手指向上滑动
            if (mScrolledDistance > 80 && mBarVisible) {
                ((HomeActivity) mContext).hideButtonBar();
                mBarVisible = false;
                mScrolledDistance = 0;
            }
            //手指向下滑动
            else if (mScrolledDistance < -80 && !mBarVisible) {
                ((HomeActivity) mContext).showBottomBar();
                mBarVisible = true;
                mScrolledDistance = 0;
            }
            if ((mBarVisible && dy > 0) || (!mBarVisible && dy < 0)) {
                mScrolledDistance += dy;
            }
        }



    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        visibleItemCount = layoutManager.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        LogManager.d("item.count::::", firstVisibleItemPostion + "+" + lastVisibleItemPosition + "+" + totalItemCount);
        if (playPostion!=perPostion || playPostion == 0) {
            LogManager.d("exoplayer:::",playPostion+"+"+perPostion);
            mVideoCallBack.isPlay(playPostion);
            perPostion = playPostion;
        }
        if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                lastVisibleItemPosition >= (totalItemCount - 1)) {
            onLoadMore();
        }

    }


    public abstract void onLoadMore();




}
