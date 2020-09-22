package com.wisrc.sys.webapp.entity;

public class VSysMenuUserEntity {
    private String userId;
    private String menuId;
    private String path;
    private Integer methodCd;
    private Integer menuType;

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

    public Integer getMethodCd() {
        return methodCd;
    }

    public void setMethodCd(Integer methodCd) {
        this.methodCd = methodCd;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    @Override
    public String toString() {
        return "VSysMenuUserEntity{" +
                "userId='" + userId + '\'' +
                ", menuId='" + menuId + '\'' +
                ", path='" + path + '\'' +
                ", methodCd=" + methodCd +
                ", menuType=" + menuType +
                '}';
    }
}
