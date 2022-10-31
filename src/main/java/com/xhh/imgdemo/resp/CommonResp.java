package com.xhh.imgdemo.resp;

public class CommonResp<T> {

    //义务上的成功和失败
    private Boolean success = true;

    //信息返回
    private String message;

    //返回范型数据 自定义类型
    private T content;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommonResp{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", content=" + content +
                '}';
    }
}
