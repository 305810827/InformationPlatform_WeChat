package com.example.demo.entity;

import java.util.Date;

public class Sensitive {
    private Integer id;

    private String word;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Sensitive(){}

    public Sensitive(Integer id, String word){
        this.id = id;
        this.word = word;
    }
}
