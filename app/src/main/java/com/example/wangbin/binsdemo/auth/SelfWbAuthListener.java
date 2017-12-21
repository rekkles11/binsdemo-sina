package com.example.wangbin.binsdemo.auth;

import android.app.Activity;
import android.content.Context;

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

    public SelfWbAuthListener(Activity context){
        this.mContext = context;
    }

    @Override
    public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
        AccessTokenKeeper.writeAccessToken(mContext,oauth2AccessToken);
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
