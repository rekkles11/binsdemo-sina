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
    private int previousCursor;

    @SerializedName("next_cursor")
    @Expose
    private int nextCursor;

    @SerializedName("total_number")
    @Expose
    private int totalNumber;

    @SerializedName("since_id")
    @Expose
    private int sinceId;

    @SerializedName("max_id")
    @Expose
    private int maxId;

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

    public int getPreviousCursor() {
        return previousCursor;
    }

    public void setPreviousCursor(int previousCursor) {
        this.previousCursor = previousCursor;
    }

    public int getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(int nextCursor) {
        this.nextCursor = nextCursor;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getSinceId() {
        return sinceId;
    }

    public void setSinceId(int sinceId) {
        this.sinceId = sinceId;
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public void setStatus(Status status) {
         this.status = status;
     }
     public Status getStatus() {
         return status;
     }

}