package com.example.wangbin.binsdemo.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wangbin.binsdemo.Entity.Status;
import com.github.lisicnu.log4android.LogManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by momo on 2017/12/15.
 */

public class OrderDao extends BaseDao{
    private static final String TAG = "orderDao";
    private final String[] ORDER_COLUMNS = new String[]{"createat","source","text","reposts","comments","attltudes"};
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
    public void insert(String name ,ContentValues contentValues){
        beiginTransByWrite();
        db.insertOrThrow(name,null,contentValues);
        commit();
    }

    public void save(Status status){
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        beiginTransByWrite();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(status);
            objectOutputStream.flush();
            byte data[] = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
            String sql = "insert into usertimeline (statusdata) values(?)";
            db.execSQL(sql,new Object[]{data});
            commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Status get(){
        Status status = null;
        begintTransByRead();
        mCursor = db.rawQuery("select * from usertimeline",null);
        if(mCursor!=null){
            while(mCursor.moveToNext()){
                byte data[] = mCursor.getBlob(mCursor.getColumnIndex("statusdata"));
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                    status = (Status) inputStream.readObject();
                    inputStream.close();
                    arrayInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        commit();
        return status;

    }

    public void execSql(String sql){

        if(sql.contains("select")){

        }else if(sql.contains("insert")||sql.contains("update")||sql.contains("delete")){
            beiginTransByWrite();
            db.execSQL(sql);
        }
        commit();

    }

    public void getAllData(){
        begintTransByRead();
        mCursor = db.query(OrderDBHelper.TABLE_NAME,ORDER_COLUMNS,null,null,null,null,null);
        if(mCursor.getCount()>0){
            int count = mCursor.getCount();
            LogManager.d("successful",count);
        }else {
            LogManager.d("error","error");
        }
        closeCursor();
        commit();

    }
    public Boolean textIsExist(String text){
        begintTransByRead();
        mCursor = db.query(OrderDBHelper.TABLE_NAME,ORDER_COLUMNS,"text = ?",new String[]{text},null,null,null);
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
