package com.wisrc.sys.webapp.vo;

public class SysDeptOperationVO {
    private String deptCd;
    private String deptName;

    public String getDeptCd() {
        return deptCd;
    }

    public void setDeptCd(String deptCd) {
        this.deptCd = deptCd;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "SysDeptOperationVO{" +
                "deptCd='" + deptCd + '\'' +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
