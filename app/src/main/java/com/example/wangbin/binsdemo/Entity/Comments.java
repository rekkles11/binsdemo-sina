/**
  * Copyright 2018 bejson.com 
  */
package com.example.wangbin.binsdemo.Entity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Comments implements Serializable{

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("rootid")
    @Expose
    private long rootid;

    @SerializedName("floor_number")
    @Expose
    private int floorNumber;

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("disable_reply")
    @Expose
    private int disableReply;

    @SerializedName("source_allowclick")
    @Expose
    private int sourceAllowclick;

    @SerializedName("source_type")
    @Expose
    private int sourceType;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("mid")
    @Expose
    private String mid;

    @SerializedName("idstr")
    @Expose
    private String idstr;

    @SerializedName("status")
    @Expose
    private Status status;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRootid() {
        return rootid;
    }

    public void setRootid(long rootid) {
        this.rootid = rootid;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDisableReply() {
        return disableReply;
    }

    public void setDisableReply(int disableReply) {
        this.disableReply = disableReply;
    }

    public int getSourceAllowclick() {
        return sourceAllowclick;
    }

    public void setSourceAllowclick(int sourceAllowclick) {
        this.sourceAllowclick = sourceAllowclick;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public void setSource(String source) {
         this.source = source;
     }
     public String getSource() {
         return source;
     }

    public void setUser(User user) {
         this.user = user;
     }
     public User getUser() {
         return user;
     }

    public void setMid(String mid) {
         this.mid = mid;
     }
     public String getMid() {
         return mid;
     }

    public void setIdstr(String idstr) {
         this.idstr = idstr;
     }
     public String getIdstr() {
         return idstr;
     }

    public void setStatus(Status status) {
         this.status = status;
     }
     public Status getStatus() {
         return status;
     }

}