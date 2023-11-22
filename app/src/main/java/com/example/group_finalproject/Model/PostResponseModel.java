package com.example.group_finalproject.Model;

import java.util.List;

public class PostResponseModel {
    private int code;
    private String message;
    private List<PostDataModel> postData;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PostDataModel> getPostData() {
        return postData;
    }

    public void setPostData(List<PostDataModel> postData) {
        this.postData = postData;
    }
}
