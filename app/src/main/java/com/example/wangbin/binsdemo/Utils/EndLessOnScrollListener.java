package com.example.wangbin.binsdemo.Utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

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

    Context mContext;
    VideoCallBack mVideoCallBack;

    public  EndLessOnScrollListener(LinearLayoutManager linearLayoutManager, Context context){
        this.mLinearLayoutManager = linearLayoutManager;
        this.mContext = context;
    }

    public  EndLessOnScrollListener(LinearLayoutManager linearLayoutManager, Context context,VideoCallBack videoCallBack){
        this.mLinearLayoutManager = linearLayoutManager;
        this.mContext = context;
        mVideoCallBack = videoCallBack;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mLayoutManager = recyclerView.getLayoutManager();
        firstVisibleItemPostion = ((LinearLayoutManager)mLayoutManager).findFirstVisibleItemPosition();
        lastVisibleItemPosition = ((LinearLayoutManager)mLayoutManager).findLastVisibleItemPosition();
        Boolean isPlay = false;
        int postion =-1;
        for (int i =firstVisibleItemPostion;i<lastVisibleItemPosition+1;i++) {
            View view = recyclerView.getChildAt(i);
            if (view!=null) {
                int height = view.getHeight();
                int[] locations = new int[2];
                view.getLocationInWindow(locations);
                int y = locations[1];
                WindowManager wm = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
                DisplayMetrics dm = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(dm);
                int screenHeight = dm.heightPixels;
                if (y < 0) {
                    if ((height + y) * 10 / height < 5)
                        isPlay = true;
                } else {
                    if ((screenHeight - y) * 10 / height < 5)
                        isPlay = true;
                }
                if (isPlay) {
                    postion = i;
                    return;
                }
            }
        }
        if (isPlay)
        mVideoCallBack.isPlay(isPlay,postion);


        LogManager.d("itemlastcount::::",lastVisibleItemPosition);
        loading = true;

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        visibleItemCount =layoutManager.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        LogManager.d("item.count::::",visibleItemCount+"+"+totalItemCount);
        if (loading&&newState == RecyclerView.SCROLL_STATE_IDLE&&visibleItemCount>0&&
                lastVisibleItemPosition>=(totalItemCount-1)){
            loading =false;
                onLoadMore();
        }

    }





    public abstract void onLoadMore();


}
