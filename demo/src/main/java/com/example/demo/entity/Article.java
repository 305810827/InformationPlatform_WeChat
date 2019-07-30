package com.example.demo.entity;

import java.util.Date;

public class Article {
    //主键
    private Integer articleId;
    //类别
    private String articleClassify;
    //标题
    private String articleTitle;
    //描述
    private String articleDesc;
    //发布时间
    private Date createTime;
    //内容
    private String articleContent;
    //列表图片路径
    private String imageUrl;
    //被收藏次数
    private int collectCount;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArticleClassify() {
        return articleClassify;
    }

    public void setArticleClassify(String articleClassify) {
        this.articleClassify = articleClassify;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public Article(){
    }

    public Article(Integer id, String title, String imageUrl, String desc, String classify, String content){
        this.articleId = id;
        this.articleTitle = title;
        this.articleDesc = desc;
        this.imageUrl = imageUrl;
        this.articleClassify = classify;
        this.articleContent = content;
    }

    public Article(String title, String image, String desc, String classify, String content, int collectCount){
        this.articleTitle = title;
        this.articleDesc = desc;
        this.imageUrl = image;
        this.articleClassify = classify;
        this.articleContent = content;
        this.collectCount = collectCount;
    }

}
