package com.wisrc.sys.webapp.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class MenuTreeVO {
    private String id;
    private String value;
    private String label;
    private int menuType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuTreeVO> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.value = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<MenuTreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeVO> children) {
        this.children = children;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    @Override
    public String toString() {
        return "MenuTreeVo{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
