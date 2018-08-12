package com.zach.manage.vo;

import java.io.Serializable;

public class PicUploadResult implements Serializable {
    private Integer error;  //非0表示上传失败
    private String url;
    private String height;
    private String width;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
