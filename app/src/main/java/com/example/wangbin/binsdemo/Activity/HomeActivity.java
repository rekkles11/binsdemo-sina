package com.example.wangbin.binsdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.wangbin.binsdemo.Fragment.DiscoveryFragment;
import com.example.wangbin.binsdemo.Fragment.HomeFragment;
import com.example.wangbin.binsdemo.Fragment.MessageFragment;
import com.example.wangbin.binsdemo.Fragment.MyselfFragment;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.BarManager;
import com.example.wangbin.binsdemo.Utils.ExoPlayerInstance;

/**
 * Created by momo on 2018/1/2.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAB_HOME_FRAGMENT = "home";
    private static final String TAB_MESSAGE_FRAGMENT = "message";
    private static final String TAB_DISCOVERY_FRAGMENT = "discovery";
    private static final String TAB_PROFILE_FRAGMENT = "profile";


    private HomeFragment mHomeFragment;
    private MyselfFragment mMySelfFragment;
    private MessageFragment mMessageFragment;
//    private DiscoveryFragment mDiscoveryFragment;
    private HomeFragment mDiscoveryFragment;
    private RelativeLayout mHomeTabRl;
    private RelativeLayout mMessageTabRl;
    private RelativeLayout mDiscoverTabRl;
    private RelativeLayout mMySelfTabRl;
    private RelativeLayout mPostTabIv;
    private LinearLayout mButtonBarLl;
    private FragmentManager mFragmentManager;
    private String mCurrentIndex;
    private FragmentTransaction mTransaction;
    public BarManager mBarManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prepareView();
        initView();
        switchToFragment(TAB_HOME_FRAGMENT);
    }


    private void initView() {
        mBarManager = new BarManager();
        mBarManager.showBottomBar(mButtonBarLl);
        mFragmentManager = getSupportFragmentManager();

    }


    private void prepareView() {
        mHomeTabRl = (RelativeLayout) findViewById(R.id.tv_home);
        mMessageTabRl = (RelativeLayout) findViewById(R.id.tv_message);
        mDiscoverTabRl = (RelativeLayout) findViewById(R.id.tv_discovery);
        mMySelfTabRl = (RelativeLayout) findViewById(R.id.tv_profile);
        mPostTabIv = (RelativeLayout) findViewById(R.id.fl_post);
        mButtonBarLl = (LinearLayout) findViewById(R.id.buttonBarId);

        mHomeTabRl.setOnClickListener(this);
        mMessageTabRl.setOnClickListener(this);
        mPostTabIv.setOnClickListener(this);
        mDiscoverTabRl.setOnClickListener(this);
        mMySelfTabRl.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_home:
                switchToFragment(TAB_HOME_FRAGMENT);
                break;
            case R.id.tv_message:
                switchToFragment(TAB_MESSAGE_FRAGMENT);
                break;
            case R.id.fl_post:
                Intent intent = new Intent(HomeActivity.this, ShareActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_discovery:
                switchToFragment(TAB_DISCOVERY_FRAGMENT);
                break;
            case R.id.tv_profile:
                switchToFragment(TAB_PROFILE_FRAGMENT);
                break;
            default:
                break;
        }

    }

    /**
     * 如果fragment因为内存不够或者其他原因被销毁掉，在这个方法中执行恢复操作
     */
    private void restoreFragment(Bundle savedInstanceState) {
        mCurrentIndex = savedInstanceState.getString("index");
        mHomeFragment = (HomeFragment) mFragmentManager.findFragmentByTag(TAB_HOME_FRAGMENT);
        switchToFragment(mCurrentIndex);
    }

    /**
     * 执行切换fragment 的操作
     * 注意：
     * 1. 切换页面的时候，还要调用showBottomBar来保证底部导航栏的显示
     *
     * @param index
     */
    private void switchToFragment(String index) {
        mButtonBarLl.clearAnimation();
        mButtonBarLl.setVisibility(View.VISIBLE);
        mTransaction = mFragmentManager.beginTransaction();
        hideAllFragments(mTransaction);
        switch (index) {
            case TAB_HOME_FRAGMENT:
                showHomeFragment();
                break;
            case TAB_MESSAGE_FRAGMENT:
                showMessageFragment();
                break;
            case TAB_DISCOVERY_FRAGMENT:
                showDiscoveryFragment();
                break;
            case TAB_PROFILE_FRAGMENT:
                showProfileFragment();
                break;
        }
        mCurrentIndex = index;
        mTransaction.commit();
    }

    /**
     * 切换到首页模块
     */
    private void showHomeFragment() {
        mHomeTabRl.setSelected(true);
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance("friendstimeline");
            mTransaction.add(R.id.main_content_fl, mHomeFragment, TAB_HOME_FRAGMENT);
        } else {
            mTransaction.show(mHomeFragment);
        }
    }

    public void showBottomBar(){
        mBarManager.showBottomBar(mButtonBarLl);
    }
    public void hideButtonBar(){
        mBarManager.hideBottomBar(mButtonBarLl);
    }


    /**
     * 切换到关于我模块
     */
    private void showProfileFragment() {
        mMySelfTabRl.setSelected(true);
        if (mMySelfFragment == null) {
           mMySelfFragment = new MyselfFragment();
            mTransaction.add(R.id.main_content_fl, mMySelfFragment, TAB_PROFILE_FRAGMENT);
        } else {
            mTransaction.show(mMySelfFragment);

        }
    }
    /**
     * 切换到消息模块
     */
    private void showMessageFragment() {
        mMessageTabRl.setSelected(true);
        if (mMessageFragment == null) {
            mMessageFragment = new MessageFragment();
            mTransaction.add(R.id.main_content_fl, mMessageFragment, TAB_MESSAGE_FRAGMENT);
        } else {
            mTransaction.show(mMessageFragment);

        }
    }

    /**
     * 切换到热门模块
     */
    private void showDiscoveryFragment() {
        mDiscoverTabRl.setSelected(true);
        if (mDiscoveryFragment == null) {
//            mDiscoveryFragment = new DiscoveryFragment();
            mDiscoveryFragment = HomeFragment.newInstance("publictimeline");
            mTransaction.add(R.id.main_content_fl, mDiscoveryFragment, TAB_DISCOVERY_FRAGMENT);
        } else {
            mTransaction.show(mDiscoveryFragment);
        }
    }



    /**
     * 隐藏所有的fragment，并且取消所有的底部导航栏的icon的高亮状态
     *
     * @param transaction
     */
    private void hideAllFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }

        if (mMessageFragment != null) {
            transaction.hide(mMessageFragment);
        }

        if (mDiscoveryFragment != null) {
            transaction.hide(mDiscoveryFragment);
        }
        if(mMySelfFragment!= null) {
            transaction.hide(mMySelfFragment);
        }
        mHomeTabRl.setSelected(false);
        mMessageTabRl.setSelected(false);
        mDiscoverTabRl.setSelected(false);
        mMySelfTabRl.setSelected(false);
    }
}
