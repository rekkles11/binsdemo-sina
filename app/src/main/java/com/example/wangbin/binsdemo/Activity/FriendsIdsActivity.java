package com.example.wangbin.binsdemo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.wangbin.binsdemo.Entity.FriendsIds;
import com.example.wangbin.binsdemo.Model.FriendsIdsModel;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Service.NetWork;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by momo on 2017/12/13.
 */

public class FriendsIdsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendsids);
        getFriendsIds();
    }
    public void getFriendsIds(){
        Map<String,String> map = new HashMap<>();
        map.put("access_token", AccessTokenKeeper.readAccessToken(this).getToken());
        FriendsIdsModel friendsIdsModel = new FriendsIdsModel();
        friendsIdsModel.getFriendsIdsFromNet(map);

    }
}
