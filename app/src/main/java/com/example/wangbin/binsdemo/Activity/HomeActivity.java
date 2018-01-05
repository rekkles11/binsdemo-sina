package com.example.wangbin.binsdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.wangbin.binsdemo.Fragment.DiscoveryFragment;
import com.example.wangbin.binsdemo.Fragment.HomeFragment;
import com.example.wangbin.binsdemo.Fragment.MessageFragment;
import com.example.wangbin.binsdemo.Fragment.MyselfFragment;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.BarManager;

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
    private DiscoveryFragment mDiscoveryFragment;
    private RelativeLayout mHomeTabRl;
    private RelativeLayout mMessageTabRl;
    private RelativeLayout mDiscoverTabRl;
    private RelativeLayout mMySelfTabRl;
    private ImageView mPostTabIv;
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
        //如果是从崩溃中恢复，还需要加载之前的缓存
        if (savedInstanceState != null) {
            restoreFragment(savedInstanceState);
        } else {
            setTabFragment(TAB_HOME_FRAGMENT);
        }
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
        mPostTabIv = (ImageView) findViewById(R.id.fl_post);
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
                setTabFragment(TAB_HOME_FRAGMENT);
                break;
            case R.id.tv_message:
                setTabFragment(TAB_MESSAGE_FRAGMENT);
                break;
            case R.id.fl_post:
                Intent intent = new Intent(HomeActivity.this, ShareActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_discovery:
                setTabFragment(TAB_DISCOVERY_FRAGMENT);
                break;
            case R.id.tv_profile:
                setTabFragment(TAB_PROFILE_FRAGMENT);
                break;
            default:
                break;
        }

    }

    /**
     * 显示指定的fragment，并且把对应的导航栏的icon设置成高亮状态
     * 注意：
     * 1. 如果选项卡已经位于当前页，则执行其他操作
     *
     * @param tabName 需要切换到的具体页面
     */
    private void setTabFragment(String tabName) {
//        if (!tabName.equals(mCurrentIndex)) {
            switchToFragment(tabName);
//        } else {
//            alreadyAtFragment(mCurrentIndex);
//        }
    }

    /**
     * 如果选项卡已经位于当前页
     * 1. 对于首页fragment，执行：滑动到顶部，并且刷新时间线，获取最新微博
     * 2. 对于消息fragment，执行：无
     * 3. 对于发现fragment，执行：无
     * 4. 对于关于我fragment，执行：无
     *
     * @param currentIndex
     */
    private void alreadyAtFragment(String currentIndex) {
        //如果在当前页
        switch (currentIndex) {
            case TAB_HOME_FRAGMENT:
//                if (mHomeFragment != null) {
//                    mHomeFragment.scrollToTop(true);
//                }
                break;
            case TAB_MESSAGE_FRAGMENT:
                break;
            case TAB_DISCOVERY_FRAGMENT:
                break;
            case TAB_PROFILE_FRAGMENT:
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
            mHomeFragment = new HomeFragment();
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
     * 切换到发现模块
     */
    private void showDiscoveryFragment() {
        mDiscoverTabRl.setSelected(true);
        if (mDiscoveryFragment == null) {
            mDiscoveryFragment = new DiscoveryFragment();
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
