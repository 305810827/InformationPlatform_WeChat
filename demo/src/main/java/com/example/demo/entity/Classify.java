package com.example.demo.entity;

public class Classify {
    //主键
    private Integer id;
    //类别
    private String classifyName;
    //列表图片路径
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Classify(String classifyName, String image){
        this.classifyName = classifyName;
        this.image = image;
    }

    public Classify(Integer id,String classifyName, String image){
        this.id = id;
        this.classifyName = classifyName;
        this.image = image;
    }
}
