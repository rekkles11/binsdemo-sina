package com.example.wangbin.binsdemo.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by momo on 2017/12/26.
 */

public class GsonConverter<T> {
//    T entity;
    static GsonBuilder mGsonBuilder;
    static Gson mGson;
    public GsonConverter(){
        mGsonBuilder = new GsonBuilder();
        mGson = mGsonBuilder.create();
    }

    public static <T> T getObject(String json,Class<T> cls){
        return mGson.fromJson(json,cls);
//        return entity;
    }

    public String getJson(T entity){
       return mGson.toJson(entity);
    }
}
