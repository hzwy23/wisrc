package com.wisrc.basic.vo;

import com.wisrc.basic.entity.UserQuestionAttrEntity;
import com.wisrc.basic.entity.UserQuestionEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class UserQuestionVO {
    @ApiModelProperty(value = "问题反馈Id")
    private String questionId;
    @ApiModelProperty(value = "问题创建时间")
    private String createTime;
    @ApiModelProperty(value = "问题创建人")
    private String userId;
    @ApiModelProperty(value = "问题创建人名称")
    private String userName;
    @ApiModelProperty(value = "反馈问题对应的华为存储桶")
    private String obsName;
    @ApiModelProperty(value = "返回问题存储的地址")
    private String questionUrl;
    @ApiModelProperty(value = "返回文件")
    private String file;
    @ApiModelProperty(value = "状态1：已解决 2：未解决")
    private int statusCd;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "点赞数量")
    private int starCnt;
    @ApiModelProperty(value = "0：非匿名提问 1：匿名提问")
    private int anonymous;
    @ApiModelProperty(value = "0: 当前用户没有点赞 1：当前用户已经点赞")
    private int isStar;
    @ApiModelProperty(value = "图片")
    private List<UserQuestionAttrEntity> questionAttrEntityList;
    @ApiModelProperty(value = "备注评论")
    private List<UserQuestionNoteVO> userQuestionNoteVOList;


    public static final UserQuestionVO toVO(UserQuestionEntity uqe) {
        UserQuestionVO vo = new UserQuestionVO();
        BeanUtils.copyProperties(uqe, vo);
        return vo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getObsName() {
        return obsName;
    }

    public void setObsName(String obsName) {
        this.obsName = obsName;
    }

    public String getQuestionUrl() {
        return questionUrl;
    }

    public void setQuestionUrl(String questionUrl) {
        this.questionUrl = questionUrl;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStarCnt() {
        return starCnt;
    }

    public void setStarCnt(int starCnt) {
        this.starCnt = starCnt;
    }

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    public int getIsStar() {
        return isStar;
    }

    public void setIsStar(int isStar) {
        this.isStar = isStar;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<UserQuestionAttrEntity> getQuestionAttrEntityList() {
        return questionAttrEntityList;
    }

    public void setQuestionAttrEntityList(List<UserQuestionAttrEntity> questionAttrEntityList) {
        this.questionAttrEntityList = questionAttrEntityList;
    }

    public List<UserQuestionNoteVO> getUserQuestionNoteVOList() {
        return userQuestionNoteVOList;
    }

    public void setUserQuestionNoteVOList(List<UserQuestionNoteVO> userQuestionNoteVOList) {
        this.userQuestionNoteVOList = userQuestionNoteVOList;
    }
}