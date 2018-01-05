/**
  * Copyright 2018 bejson.com 
  */
package com.example.wangbin.binsdemo.Entity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Geo implements Serializable {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("coordinates")
    @Expose
    private List<Double> coordinates;

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setCoordinates(List<Double> coordinates) {
         this.coordinates = coordinates;
     }
     public List<Double> getCoordinates() {
         return coordinates;
     }

}