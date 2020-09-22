package com.wisrc.basic.entity;

import io.swagger.annotations.ApiModelProperty;


/**
 * 点赞表
 */
public class UserQuestionStarEntity {
    @ApiModelProperty(value = "唯一标识符")
    private String uuid;
    @ApiModelProperty(value = "问题反馈Id")
    private String questionId;
    @ApiModelProperty(value = "点赞人")
    private String userId;
    @ApiModelProperty(value = "点赞时间")
    private String createTime;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
