package com.example.administrator.materialdesign.model;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

 public class DataBean {
    public DataBean(String name, String content, String imgId) {
        this.name = name;
        this.content = content;
        this.imgUrl = imgId;
    }

    private String name;
    private String content;
    private String imgUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
