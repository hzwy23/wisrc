package com.wisrc.sys.webapp.vo.dept;

import java.util.List;

public class DeptTreeVO {
    private String label;
    private String value;
    private List<DeptTreeVO> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<DeptTreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<DeptTreeVO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "DeptTreeVO{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                ", children=" + children +
                '}';
    }
}
