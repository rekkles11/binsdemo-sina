package com.example.wangbin.binsdemo.Utils.Image;

import com.example.wangbin.binsdemo.Entity.PicUrl;

import java.util.List;

/**
 * Created by momo on 2017/12/28.
 */

public class PicSize {


    public List<PicUrl> getMiddlePicUrls(List<PicUrl> picUrlList){
        for (int i = 0;i<picUrlList.size();i++){
            String thumbnail = picUrlList.get(i).getThumbnailPic();
            picUrlList.get(i).setThumbnailPic(thumbnail.replace("/thumbnail/","/bmiddle/"));
        }
        return picUrlList;
    }
    public List<PicUrl> getLaragePicUrls(List<PicUrl> picUrlList){
        for (int i = 0;i<picUrlList.size();i++){
            String thumbnail = picUrlList.get(i).getThumbnailPic();
            picUrlList.get(i).setThumbnailPic(thumbnail.replace("/thumbnail/","/larage/"));
        }
        return picUrlList;
    }
}
