package com.example.wangbin.binsdemo.Service;

import android.text.TextUtils;

import com.example.wangbin.binsdemo.BuildConfig;
import com.example.wangbin.binsdemo.Utils.LoggingInterceptor;
import com.github.lisicnu.log4android.LogManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by momo on 2017/12/12.
 */

public class NetWork {
    private static volatile NetWork mInstance;
    private Api mApi;

    private NetWork() {
    }

    public static NetWork getInstance() {
        if (mInstance == null) {
            synchronized (NetWork.class) {
                if (mInstance == null) {
                    mInstance = new NetWork();
                }
            }
        }
        return mInstance;
    }

    public Api getApi(){
        if(mApi==null){
            synchronized (NetWork.class){
                if(mApi == null){
                    OkHttpClient.Builder client = new OkHttpClient.Builder()
                            .addInterceptor(new LoggingInterceptor())
                            .retryOnConnectionFailure(true);

                    if (BuildConfig.DEBUG){
                        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                            @Override
                            public void log(String message) {
                                if (TextUtils.isEmpty(message))
                                    return;
                                String s = message.substring(0,1);
                                if ("{".equals(s)||"[".equals(s))
                                    LogManager.d("收到响应：",message);

                            }
                        });
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    }
                    Retrofit retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl("https://api.weibo.com/2/")
                            .client(client.build())
                            .build();
                    mApi = retrofit.create(Api.class);
                }
            }
        }
        return mApi;
    }

}
