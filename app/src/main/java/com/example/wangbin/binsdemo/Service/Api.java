package com.example.wangbin.binsdemo.Service;


import com.example.wangbin.binsdemo.Entity.FriendsIds;
import com.example.wangbin.binsdemo.Entity.UserTimelineReponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * Created by momo on 2017/12/12.
 */

public interface Api {
    @GET("statuses/user_timeline.json")
    Call<UserTimelineReponse> getUserTimeline(@QueryMap Map<String,String> options);

    @GET("statuses/public_timeline.json")
    Call<UserTimelineReponse> getPublicTimeline(@QueryMap Map<String ,String> options);

    @GET("friendships/friends/ids.json")
    Observable<FriendsIds> getFriendsIds(@QueryMap Map<String,String> map);

    @POST("statuses/share.json")
    @Multipart
    Call<Object> postShareImage(@Part("access_token") RequestBody token,@Part("status") RequestBody status ,@Part MultipartBody.Part[] file);

    @POST("statuses/share.json")
    @FormUrlEncoded
    Call<Object> postShareText(@FieldMap(encoded = true) Map<String,String> map);

}
