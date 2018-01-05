package com.example.wangbin.binsdemo.Model;

import android.content.ContentValues;
import android.content.Context;

import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.Entity.User;
import com.example.wangbin.binsdemo.SQLite.OrderDBHelper;
import com.example.wangbin.binsdemo.SQLite.OrderDao;
import com.example.wangbin.binsdemo.Service.Api;
import com.example.wangbin.binsdemo.Service.NetWork;
import com.example.wangbin.binsdemo.Utils.GsonConverter;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by momo on 2018/1/4.
 */

public class UserModel {

    Context mContext;
    public UserModel(Context context){
        this.mContext = context;
    }

    public void getUserId(final UserCallBack callBack, Map<String, String> map, final String apiStr){

        Api api = NetWork.getInstance().getApi();
        Call<User> call = api.getUserId(map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    callBack.getUser(response.body());
                    saveToDB(apiStr,response.body());
                }else {
                    callBack.getUser(new OrderDao(mContext).getUserData(apiStr));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callBack.getUser(new OrderDao(mContext).getUserData(apiStr));
            }
        });



    }


    public void saveToDB(String apiStr,User user){
        OrderDao dao = new OrderDao(mContext);
            ContentValues contentValues = new ContentValues();
            contentValues.put("Id", user.getId());
            contentValues.put("json", new GsonConverter().getJson(user));
            contentValues.put("types", apiStr);
            dao.insert(OrderDBHelper.TABLE_NAME, contentValues,String.valueOf(user.getId()));

        }

}
