package com.wisrc.merchandise.vo;

import com.wisrc.merchandise.entity.BasicPlatformInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

public class PlatformInfoVO {

    @ApiModelProperty(hidden = true)
    private String platId;

    private String platform;

    private String siteName;

    private String api;

    @ApiModelProperty(hidden = true)
    private int statusCd;

    @ApiModelProperty(hidden = true)
    private String modifyUser;

    @ApiModelProperty(hidden = true)
    private String updateTime;

    private String marketplaceId;

    public static PlatformInfoVO toVO(BasicPlatformInfoEntity ele) {
        if (ele == null) {
            return null;
        }
        PlatformInfoVO vo = new PlatformInfoVO();
        BeanUtils.copyProperties(ele, vo);
        vo.setApi(ele.getApiUrl());
        vo.setMarketplaceId(ele.getMarketPlaceId());
        vo.setModifyUser(ele.getModifyUser());
        vo.setPlatform(ele.getPlatName());
        vo.setSiteName(ele.getPlatSite());
        vo.setUpdateTime(ele.getModifyTime());
        return vo;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    @Override
    public String toString() {
        return "PlatformInfoVO{" +
                "platId='" + platId + '\'' +
                ", platform='" + platform + '\'' +
                ", siteName='" + siteName + '\'' +
                ", api='" + api + '\'' +
                ", statusCd='" + statusCd + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", marketplaceId='" + marketplaceId + '\'' +
                '}';
    }
}

