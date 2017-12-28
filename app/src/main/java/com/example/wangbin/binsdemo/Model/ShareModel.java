package com.example.wangbin.binsdemo.Model;

import com.example.wangbin.binsdemo.Service.Api;
import com.example.wangbin.binsdemo.Service.NetWork;
import com.github.lisicnu.log4android.LogManager;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

/**
 * Created by momo on 2017/12/18.
 */

public class ShareModel {

    public void postShare(Map<String,String> map, List<File> files, final ShareCallBack callBack){
            Api api = NetWork.getInstance().getApi();
        Call<Object> call = null;
        if(files != null) {
            MultipartBody.Part[] pics = new MultipartBody.Part[files.size()];
            for (int i =0;i<files.size();i++){
                RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), files.get(i));
                MultipartBody.Part pic = MultipartBody.Part.createFormData("pic", files.get(i).getName(), body);
                pics[i] = pic;
            }
            RequestBody token = RequestBody.create(MediaType.parse("text/form-plain"), map.get("access_token"));
            RequestBody status = RequestBody.create(MediaType.parse("text/form-plain"), map.get("status"));

            call = api.postShareImage(token,status, pics);
        }else {
            call = api.postShareText(map);

        }

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                callBack.isPostSuccessful(true);
                LogManager.d("sharemodel","response"+"-"+response.code());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                callBack.isPostSuccessful(false);
                LogManager.d("sharemodel","failure"+"-"+t.getMessage());
            }
        });

    }
}
