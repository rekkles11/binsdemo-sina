package com.example.wangbin.binsdemo.Utils;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created by momo on 2018/1/9.
 */

public class AppApplication extends Application {
    private RefWatcher mRefWatcher;
//
//    public static RefWatcher getWatcher(Context context) {
//        AppApplication application = (AppApplication) context.getApplicationContext();
//
//        return application.mRefWatcher;
//    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this))
            return;
        mRefWatcher = LeakCanary.install(this);
    }

}
