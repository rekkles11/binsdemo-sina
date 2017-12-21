
package com.example.wangbin.binsdemo.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Insecurity implements Serializable{

    @SerializedName("sexual_content")
    @Expose
    private Boolean sexualContent;

    public Boolean getSexualContent() {
        return sexualContent;
    }

    public void setSexualContent(Boolean sexualContent) {
        this.sexualContent = sexualContent;
    }

}
