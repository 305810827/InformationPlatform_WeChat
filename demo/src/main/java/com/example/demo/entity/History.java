package com.example.demo.entity;

public class History {
    //主键
    private Integer id;
    //标题
    private String historyTitle;
    //藐视
    private String historyDesc;
    //图片路径
    private String historyUrl;
    //内容
    private String historyContent;

    private int year;
    private int month;
    private int day;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHistoryTitle() {
        return historyTitle;
    }

    public void setHistoryTitle(String historyTitle) {
        this.historyTitle = historyTitle;
    }

    public String getHistoryDesc() {
        return historyDesc;
    }

    public void setHistoryDesc(String historyDesc) {
        this.historyDesc = historyDesc;
    }

    public String getHistoryUrl() {
        return historyUrl;
    }

    public void setHistoryUrl(String historyUrl) {
        this.historyUrl = historyUrl;
    }

    public String getHistoryContent() {
        return historyContent;
    }

    public void setHistoryContent(String historyContent) {
        this.historyContent = historyContent;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
