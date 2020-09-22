package com.wisrc.sys.webapp.entity;

import java.util.Objects;

public class SysRoleMenusEntity {
    private String uuid;
    private String roleId;
    private String menuId;
    private String menuType;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRoleMenusEntity that = (SysRoleMenusEntity) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(roleId, that.roleId) &&
                Objects.equals(menuId, that.menuId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, roleId, menuId);
    }
}
