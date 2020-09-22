package com.wisrc.sys.webapp.entity;

public class SysMethodAttrEntity {
    private int methodCd;
    private String methodValue;

    public int getMethodCd() {
        return methodCd;
    }

    public void setMethodCd(int methodCd) {
        this.methodCd = methodCd;
    }

    public String getMethodValue() {
        return methodValue;
    }

    public void setMethodValue(String methodValue) {
        this.methodValue = methodValue;
    }

    @Override
    public String toString() {
        return "SysMethodAttrEntity{" +
                "methodCd=" + methodCd +
                ", methodValue='" + methodValue + '\'' +
                '}';
    }
}
