package com.example.wangbin.binsdemo;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.wangbin.binsdemo.Activity.FriendsIdsActivity;
import com.example.wangbin.binsdemo.Activity.ShareActivity;
import com.example.wangbin.binsdemo.Activity.UserTimeLineActivity;
import com.example.wangbin.binsdemo.auth.Constants;
import com.example.wangbin.binsdemo.auth.SelfWbAuthListener;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SsoHandler mSsoHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        com.github.lisicnu.log4android.LogManager.init(this);

        WbSdk.install(this,new AuthInfo(this, Constants.APP_KEY,Constants.REDIRECT_URL,""));
        initEvents();
        gotoActivity();

    }

    private void gotoActivity() {
        if (AccessTokenKeeper.readAccessToken(MainActivity.this).getToken()!=""){
            startActivity(new Intent(MainActivity.this,UserTimeLineActivity.class));
        }else{
            starSsoAuthActivity();
        }
    }

    private void initEvents(){
        findViewById(R.id.btn_auth).setOnClickListener(this);
        findViewById(R.id.btn_usertimeline).setOnClickListener(this);
        findViewById(R.id.bt_friendsids).setOnClickListener(this);
        findViewById(R.id.bt_shareweibo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_auth:
                starSsoAuthActivity();
                break;
            case R.id.btn_usertimeline:
                startActivity(new Intent(MainActivity.this, UserTimeLineActivity.class));
                break;
            case R.id.bt_friendsids:
                startActivity(new Intent(MainActivity.this, FriendsIdsActivity.class));
                break;
            case R.id.bt_shareweibo:
                startActivity(new Intent(MainActivity.this, ShareActivity.class));
                break;
        }
    }

    private void starSsoAuthActivity(){
        mSsoHandler = new SsoHandler(this);

        mSsoHandler.authorizeWeb(new SelfWbAuthListener(this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }

}
