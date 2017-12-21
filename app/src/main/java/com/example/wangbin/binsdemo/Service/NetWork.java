package com.example.wangbin.binsdemo.Service;

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
                    Retrofit retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl("https://api.weibo.com/2/")
                            .build();
                    mApi = retrofit.create(Api.class);
                }
            }
        }
        return mApi;
    }

}
