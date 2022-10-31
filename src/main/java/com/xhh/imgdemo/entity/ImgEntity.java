package com.xhh.imgdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("img")
public class ImgEntity {

    private Long id;

    private String location;

    private String time;

    private String url;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public String toString() {
        return "ImgEntity{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
