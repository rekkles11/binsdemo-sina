/**
  * Copyright 2018 bejson.com 
  */
package com.example.wangbin.binsdemo.Entity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
public class Comment implements Serializable{

    @SerializedName("comments")
    @Expose
    private List<Comments> comments = null;


    @SerializedName("marks")
    @Expose
    private List<String> marks = null;

    @SerializedName("hasvisible")
    @Expose
    private boolean hasvisible;

    @SerializedName("previous_cursor")
    @Expose
    private Long previousCursor;

    @SerializedName("next_cursor")
    @Expose
    private Long nextCursor;

    @SerializedName("total_number")
    @Expose
    private int totalNumber;

    @SerializedName("since_id")
    @Expose
    private Long sinceId;

    @SerializedName("max_id")
    @Expose
    private Long maxId;

    @SerializedName("status")
    @Expose
    private Status status;

    public void setComments(List<Comments> comments) {
         this.comments = comments;
     }
     public List<Comments> getComments() {
         return comments;
     }

    public void setMarks(List<String> marks) {
         this.marks = marks;
     }
     public List<String> getMarks() {
         return marks;
     }

    public void setHasvisible(boolean hasvisible) {
         this.hasvisible = hasvisible;
     }
     public boolean getHasvisible() {
         return hasvisible;
     }

    public boolean isHasvisible() {
        return hasvisible;
    }

    public Long getPreviousCursor() {
        return previousCursor;
    }

    public void setPreviousCursor(Long previousCursor) {
        this.previousCursor = previousCursor;
    }

    public Long getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(Long nextCursor) {
        this.nextCursor = nextCursor;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Long getSinceId() {
        return sinceId;
    }

    public void setSinceId(Long sinceId) {
        this.sinceId = sinceId;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public void setStatus(Status status) {
         this.status = status;
     }
     public Status getStatus() {
         return status;
     }

}