package com.wisrc.zuul.vo;

public class LoginResultVO {
    private String username;
    private int code;
    private String details;

    private String token;
    private boolean needChangePassword;
    private String error;
    private String description;

    public LoginResultVO() {

    }

    public LoginResultVO(String username, int code, String details) {
        this.username = username;
        this.code = code;
        this.details = details;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isNeedChangePassword() {
        return needChangePassword;
    }

    public void setNeedChangePassword(boolean needChangePassword) {
        this.needChangePassword = needChangePassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    @Override
    public String toString() {
        return "LoginResultBO{" +
                "username='" + username + '\'' +
                ", code=" + code +
                ", details='" + details + '\'' +
                ", token='" + token + '\'' +
                ", needChangePassword=" + needChangePassword +
                ", error='" + error + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
