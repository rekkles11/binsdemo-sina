package com.example.wangbin.binsdemo.Model;

import android.content.ContentValues;
import android.content.Context;

import com.example.wangbin.binsdemo.Entity.Comment;
import com.example.wangbin.binsdemo.Entity.Comments;
import com.example.wangbin.binsdemo.SQLite.OrderDBHelper;
import com.example.wangbin.binsdemo.SQLite.OrderDao;
import com.example.wangbin.binsdemo.Service.Api;
import com.example.wangbin.binsdemo.Service.NetWork;
import com.example.wangbin.binsdemo.Utils.GsonConverter;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by momo on 2018/1/4.
 */

public class CommentsModel {

    Context mContext;
    List<Comments> mList;
    public CommentsModel(Context context){
        this.mContext = context;
    }

    public void getComments(Map<String, String> map, final String apiStr, final CommentsCallBack callBack, final String weiboId){

        Api api = NetWork.getInstance().getApi();
        Call<Comment> call = api.getComments(map);

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.code() ==200) {
                    mList = response.body().getComments();
                    for (int i = 0; i < mList.size(); i++) {
                        String createdAt = mList.get(i).getCreatedAt();
                        if (!createdAt.equals("")) {
                            mList.get(i).setCreatedAt(getcreatedAt(createdAt));
                        }
                    }
                    saveToDB(apiStr);
                }else
                    mList = new OrderDao(mContext).getAllData(apiStr,weiboId,Comments.class);
                callBack.getComments(mList);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }

    public String  getcreatedAt(String createdAt){
        int index = createdAt.indexOf(" ")+1;
        return createdAt.substring(index,index +3)+"-"+createdAt.substring(index+4,index+6)+"   ";
    }

    public void saveToDB(String apiStr){
        OrderDao dao = new OrderDao(mContext);
        for(int i = 0;i<mList.size();i++){
            Comments comments = mList.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put("Id", comments.getId());
            contentValues.put("json", new GsonConverter().getJson(comments));
            contentValues.put("types",apiStr );
            contentValues.put("weiboId",comments.getStatus().getIdstr());

            dao.insert(OrderDBHelper.TABLE_NAME, contentValues,String.valueOf(comments.getId()));

        }
    }
}
