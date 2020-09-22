package com.wisrc.basic.entity;

import io.swagger.annotations.ApiModelProperty;

public class UserQuestionAttrEntity {
    @ApiModelProperty(value = "唯一标识符")
    private String uuid;
    @ApiModelProperty(value = "问题反馈Id")
    private String questionId;
    @ApiModelProperty(value = "反馈问题对应的华为存储桶")
    private String obsName;
    @ApiModelProperty(value = "返回问题存储的地址")
    private String imagesUrl;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getObsName() {
        return obsName;
    }

    public void setObsName(String obsName) {
        this.obsName = obsName;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
