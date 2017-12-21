package com.example.wangbin.binsdemo.Model;

import com.example.wangbin.binsdemo.Service.Api;
import com.example.wangbin.binsdemo.Service.NetWork;
import com.github.lisicnu.log4android.LogManager;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by momo on 2017/12/18.
 */

public class ShareModel {

    public void postShare(Map<String,String> map, File file, final ShareCallBack callBack){
            Api api = NetWork.getInstance().getApi();
        Call<Object> call = null;
        if(file != null) {
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part pic = MultipartBody.Part.createFormData("pic", file.getName(), body);
            RequestBody token = RequestBody.create(MediaType.parse("text/form-plain"), map.get("access_token"));
            RequestBody status = RequestBody.create(MediaType.parse("text/form-plain"), map.get("status"));

            call = api.postShareImage(token,status, pic);
        }else {
            call = api.postShareText(map);

        }

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                callBack.isPostSuccessful(true);
                LogManager.d("sharemodel","response"+"-"+response.body());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                callBack.isPostSuccessful(false);
                LogManager.d("sharemodel","failure"+"-"+t.getMessage());
            }
        });

    }
}
