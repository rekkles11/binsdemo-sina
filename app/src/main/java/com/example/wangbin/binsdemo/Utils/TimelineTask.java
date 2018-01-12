package com.example.wangbin.binsdemo.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.telecom.Call;

import com.example.wangbin.binsdemo.Activity.HomeActivity;
import com.example.wangbin.binsdemo.Activity.ShareActivity;
import com.example.wangbin.binsdemo.Activity.UserTimeLineActivity;
import com.example.wangbin.binsdemo.Activity.WeiboDataActivity;
import com.example.wangbin.binsdemo.Fragment.HomeFragment;
import com.example.wangbin.binsdemo.Model.CommentsCallBack;
import com.example.wangbin.binsdemo.Model.CommentsModel;
import com.example.wangbin.binsdemo.Model.ShareModel;
import com.example.wangbin.binsdemo.Model.UserCallBack;
import com.example.wangbin.binsdemo.Model.UserTimelineCallBack;
import com.example.wangbin.binsdemo.Model.UserTimelineModel;

import java.io.File;
import java.lang.ref.WeakReference;
import java.net.InterfaceAddress;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * Created by momo on 2018/1/9.
 */

public   class TimelineTask extends AsyncTask<Map<String, String>, Integer, Object>{

    WeakReference<Context> mContext;
    String mApi;
    String mWeiboId;
    List<File> mFiles;
    public TimelineTask(Context context,String api){
        this.mContext = new WeakReference<Context>(context);
        this.mApi = api;
    }
    public TimelineTask(Context context,String api,String weiboId){
        this.mContext = new WeakReference<Context>(context);
        this.mApi = api;
        this.mWeiboId = weiboId;
    }
    public TimelineTask(Context context,String api,List<File> files){
        this.mContext = new WeakReference<Context>(context);
        this.mApi = api;
        this.mFiles = files;

    }

    @Override
    protected Object doInBackground(Map<String, String>[] maps) {
        if (mApi.equals("comments"))
            new CommentsModel(mContext.get()).getComments(maps[0],"comments",(CommentsCallBack)((WeiboDataActivity)mContext.get()),mWeiboId);
        else if (mApi.equals("friendstimeline")) {
            Fragment fragment = ((HomeActivity) (mContext.get())).getSupportFragmentManager().findFragmentByTag("home");
            new UserTimelineModel(mContext.get()).getStatus(maps[0], (HomeFragment)fragment, mApi);
        }else if (mApi.equals("publictimeline")){
            Fragment fragment = ((HomeActivity) (mContext.get())).getSupportFragmentManager().findFragmentByTag("discovery");
            new UserTimelineModel(mContext.get()).getStatus(maps[0], (HomeFragment)fragment, mApi);
        }
        else if (mApi.equals("usertimeline"))
            new UserTimelineModel(mContext.get()).getStatus(maps[0],(UserTimelineCallBack) ((UserTimeLineActivity)(mContext.get())),mApi);
        else if (mApi.equals("share")){
            if (mFiles==null)
                new ShareModel().postShare(maps[0], null,(ShareActivity)mContext.get());
            else
                new ShareModel().postShare(maps[0],mFiles,(ShareActivity)mContext.get());
        }
        return null;
    }
}
