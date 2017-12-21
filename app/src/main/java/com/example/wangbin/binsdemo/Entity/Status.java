
package com.example.wangbin.binsdemo.Entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status implements Serializable{

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("idstr")
    @Expose
    private String idstr;
    @SerializedName("can_edit")
    @Expose
    private Boolean canEdit;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("textLength")
    @Expose
    private Integer textLength;
    @SerializedName("source_allowclick")
    @Expose
    private Integer sourceAllowclick;
    @SerializedName("source_type")
    @Expose
    private Integer sourceType;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("favorited")
    @Expose
    private Boolean favorited;
    @SerializedName("truncated")
    @Expose
    private Boolean truncated;
    @SerializedName("in_reply_to_status_id")
    @Expose
    private String inReplyToStatusId;
    @SerializedName("in_reply_to_user_id")
    @Expose
    private String inReplyToUserId;
    @SerializedName("in_reply_to_screen_name")
    @Expose
    private String inReplyToScreenName;
    @SerializedName("pic_urls")
    @Expose
    private List<PicUrl> picUrls = null;
    @SerializedName("thumbnail_pic")
    @Expose
    private String thumbnailPic;
    @SerializedName("bmiddle_pic")
    @Expose
    private String bmiddlePic;
    @SerializedName("original_pic")
    @Expose
    private String originalPic;
    @SerializedName("geo")
    @Expose
    private Object geo;
    @SerializedName("is_paid")
    @Expose
    private Boolean isPaid;
    @SerializedName("mblog_vip_type")
    @Expose
    private Integer mblogVipType;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("annotations")
    @Expose
    private List<Annotation> annotations = null;
    @SerializedName("reposts_count")
    @Expose
    private Integer repostsCount;
    @SerializedName("comments_count")
    @Expose
    private Integer commentsCount;
    @SerializedName("attitudes_count")
    @Expose
    private Integer attitudesCount;
    @SerializedName("pending_approval_count")
    @Expose
    private Integer pendingApprovalCount;
    @SerializedName("isLongText")
    @Expose
    private Boolean isLongText;
    @SerializedName("mlevel")
    @Expose
    private Integer mlevel;
    @SerializedName("visible")
    @Expose
    private Visible visible;
    @SerializedName("biz_feature")
    @Expose
    private Long bizFeature;
    @SerializedName("page_type")
    @Expose
    private Integer pageType;
    @SerializedName("hasActionTypeCard")
    @Expose
    private Integer hasActionTypeCard;
    @SerializedName("darwin_tags")
    @Expose
    private List<Object> darwinTags = null;
    @SerializedName("hot_weibo_tags")
    @Expose
    private List<Object> hotWeiboTags = null;
    @SerializedName("text_tag_tips")
    @Expose
    private List<Object> textTagTips = null;
    @SerializedName("userType")
    @Expose
    private Integer userType;
    @SerializedName("more_info_type")
    @Expose
    private Integer moreInfoType;
    @SerializedName("positive_recom_flag")
    @Expose
    private Integer positiveRecomFlag;
    @SerializedName("gif_ids")
    @Expose
    private String gifIds;
    @SerializedName("is_show_bulletin")
    @Expose
    private Integer isShowBulletin;
    @SerializedName("comment_manage_info")
    @Expose
    private CommentManageInfo commentManageInfo;
    @SerializedName("biz_ids")
    @Expose
    private List<Integer> bizIds = null;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getTextLength() {
        return textLength;
    }

    public void setTextLength(Integer textLength) {
        this.textLength = textLength;
    }

    public Integer getSourceAllowclick() {
        return sourceAllowclick;
    }

    public void setSourceAllowclick(Integer sourceAllowclick) {
        this.sourceAllowclick = sourceAllowclick;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public Boolean getTruncated() {
        return truncated;
    }

    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    public String getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public void setInReplyToStatusId(String inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public String getInReplyToUserId() {
        return inReplyToUserId;
    }

    public void setInReplyToUserId(String inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public List<PicUrl> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<PicUrl> picUrls) {
        this.picUrls = picUrls;
    }

    public String getThumbnailPic() {
        return thumbnailPic;
    }

    public void setThumbnailPic(String thumbnailPic) {
        this.thumbnailPic = thumbnailPic;
    }

    public String getBmiddlePic() {
        return bmiddlePic;
    }

    public void setBmiddlePic(String bmiddlePic) {
        this.bmiddlePic = bmiddlePic;
    }

    public String getOriginalPic() {
        return originalPic;
    }

    public void setOriginalPic(String originalPic) {
        this.originalPic = originalPic;
    }

    public Object getGeo() {
        return geo;
    }

    public void setGeo(Object geo) {
        this.geo = geo;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Integer getMblogVipType() {
        return mblogVipType;
    }

    public void setMblogVipType(Integer mblogVipType) {
        this.mblogVipType = mblogVipType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public Integer getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(Integer repostsCount) {
        this.repostsCount = repostsCount;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Integer getAttitudesCount() {
        return attitudesCount;
    }

    public void setAttitudesCount(Integer attitudesCount) {
        this.attitudesCount = attitudesCount;
    }

    public Integer getPendingApprovalCount() {
        return pendingApprovalCount;
    }

    public void setPendingApprovalCount(Integer pendingApprovalCount) {
        this.pendingApprovalCount = pendingApprovalCount;
    }

    public Boolean getIsLongText() {
        return isLongText;
    }

    public void setIsLongText(Boolean isLongText) {
        this.isLongText = isLongText;
    }

    public Integer getMlevel() {
        return mlevel;
    }

    public void setMlevel(Integer mlevel) {
        this.mlevel = mlevel;
    }

    public Visible getVisible() {
        return visible;
    }

    public void setVisible(Visible visible) {
        this.visible = visible;
    }

    public Long getBizFeature() {
        return bizFeature;
    }

    public void setBizFeature(Long bizFeature) {
        this.bizFeature = bizFeature;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    public Integer getHasActionTypeCard() {
        return hasActionTypeCard;
    }

    public void setHasActionTypeCard(Integer hasActionTypeCard) {
        this.hasActionTypeCard = hasActionTypeCard;
    }

    public List<Object> getDarwinTags() {
        return darwinTags;
    }

    public void setDarwinTags(List<Object> darwinTags) {
        this.darwinTags = darwinTags;
    }

    public List<Object> getHotWeiboTags() {
        return hotWeiboTags;
    }

    public void setHotWeiboTags(List<Object> hotWeiboTags) {
        this.hotWeiboTags = hotWeiboTags;
    }

    public List<Object> getTextTagTips() {
        return textTagTips;
    }

    public void setTextTagTips(List<Object> textTagTips) {
        this.textTagTips = textTagTips;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getMoreInfoType() {
        return moreInfoType;
    }

    public void setMoreInfoType(Integer moreInfoType) {
        this.moreInfoType = moreInfoType;
    }

    public Integer getPositiveRecomFlag() {
        return positiveRecomFlag;
    }

    public void setPositiveRecomFlag(Integer positiveRecomFlag) {
        this.positiveRecomFlag = positiveRecomFlag;
    }

    public String getGifIds() {
        return gifIds;
    }

    public void setGifIds(String gifIds) {
        this.gifIds = gifIds;
    }

    public Integer getIsShowBulletin() {
        return isShowBulletin;
    }

    public void setIsShowBulletin(Integer isShowBulletin) {
        this.isShowBulletin = isShowBulletin;
    }

    public CommentManageInfo getCommentManageInfo() {
        return commentManageInfo;
    }

    public void setCommentManageInfo(CommentManageInfo commentManageInfo) {
        this.commentManageInfo = commentManageInfo;
    }

    public List<Integer> getBizIds() {
        return bizIds;
    }

    public void setBizIds(List<Integer> bizIds) {
        this.bizIds = bizIds;
    }

}
