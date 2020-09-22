package com.wisrc.zuul.entity;


public class UserMenusEntity {

    private String userId;
    private String menuId;
    private String path;
    private Integer methodCd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getMethodCd() {
        return methodCd;
    }

    public void setMethodCd(Integer methodCd) {
        this.methodCd = methodCd;
    }

    @Override
    public String toString() {
        return "UserMenusEntity{" +
                "userId='" + userId + '\'' +
                ", menuId='" + menuId + '\'' +
                ", path='" + path + '\'' +
                ", methodCd=" + methodCd +
                '}';
    }
}
