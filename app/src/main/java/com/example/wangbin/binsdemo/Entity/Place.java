/**
  * Copyright 2018 bejson.com 
  */
package com.example.wangbin.binsdemo.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Place implements Serializable{

    @SerializedName("lon")
    @Expose
    private double lon;

    @SerializedName("poiid")
    @Expose
    private String poiid;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("lat")
    @Expose
    private double lat;

    public void setLon(double lon) {
         this.lon = lon;
     }
     public double getLon() {
         return lon;
     }

    public void setPoiid(String poiid) {
         this.poiid = poiid;
     }
     public String getPoiid() {
         return poiid;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setLat(double lat) {
         this.lat = lat;
     }
     public double getLat() {
         return lat;
     }

}