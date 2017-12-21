
package com.example.wangbin.binsdemo.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Visible implements Serializable{

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("list_id")
    @Expose
    private Integer listId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

}
