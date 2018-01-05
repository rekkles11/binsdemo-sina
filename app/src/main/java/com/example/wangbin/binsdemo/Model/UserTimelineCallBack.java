package com.example.wangbin.binsdemo.Model;

import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.Entity.UserTimelineReponse;

import java.util.List;

/**
 * Created by momo on 2017/12/14.
 */

public interface UserTimelineCallBack {
    void getResult(List<Status> list,Boolean isSuccess);
}
