package com.example.wangbin.binsdemo.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wangbin.binsdemo.Activity.HomeActivity;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.R;
import com.github.lisicnu.log4android.LogManager;

import java.util.List;

/**
 * Created by momo on 2017/12/22.
 */

public abstract class EndLessOnScrollListener extends RecyclerView.OnScrollListener {

    //子item的总数
    private int totalItemCount;
    //第一个显示的item位置
    private int firstVisibleItemPostion;
    //最后一个item的位置
    private int lastVisibleItemPosition;
    private Boolean loading = true;
    //记录滑动的距离
    int mScrolledDistance =0;
    Boolean mBarVisible = true;

    Context mContext;
    int playPostion = 0;
    int perPostion;
    Boolean isHome;
    Boolean isWeiboData;
    private List<Status> mList;

    //普通的加载更多
    public EndLessOnScrollListener( Context context) {
        this.mContext = context;
        this.isWeiboData = true;
    }
    //带视频的加载更多
    public EndLessOnScrollListener(List<Status> list, Context context, Boolean bool) {
        this.mContext = context;
        this.mList = list;
        this.isHome = bool;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        firstVisibleItemPostion = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        View view = layoutManager.findViewByPosition(playPostion);
        if (isHome!=null) {
            //判断视频的播放
            if (view != null) {
                int height = view.getHeight();
                int[] locations = new int[2];
                view.getLocationInWindow(locations);
                int y = locations[1];
                if (mContext != null) {
                    WindowManager wm = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
                    DisplayMetrics dm = new DisplayMetrics();
                    wm.getDefaultDisplay().getMetrics(dm);
                    int screenHeight = dm.heightPixels;
                    if (y < 0) {
                        if ((height + y) * 10 / height < 4) {
                            playPostion++;
                            LogManager.d("1playerpostion::",playPostion);
                            finishVideo(layoutManager);
                        }
                    } else {
                        if ((screenHeight - y) * 10 / height < 4) {
                            playPostion--;
                            finishVideo(layoutManager);
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

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        totalItemCount = layoutManager.getItemCount();
        LogManager.d("21item.count::::", firstVisibleItemPostion + "+" + lastVisibleItemPosition + "+" + totalItemCount);

        if (isHome!=null){
            LogManager.d("exoplayer:::", playPostion + "+" + perPostion);
            if (playPostion != perPostion || playPostion == 0 || playPostion < firstVisibleItemPostion) {
                if (playPostion < firstVisibleItemPostion)
                    playPostion = firstVisibleItemPostion;
                startVideo(layoutManager);
            }
        }

        //微博详情视频播放
        if (isWeiboData!=null){

            startVideo(layoutManager);
        }

        if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                lastVisibleItemPosition >= (totalItemCount - 1)) {
            onLoadMore();
        }

    }

    private void startVideo(RecyclerView.LayoutManager manager){

        try {
            if (mList != null && mList.get(playPostion).getPicUrls().size() == 0||
                    isWeiboData!=null&&firstVisibleItemPostion ==0) {
                perPostion = playPostion;
                LogManager.d("playerpostion:::",playPostion);
                View view = manager.findViewByPosition(playPostion);

                TextureView textureView;
                ImageView imageView;
                if (view!=null) {
                    textureView = (TextureView) ((LinearLayout) view).findViewById(R.id.view_exoplayer);
                    textureView.setVisibility(View.VISIBLE);
                    imageView = (ImageView) ((LinearLayout) view).findViewById(R.id.img_pause);
                    ExoPlayerInstance instance = ExoPlayerInstance.getInstance(mContext.getApplicationContext());
                    instance.getPlayer();
                    Uri playerUri = Uri.parse("https://storage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%20Hangin'%20with%20the%20Google%20Search%20Bar.mp4");
                    instance.setmExoPlayer(playerUri, textureView, true, imageView);
                    loading =true;
                }
            }
        }catch (Exception e){
            LogManager.d("isplay",e.getMessage());
        }

    }


    private void finishVideo(RecyclerView.LayoutManager manager){
        TextureView textureView;
        ImageView imageView;
        ExoPlayerInstance.getInstance(mContext).releasePlayer();
        try {
            if (loading) {
                if (playPostion != 0 && playPostion != perPostion) {
                    View perView = manager.findViewByPosition(perPostion);
                    if (perView != null) {
                        textureView = (TextureView) ((LinearLayout) perView).findViewById(R.id.view_exoplayer);
                        textureView.setVisibility(View.GONE);
                        imageView = (ImageView) ((LinearLayout) perView).findViewById(R.id.img_pause);
                        imageView.setImageResource(R.drawable.video_pause);
                        imageView.setVisibility(View.VISIBLE);
                        loading = false;
                    }
                }

            }
        }catch (Exception e){
            LogManager.d("isplay",e.getMessage());
        }
    }


    public abstract void onLoadMore();




}
