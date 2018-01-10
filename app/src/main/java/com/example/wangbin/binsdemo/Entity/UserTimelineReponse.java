
package com.example.wangbin.binsdemo.Entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTimelineReponse implements Serializable{

    @SerializedName("statuses")
    @Expose
    private List<Status> statuses = null;
    @SerializedName("marks")
    @Expose
    private List<Object> marks = null;
    @SerializedName("hasvisible")
    @Expose
    private Boolean hasvisible;
    @SerializedName("previous_cursor")
    @Expose
    private Long previousCursor;
    @SerializedName("next_cursor")
    @Expose
    private Long nextCursor;
    @SerializedName("total_number")
    @Expose
    private Integer totalNumber;
    @SerializedName("interval")
    @Expose
    private Integer interval;

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public List<Object> getMarks() {
        return marks;
    }

    public void setMarks(List<Object> marks) {
        this.marks = marks;
    }

    public Boolean getHasvisible() {
        return hasvisible;
    }

    public void setHasvisible(Boolean hasvisible) {
        this.hasvisible = hasvisible;
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

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

}
