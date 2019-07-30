package com.example.demo.entity;

import java.util.Date;

public class Comment {
    private Integer id;

    private String wxName;

    private String wxHead;

    private int articleId;

    private String content;

    private Date date;

    private String openId;

    private int pointsCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getWxHead() {
        return wxHead;
    }

    public void setWxHead(String wxHead) {
        this.wxHead = wxHead;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public void setPointsCount(int pointsCount) {
        this.pointsCount = pointsCount;
    }

    public Comment(){

    }

    public Comment(int articleId, String wxName,String wxHead,String content,String openId,int pointsCount){
        this.articleId = articleId;
        this.wxName = wxName;
        this.wxHead = wxHead;
        this.content = content;
        this.openId = openId;
        this.pointsCount = pointsCount;
    }
}
