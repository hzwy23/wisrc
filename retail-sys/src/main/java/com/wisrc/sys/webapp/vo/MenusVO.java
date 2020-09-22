package com.wisrc.sys.webapp.vo;

import java.util.List;

public class MenusVO {
    private String path;
    private String component;
    private String redirect;
    private String name;
    private MenusMetaVO meta;
    private List<MenusVO> children;
    private boolean alwaysShow;
    private boolean hidden;
    private int menuType;

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public boolean isAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public MenusMetaVO getMeta() {
        return meta;
    }

    public void setMeta(MenusMetaVO meta) {
        this.meta = meta;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenusVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenusVO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenusVO{" +
                "path='" + path + '\'' +
                ", component='" + component + '\'' +
                ", redirect='" + redirect + '\'' +
                ", name='" + name + '\'' +
                ", meta=" + meta +
                ", children=" + children +
                ", alwaysShow=" + alwaysShow +
                ", hidden=" + hidden +
                ", menuType=" + menuType +
                '}';
    }
}
