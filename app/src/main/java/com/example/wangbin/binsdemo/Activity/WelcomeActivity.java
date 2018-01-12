package com.example.wangbin.binsdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.WritePermission;
import com.example.wangbin.binsdemo.auth.Constants;
import com.example.wangbin.binsdemo.auth.SelfWbAuthListener;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by momo on 2018/1/2.
 */

public class WelcomeActivity extends AppCompatActivity{
    public Intent mStartIntent;
    private SsoHandler mSsoHandler;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private MyHandler mHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        gotoActivity();
    }

    private void init() {
        com.github.lisicnu.log4android.LogManager.init(this.getApplication());
        WbSdk.install(this.getApplication(),new AuthInfo(this, Constants.APP_KEY,Constants.REDIRECT_URL,""));
        mStartIntent = new Intent(WelcomeActivity.this,HomeActivity.class);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mHandler = new MyHandler(this);
    }

    private void gotoActivity() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendMessage(Message.obtain());
            }
        };
        mTimer.schedule(mTimerTask,1000);
    }

    private static class MyHandler extends Handler{
        private WeakReference<WelcomeActivity> mActivity;
        public MyHandler(WelcomeActivity activity){
            mActivity = new WeakReference<WelcomeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (AccessTokenKeeper.readAccessToken(mActivity.get()).getToken()!=""){

                WritePermission.verifystoragePermissons(mActivity.get());
                mActivity.get().startActivity(mActivity.get().mStartIntent);
                mActivity.get().finish();
            }else{
                mActivity.get().starSsoAuthActivity();
            }
        }
    }
    private void starSsoAuthActivity(){
        mSsoHandler = new SsoHandler(this);
        mSsoHandler.authorizeWeb(new SelfWbAuthListener(this, mStartIntent));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
    private void stop(){
        if (mTimer!=null){
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
        if (mTimerTask!=null){
            mTimerTask.cancel();
            mTimerTask = null;
        }
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }
}
