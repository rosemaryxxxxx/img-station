package com.xhh.imgdemo.req;

import com.baomidou.mybatisplus.annotation.TableName;


public class ImgSaveReq {

    private Long id;

    private String location;

    private String taketime;

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
        return taketime;
    }

    public void setTime(String taketime) {
        this.taketime = taketime;
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
                ", taketime='" + taketime + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
