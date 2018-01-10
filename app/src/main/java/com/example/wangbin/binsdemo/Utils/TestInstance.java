package com.example.wangbin.binsdemo.Utils;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by momo on 2018/1/9.
 */

public class TestInstance {
    private Context mContext;
    private TextView mTextView;
    private static TestInstance mInstance = null;
    private TestInstance(Context context){
        this.mContext = context;
    }

    public static TestInstance getIntance(Context context){
        if (mInstance ==null)
            mInstance = new TestInstance(context);
        return mInstance;
    }
    public void setTextView(TextView tv){
        this.mTextView = tv;
        mTextView.setText(mContext.getString(android.R.string.ok));
    }

}
