package com.example.demo.entity;

public class WxUser {
    private Integer id;

    private String sex;

    private String openid;

    private String name;

    private String head;

    private String commentPraise;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getCommentPraise() {
        return commentPraise;
    }

    public void setCommentPraise(String commentPraise) {
        this.commentPraise = commentPraise;
    }

    public WxUser(){

    }

    public WxUser(String sex, String name, String head, String openid){
        this.sex = sex;
        this.name = name;
        this.head = head;
        this.openid = openid;
    }
}
