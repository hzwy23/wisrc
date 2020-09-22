package com.wisrc.sys.webapp.entity;

public class SysMenuTypeAttrEntity {
    private int menuType;
    private String menuTypeDesc;

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public String getMenuTypeDesc() {
        return menuTypeDesc;
    }

    public void setMenuTypeDesc(String menuTypeDesc) {
        this.menuTypeDesc = menuTypeDesc;
    }

    @Override
    public String toString() {
        return "SysMenuTypeAttrEntity{" +
                "menuType=" + menuType +
                ", menuTypeDesc='" + menuTypeDesc + '\'' +
                '}';
    }
}
