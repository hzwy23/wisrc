package com.wisrc.purchase.webapp.vo;

import java.util.List;

public class DetailsProdictAllListVO {
    private List<AddDetailsProdictAllVO> addDetailsProdictAllVOList;

    public List<AddDetailsProdictAllVO> getAddDetailsProdictAllVOList() {
        return addDetailsProdictAllVOList;
    }

    public void setAddDetailsProdictAllVOList(List<AddDetailsProdictAllVO> addDetailsProdictAllVOList) {
        this.addDetailsProdictAllVOList = addDetailsProdictAllVOList;
    }

    @Override
    public String toString() {
        return "DetailsProdictAllListVO{" +
                "addDetailsProdictAllVOList=" + addDetailsProdictAllVOList +
                '}';
    }
}
