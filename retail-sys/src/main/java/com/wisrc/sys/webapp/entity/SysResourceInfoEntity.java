package com.wisrc.sys.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class SysResourceInfoEntity {

    @ApiModelProperty(value = "菜单编码")
    @NotNull(message = "菜单编码不能为空")
    private String menuId;
    @ApiModelProperty(value = "组件名称")

    @NotNull(message = "菜单名称不能为空")
    private String menuName;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "请求方式")
    private int methodCd;

    @ApiModelProperty(value = "组件名称")
    private String component;

    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    @ApiModelProperty(value = "总是显示")
    private boolean alwaysShow;

    @ApiModelProperty(value = "菜单标题")
    private String metaTitle;

    @ApiModelProperty(value = "菜单图标")
    private String metaIcon;

    @ApiModelProperty(value = "是否隐藏")
    private boolean hidden;

    @ApiModelProperty(value = "是否缓存")
    private boolean metaNoCache;

    @ApiModelProperty(value = "上级菜单编码")
    @NotNull(message = "父级菜单不能为空")
    private String parentId;

    @ApiModelProperty(value = "菜单状态")
    private int statusCd;

    @ApiModelProperty(value = "菜单类型")
    @NotNull(message = "请选择菜单类型。1:一级主菜单，2：页面，3：目录节点，4：页面操作按钮")
    private int menuType;

    @ApiModelProperty(value = "排序大小")
    private int sortNumber;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public boolean isMetaNoCache() {
        return metaNoCache;
    }

    public void setMetaNoCache(boolean metaNoCache) {
        this.metaNoCache = metaNoCache;
    }

    public int getMethodCd() {
        return methodCd;
    }

    public void setMethodCd(int methodCd) {
        this.methodCd = methodCd;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaIcon() {
        return metaIcon;
    }

    public void setMetaIcon(String metaIcon) {
        this.metaIcon = metaIcon;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }

    @Override
    public String toString() {
        return "SysResourceInfoEntity{" + "menuId='" + menuId + '\'' + ", menuName='" + menuName + '\'' + ", path='"
                + path + '\'' + ", methodCd=" + methodCd + ", component='" + component + '\'' + ", redirect='"
                + redirect + '\'' + ", always_show=" + alwaysShow + ", metaTitle='" + metaTitle + '\'' + ", metaIcon='"
                + metaIcon + '\'' + ", hidden=" + hidden + ", metaNoCache=" + metaNoCache + ", parentId='" + parentId
                + '\'' + ", statusCd='" + statusCd + '\'' + ", menuType='" + menuType + '\'' + ", sortNumber='"
                + sortNumber + '\'' + '}';
    }
}
