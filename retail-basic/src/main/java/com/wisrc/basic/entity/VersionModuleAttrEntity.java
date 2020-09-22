package com.wisrc.basic.entity;

import io.swagger.annotations.ApiModelProperty;

public class VersionModuleAttrEntity {

    private int versionModuleCd;

    @ApiModelProperty("更新公告名称")
    private String versionModuleName;

    public int getVersionModuleCd() {
        return versionModuleCd;
    }

    public void setVersionModuleCd(int versionModuleCd) {
        this.versionModuleCd = versionModuleCd;
    }

    public String getVersionModuleName() {
        return versionModuleName;
    }

    public void setVersionModuleName(String versionModuleName) {
        this.versionModuleName = versionModuleName;
    }
}
