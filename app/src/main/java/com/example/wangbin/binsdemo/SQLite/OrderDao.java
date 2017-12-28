package com.example.wangbin.binsdemo.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.Utils.GsonConverter;
import com.github.lisicnu.log4android.LogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 2017/12/15.
 */

public class OrderDao extends BaseDao{
    private static final String TAG = "orderDao";
    private final String[] ORDER_COLUMNS = new String[]{"Id","json","types"};
    public OrderDao(Context context){
        super(new OrderDBHelper(context));
    }

    public boolean isDataExist(){
        int count = 0;
        begintTransByRead();
        Cursor cursor = db.query(OrderDBHelper.TABLE_NAME,new String[]{"COUNT(id)"},
                null,null,null,null,null);
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        if (count>0)
            return true;

        closeCursor();
        commit();
        return false;
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


    public List<Status> getAllData(){
        List<Status> list = null;
        begintTransByRead();
        mCursor = db.query(OrderDBHelper.TABLE_NAME,ORDER_COLUMNS,null,null,null,null,null);
        if(mCursor.getCount()>0){
            list = new ArrayList<>();
            int count = mCursor.getCount();
            GsonConverter gsonConverter = new GsonConverter<Status>();
            while (mCursor.moveToNext()){
                String str = mCursor.getString(mCursor.getColumnIndex("json"));
                list.add(gsonConverter.getObject(str,Status.class));
            }
            LogManager.d("successful",count);
        }else {
            LogManager.d("error","error");
        }
        closeCursor();
        commit();
        return list;
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
