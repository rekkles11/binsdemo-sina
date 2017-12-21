package com.example.wangbin.binsdemo.Model;

import com.example.wangbin.binsdemo.Entity.FriendsIds;
import com.example.wangbin.binsdemo.Service.Api;
import com.example.wangbin.binsdemo.Service.NetWork;
import com.github.lisicnu.log4android.LogManager;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by momo on 2017/12/13.
 */

public class FriendsIdsModel {
    private FriendsIds mFriendsIds;

    public void getFriendsIdsFromNet(Map<String,String> map){
        Api api = NetWork.getInstance().getApi();
//        Observable<FriendsIds> observable = api.getFriendsIds(map).subscribeOn(Schedulers.io());
        api.getFriendsIds(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FriendsIds>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FriendsIds friendsIds) {
                        mFriendsIds = friendsIds;

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogManager.d("FriendsIds",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public FriendsIds getFriendsIds() {
        return mFriendsIds;
    }

    public void setFriendsIds(FriendsIds friendsIds) {
        mFriendsIds = friendsIds;
    }
}
