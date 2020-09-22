package com.wisrc.sys.webapp.entity;

import java.util.Objects;

public class SysSecUserEntity {
    private String userId;
    private String password;
    private Integer errorCnt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getErrorCnt() {
        return errorCnt;
    }

    public void setErrorCnt(Integer errorCnt) {
        this.errorCnt = errorCnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysSecUserEntity that = (SysSecUserEntity) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(password, that.password) &&
                Objects.equals(errorCnt, that.errorCnt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, password, errorCnt);
    }
}
