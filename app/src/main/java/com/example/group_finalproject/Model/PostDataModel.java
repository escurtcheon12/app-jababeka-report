package com.example.group_finalproject.Model;

import java.util.Date;

public class PostDataModel {
    private int id;
    private String author, category, header, location;
    private Date createdAt;

    public int getPost_id() {
        return id;
    }

    public void setPost_id(int id) {
        this.id = id;
    }

    public String getPost_author() {
        return author;
    }

    public void setPost_author(String author) {
        this.author = author;
    }

    public String getPost_category() {
        return category;
    }

    public void setPost_category(String category) {
        this.category = category;
    }

    public String getPost_text_report() {
        return header;
    }

    public void setPost_text_report(String header) {
        this.header = header;
    }

    public String getPost_location() {
        return location;
    }

    public void setPost_location(String location) {
        this.location = location;
    }

    public Date getPost_date() {
        return createdAt;
    }

    public void setPost_date(Date createdAt) {
        this.createdAt = createdAt;
    }
}
