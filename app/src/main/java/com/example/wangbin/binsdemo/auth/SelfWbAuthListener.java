package com.example.wangbin.binsdemo.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.github.lisicnu.log4android.LogManager;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;


/**
 * Created by momo on 2017/12/11.
 */

public class SelfWbAuthListener implements WbAuthListener {

    private Context mContext;
    Intent mIntent;

    public SelfWbAuthListener(Activity context, Intent intent){
        this.mContext = context;
        this.mIntent = intent;
    }

    @Override
    public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
        AccessTokenKeeper.writeAccessToken(mContext,oauth2AccessToken);
        mContext.startActivity(mIntent);
        ((Activity)mContext).finish();

    }

    @Override
    public void cancel() {
        LogManager.d("weibo", "cancel");
    }

    @Override
    public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
        LogManager.d("weibo", "error:" + wbConnectErrorMessage.getErrorMessage() + "-" + wbConnectErrorMessage.getErrorCode());
    }
}
