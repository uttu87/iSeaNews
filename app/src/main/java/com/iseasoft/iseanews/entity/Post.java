package com.iseasoft.iseanews.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable{

    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("post_title")
    @Expose
    private String postTitle;
    @SerializedName("post_desc")
    @Expose
    private String postDesc;
    @SerializedName("post_thumb")
    @Expose
    private String postThumb;
    @SerializedName("category_id")
    @Expose
    private String categoryId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getPostThumb() {
        return postThumb;
    }

    public void setPostThumb(String postThumb) {
        this.postThumb = postThumb;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

}