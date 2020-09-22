package com.wisrc.basic.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;


public class AddUserQuestionVO {
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "问题描述")
    private String file;
    @ApiModelProperty(value = "存储桶")
    private String obsName;
    @ApiModelProperty(value = "0：非匿名提问 1：匿名提问")
    private int anonymous;
    @ApiModelProperty(value = "图片")
    private List<Map> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getObsName() {
        return obsName;
    }

    public void setObsName(String obsName) {
        this.obsName = obsName;
    }

    public List<Map> getImages() {
        return images;
    }

    public void setImages(List<Map> images) {
        this.images = images;
    }

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }
}
