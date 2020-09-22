package com.wisrc.zuul.entity;

public class ApiInfoEntity {
    private String menuId;
    private int authFlag;
    private int statusCd;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public int getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(int authFlag) {
        this.authFlag = authFlag;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    @Override
    public String toString() {
        return "ApiInfoEntity{" +
                "menuId='" + menuId + '\'' +
                ", authFlag=" + authFlag +
                ", statusCd=" + statusCd +
                '}';
    }
}
