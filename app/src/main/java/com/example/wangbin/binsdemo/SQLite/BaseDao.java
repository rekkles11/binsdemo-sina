package com.example.wangbin.binsdemo.SQLite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by momo on 2017/12/15.
 */

public class BaseDao {
    protected SQLiteOpenHelper mHelper;
    protected SQLiteDatabase db;
    protected Cursor mCursor;

    public BaseDao(SQLiteOpenHelper helper){
        this.mHelper = helper;
    }

    public void beiginTransByWrite(){
        db = mHelper.getWritableDatabase();
        db.beginTransaction();
    }
    public void begintTransByRead(){
        db = mHelper.getReadableDatabase();
        db.beginTransaction();
    }

    public void commit(){
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void closeCursor(){
        if(mCursor!=null){
            mCursor.close();
            mCursor = null;
        }
    }
}
