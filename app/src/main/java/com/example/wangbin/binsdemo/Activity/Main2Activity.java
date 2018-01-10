package com.example.wangbin.binsdemo.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.AppApplication;
import com.squareup.leakcanary.RefWatcher;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toast.makeText(this, "main2activty", Toast.LENGTH_SHORT).show();

        final TextView tx = new TextView(this);

        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                tx.getHeight();
            }
        };

        handler.postDelayed(runnable, 3000000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        RefWatcher watcher = AppApplication.getWatcher(this);
//
//        watcher.watch(this);
    }
}
