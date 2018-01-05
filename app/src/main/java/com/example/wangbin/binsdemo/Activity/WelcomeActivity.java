package com.example.wangbin.binsdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.auth.Constants;
import com.example.wangbin.binsdemo.auth.SelfWbAuthListener;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by momo on 2018/1/2.
 */

public class WelcomeActivity extends AppCompatActivity{
    public Intent mStartIntent;
    private SsoHandler mSsoHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        gotoActivity();
    }

    private void init() {
        com.github.lisicnu.log4android.LogManager.init(this);
        WbSdk.install(this,new AuthInfo(this, Constants.APP_KEY,Constants.REDIRECT_URL,""));
        mStartIntent = new Intent(WelcomeActivity.this,HomeActivity.class);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void gotoActivity() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendMessage(Message.obtain());
            }
        },1000);

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (AccessTokenKeeper.readAccessToken(WelcomeActivity.this).getToken()!=""){
                startActivity(mStartIntent);
                finish();
            }else{
                starSsoAuthActivity();
            }

        }
    };
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


}
