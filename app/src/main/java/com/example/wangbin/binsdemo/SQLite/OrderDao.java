package com.example.wangbin.binsdemo.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.wangbin.binsdemo.Entity.Comment;
import com.example.wangbin.binsdemo.Entity.Comments;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.Entity.User;
import com.example.wangbin.binsdemo.Utils.GsonConverter;
import com.github.lisicnu.log4android.LogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 2017/12/15.
 */

public class OrderDao extends BaseDao{
    private static final String TAG = "orderDao";
    private final String[] ORDER_COLUMNS = new String[]{"Id","json","types","weiboId"};
    public OrderDao(Context context){
        super(new OrderDBHelper(context));
    }


    public void insert(String name ,ContentValues contentValues,String id){
        if (!idIsExist(id)) {
            beiginTransByWrite();
            db.insertOrThrow(name, null, contentValues);
        }
        else {
            beiginTransByWrite();
            db.replace(name, null, contentValues);
        }
        commit();
    }


    public <T> List<T> getAllData(String type,String weiboId, Class<T> cla){
        List<T> list = new ArrayList<>();
        begintTransByRead();
        if (weiboId != null)
            mCursor = db.query(OrderDBHelper.TABLE_NAME,ORDER_COLUMNS,
                    "types = ? AND weiboId =?",new String[]{type,weiboId},
                    null,null,null);
        else
            mCursor = db.query(OrderDBHelper.TABLE_NAME,ORDER_COLUMNS,
                    "types = ?",new String[]{type},
                    null,null,null);

        if(mCursor.getCount()>0){
            int count = mCursor.getCount();
            GsonConverter gsonConverter = new GsonConverter<Status>();
            while (mCursor.moveToNext()){
                String str = mCursor.getString(mCursor.getColumnIndex("json"));
                list.add( gsonConverter.getObject(str,cla));
            }
            LogManager.d("successful",count);
        }else {
            LogManager.d("error","error");
        }
        closeCursor();
        commit();

        return list;
    }
    public <T> T getData(String type,Class<T> cla){
        T t = null;
        begintTransByRead();
        mCursor = db.query(OrderDBHelper.TABLE_NAME,ORDER_COLUMNS,"types = ?",new String[]{type},null,null,null);
        if(mCursor.getCount()>0){
            int count = mCursor.getCount();
            GsonConverter gsonConverter = new GsonConverter<Status>();
            while (mCursor.moveToNext()){
                String str = mCursor.getString(mCursor.getColumnIndex("json"));
                t = gsonConverter.getObject(str,cla);
            }
            LogManager.d("successful",count);
        }else {
            LogManager.d("error","error");
        }
        closeCursor();
        commit();
        return t;
    }
    public Boolean idIsExist(String id){
        begintTransByRead();
        mCursor = db.query(OrderDBHelper.TABLE_NAME,ORDER_COLUMNS,"Id = ?",
                new String[]{id},null,null,null);
        if(mCursor.getCount()>0) {
            closeCursor();
            commit();
            return true;
        } else {
            closeCursor();
            commit();
            return false;
        }
    }

}
