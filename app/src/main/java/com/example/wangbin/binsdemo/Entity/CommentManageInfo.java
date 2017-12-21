
package com.example.wangbin.binsdemo.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommentManageInfo implements Serializable{

    @SerializedName("comment_manage_button")
    @Expose
    private Integer commentManageButton;
    @SerializedName("comment_permission_type")
    @Expose
    private Integer commentPermissionType;
    @SerializedName("approval_comment_type")
    @Expose
    private Integer approvalCommentType;

    public Integer getCommentManageButton() {
        return commentManageButton;
    }

    public void setCommentManageButton(Integer commentManageButton) {
        this.commentManageButton = commentManageButton;
    }

    public Integer getCommentPermissionType() {
        return commentPermissionType;
    }

    public void setCommentPermissionType(Integer commentPermissionType) {
        this.commentPermissionType = commentPermissionType;
    }

    public Integer getApprovalCommentType() {
        return approvalCommentType;
    }

    public void setApprovalCommentType(Integer approvalCommentType) {
        this.approvalCommentType = approvalCommentType;
    }

}
