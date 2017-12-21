package com.example.wangbin.binsdemo.Model;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.Entity.UserTimelineReponse;
import com.example.wangbin.binsdemo.SQLite.OrderDBHelper;
import com.example.wangbin.binsdemo.SQLite.OrderDao;
import com.example.wangbin.binsdemo.Service.Api;
import com.example.wangbin.binsdemo.Service.NetWork;
import com.github.lisicnu.log4android.LogManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by momo on 2017/12/12.
 */

public class UserTimelineModel {
    private Context mContext;
    private List<Status> mStatusList;
    public UserTimelineModel(Context context){
        this.mContext = context;
    }

    public void getStatus(Map<String, String> map, final UserTimelineCallBack callBack) throws IOException {
        Api api = NetWork.getInstance().getApi();
        Call<UserTimelineReponse> userTimelineCall = api.getUserTimeline(map);
        userTimelineCall.enqueue(new Callback<UserTimelineReponse>() {
            @Override
            public void onResponse(Call<UserTimelineReponse> call, Response<UserTimelineReponse> response) {
                mStatusList = response.body().getStatuses();
                for(int i =0;i<mStatusList.size();i++){
                    String source = mStatusList.get(i).getSource();
                    source = "来自 "+source.substring(source.indexOf(">")+1,source.lastIndexOf("<"));
                    mStatusList.get(i).setSource(source);
                    mStatusList.get(i).setCreatedAt(getcreatedAt(mStatusList.get(i).getCreatedAt()));
                }
                saveToDB();
                callBack.getResult(mStatusList);


                LogManager.d("getUserTimeline",response.body());
            }

            @Override
            public void onFailure(Call<UserTimelineReponse> call, Throwable t) {
                LogManager.d("getUserTimeline",t.getMessage());
            }
        });

    }
    public String  getcreatedAt(String createdAt){
        int index = createdAt.indexOf(" ")+1;
        return createdAt.substring(index,index +3)+"-"+createdAt.substring(index+4,index+6)+"   ";
    }
    public void saveToDB(){
        OrderDao dao = new OrderDao(mContext);
        Status status = null;
        for(int i = 0;i<mStatusList.size();i++){
            if(!dao.textIsExist(mStatusList.get(i).getText())) {
                status = mStatusList.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("createat", status.getCreatedAt());
                contentValues.put("source", status.getSource());
                contentValues.put("text", status.getText());
                contentValues.put("reposts", status.getRepostsCount());
                contentValues.put("comments", status.getCommentsCount());
                contentValues.put("attltudes", status.getAttitudesCount());
                dao.insert(OrderDBHelper.TABLE_NAME, contentValues);
            }

//            dao.save(status);
        }
//        dao.get();

        dao.getAllData();
    }

    public List<Status> getStatusList() {
        return mStatusList;
    }

    public void setStatusList(List<Status> statusList) {
        mStatusList = statusList;
    }
}
