package com.wisrc.basic.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 查询留言
 */
public class UserQuestionNoteVO {
    @ApiModelProperty(value = "唯一标识符")
    private String uuid;
    @ApiModelProperty(value = "问题反馈Id")
    private String questionId;
    @ApiModelProperty(value = "留言人")
    private String userId;
    @ApiModelProperty(value = "留言人名称")
    private String userName;
    @ApiModelProperty(value = "留言创建时间")
    private String createTime;
    @ApiModelProperty(value = "留言")
    private String message;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
