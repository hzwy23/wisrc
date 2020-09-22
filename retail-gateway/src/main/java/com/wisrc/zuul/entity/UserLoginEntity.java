package com.wisrc.zuul.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserLoginEntity {
    private String username;
    @JsonIgnore
    private String password;
    private int statusCd;
    private String identifyUrl;

    public UserLoginEntity() {
    }

    public UserLoginEntity(String username, String password, String identifyUrl) {
        this.username = username;
        this.password = password;
        this.identifyUrl = identifyUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getIdentifyUrl() {
        return identifyUrl;
    }

    public void setIdentifyUrl(String identifyUrl) {
        this.identifyUrl = identifyUrl;
    }


    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    @Override
    public String toString() {
        return "UserLoginEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", statusCd=" + statusCd +
                ", identifyUrl='" + identifyUrl + '\'' +
                '}';
    }
}
