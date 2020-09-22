package com.wisrc.replenishment.webapp.vo;

import java.util.List;

public class FbaMskuVo {
    private String zipCode;
    private List<FbaMskuInfoVO> fbaMskuInfoList;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<FbaMskuInfoVO> getFbaMskuInfoList() {
        return fbaMskuInfoList;
    }

    public void setFbaMskuInfoList(List<FbaMskuInfoVO> fbaMskuInfoList) {
        this.fbaMskuInfoList = fbaMskuInfoList;
    }
}
