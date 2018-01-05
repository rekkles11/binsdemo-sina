package com.example.wangbin.binsdemo.Model;

import com.example.wangbin.binsdemo.Entity.Comment;
import com.example.wangbin.binsdemo.Entity.Comments;

import java.util.List;

/**
 * Created by momo on 2018/1/5.
 */

public interface CommentsCallBack {
    void getComments(List<Comments> commentsList);
}
