package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.BlitemRemarkEntity;

public class BlitemRemarkVO extends BlitemRemarkEntity {
    private String remarkUserName;

    @Override
    public String getRemarkUserName() {
        return remarkUserName;
    }

    @Override
    public void setRemarkUserName(String remarkUserName) {
        this.remarkUserName = remarkUserName;
    }
}
