package com.example.wangbin.binsdemo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangbin.binsdemo.Activity.UserTimeLineActivity;
import com.example.wangbin.binsdemo.Entity.User;
import com.example.wangbin.binsdemo.Model.UserCallBack;
import com.example.wangbin.binsdemo.Model.UserModel;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.Image.GlideLoader;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.Map;

/**
 * Created by momo on 2018/1/3.
 */

public class MyselfFragment extends Fragment implements UserCallBack,View.OnClickListener {

    View mView;
    User mUser;
    Context mContext;
    private ImageView mProfile_myimg;
    private TextView mProfile_mydescribe;
    private TextView mProfile_myname;
    private TextView mStatuses_count;
    private TextView mFriends_count;
    private TextView mFollowers_count;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_myself, null);
        mContext = getContext();
        initView();
        listener();
        getUserForNet();
        return mView;
    }

    private void listener() {

        mView.findViewById(R.id.yyweibo_layout).setOnClickListener(this);
        mView.findViewById(R.id.friends_layout).setOnClickListener(this);
        mView.findViewById(R.id.followers_layout).setOnClickListener(this);

    }

    private void getUserForNet() {
        Map<String, String> map = new ArrayMap<>();
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(mContext);
        map.put("access_token",token.getToken());
        map.put("uid", token.getUid());
        new UserTask().execute(map);
    }

    private void initContent() {

        if (mUser!=null) {
            new GlideLoader().displayCircleImg(mContext, mUser.getProfileImageUrl(),
                    100, 100, mProfile_myimg);
            mProfile_myname.setText(mUser.getScreenName());
            mProfile_mydescribe.setText(mUser.getDescription());
            mStatuses_count.setText(String.valueOf(mUser.getStatusesCount()));
            mFriends_count.setText(String.valueOf(mUser.getFriendsCount()));
            mFollowers_count.setText(String.valueOf(mUser.getFollowersCount()));
        }
    }

    public static MyselfFragment newInstance(User currentUser) {
        MyselfFragment mySelfFragment = new MyselfFragment();
        Bundle args = new Bundle();
        args.putParcelable("currentUser", currentUser);
        mySelfFragment.setArguments(args);
        return mySelfFragment;
    }

    private void initView() {
        mProfile_myimg = (ImageView) mView.findViewById(R.id.profile_myimg);
        mProfile_myname = (TextView) mView.findViewById(R.id.profile_myname);
        mProfile_mydescribe = (TextView) mView.findViewById(R.id.profile_mydescribe);
        mStatuses_count = (TextView) mView.findViewById(R.id.profile_statuses_count);
        mFollowers_count = (TextView) mView.findViewById(R.id.profile_followers_count);
        mFriends_count = (TextView) mView.findViewById(R.id.profile_friends_count);

    }

    @Override
    public void getUser(User user) {
        mUser = user;
        initContent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yyweibo_layout:
                Intent intent = new Intent(mContext, UserTimeLineActivity.class);
                intent.putExtra("name","usertimeline");
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    private class UserTask extends AsyncTask<Map<String, String>, Integer, Integer> {


        @Override
        protected Integer doInBackground(Map<String, String>[] maps) {
            new UserModel(mContext).getUserId(MyselfFragment.this, maps[0],"User");
            return null;
        }
    }
}
