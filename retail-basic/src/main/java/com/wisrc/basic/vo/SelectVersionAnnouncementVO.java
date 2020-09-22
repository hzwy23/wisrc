package com.wisrc.basic.vo;

import io.swagger.annotations.ApiModelProperty;

public class SelectVersionAnnouncementVO {
    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "版本号")
    private String versionNumber;

    @ApiModelProperty(value = "更新版本模块")
    private int versionModuleCd;

    @ApiModelProperty(value = "跟新模块名称")
    private String versionModuleName;

    @ApiModelProperty(value = "跟新版本时间")
    private String versionTime;

    @ApiModelProperty(value = "跟新版本内容")
    private String versionContent;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getVersionModuleCd() {
        return versionModuleCd;
    }

    public void setVersionModuleCd(int versionModuleCd) {
        this.versionModuleCd = versionModuleCd;
    }

    public String getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(String versionTime) {
        this.versionTime = versionTime;
    }

    public String getVersionContent() {
        return versionContent;
    }

    public void setVersionContent(String versionContent) {
        this.versionContent = versionContent;
    }

    public String getVersionModuleName() {
        return versionModuleName;
    }

    public void setVersionModuleName(String versionModuleName) {
        this.versionModuleName = versionModuleName;
    }
}
