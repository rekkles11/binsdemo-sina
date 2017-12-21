
package com.example.wangbin.binsdemo.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Annotation implements Serializable {

    @SerializedName("client_mblogid")
    @Expose
    private String clientMblogid;
    @SerializedName("mapi_request")
    @Expose
    private Boolean mapiRequest;

    public String getClientMblogid() {
        return clientMblogid;
    }

    public void setClientMblogid(String clientMblogid) {
        this.clientMblogid = clientMblogid;
    }

    public Boolean getMapiRequest() {
        return mapiRequest;
    }

    public void setMapiRequest(Boolean mapiRequest) {
        this.mapiRequest = mapiRequest;
    }

}
