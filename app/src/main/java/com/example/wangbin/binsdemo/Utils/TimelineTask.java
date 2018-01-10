package com.example.wangbin.binsdemo.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.telecom.Call;

import com.example.wangbin.binsdemo.Activity.HomeActivity;
import com.example.wangbin.binsdemo.Activity.UserTimeLineActivity;
import com.example.wangbin.binsdemo.Activity.WeiboDataActivity;
import com.example.wangbin.binsdemo.Fragment.HomeFragment;
import com.example.wangbin.binsdemo.Model.CommentsCallBack;
import com.example.wangbin.binsdemo.Model.CommentsModel;
import com.example.wangbin.binsdemo.Model.UserCallBack;
import com.example.wangbin.binsdemo.Model.UserTimelineCallBack;
import com.example.wangbin.binsdemo.Model.UserTimelineModel;

import java.lang.ref.WeakReference;
import java.net.InterfaceAddress;
import java.util.Map;

/**
 * Created by momo on 2018/1/9.
 */

public   class TimelineTask extends AsyncTask<Map<String, String>, Integer, Object>{

    WeakReference<Context> mContext;
    String mApi;
    String mWeiboId;
    public TimelineTask(Context context,String api){
        this.mContext = new WeakReference<Context>(context);
        this.mApi = api;
    }
    public TimelineTask(Context context,String api,String weiboId){
        this.mContext = new WeakReference<Context>(context);
        this.mApi = api;
        this.mWeiboId = weiboId;
    }

    @Override
    protected Object doInBackground(Map<String, String>[] maps) {
        if (mApi.equals("comments"))
            new CommentsModel(mContext.get()).getComments(maps[0],"comments",(CommentsCallBack)((WeiboDataActivity)mContext.get()),mWeiboId);
        else if (mApi.equals("friendstimeline")) {
            Fragment fragment = ((HomeActivity) (mContext.get())).getSupportFragmentManager().findFragmentByTag("home");
            new UserTimelineModel(mContext.get()).getStatus(maps[0], (HomeFragment)fragment, mApi);
        }
        else if (mApi.equals("publictimeline")||mApi.equals("usertimeline"))
            new UserTimelineModel(mContext.get()).getStatus(maps[0],(UserTimelineCallBack) ((UserTimeLineActivity)(mContext.get())),mApi);
        return null;
    }
}
