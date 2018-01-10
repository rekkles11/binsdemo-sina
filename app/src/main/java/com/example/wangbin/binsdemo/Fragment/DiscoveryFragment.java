package com.example.wangbin.binsdemo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.wangbin.binsdemo.Activity.UserTimeLineActivity;
import com.example.wangbin.binsdemo.R;

/**
 * Created by momo on 2018/1/4.
 */

public class DiscoveryFragment extends Fragment implements View.OnClickListener {
    RelativeLayout mRelativeLayout;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discovery,container,false);
        mContext = getContext();
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.publicweibo_layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.publicweibo_layout:
                Intent intent = new Intent(mContext, UserTimeLineActivity.class);
                intent.putExtra("name","publictimeline");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
