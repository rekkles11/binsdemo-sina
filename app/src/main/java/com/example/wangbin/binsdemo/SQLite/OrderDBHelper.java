package com.example.wangbin.binsdemo.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by momo on 2017/12/12.
 */

public class OrderDBHelper extends SQLiteOpenHelper{
    public final static int mVersion = 1;
    public final static String mDbName = "binsdemo.db";
    public final static String TABLE_NAME ="weibodeta";
    public OrderDBHelper(Context context){
        super(context,mDbName,null,mVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table if not exists "+ TABLE_NAME+"("+
        "Id integer primary key, "+ "json text, "+"types text,"+"weiboId text"+");";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion ==1) {
            String sql = "DROP TABLE IF EXISTS " + "TABLE_NAME";
//            String sql = "create table if not exists "+ TABLE_NAME+"("+
//                    "Id integer primary key, "+ "json text, "+"types text"+");";
            db.execSQL(sql);
            onCreate(db);
        }
    }
}
