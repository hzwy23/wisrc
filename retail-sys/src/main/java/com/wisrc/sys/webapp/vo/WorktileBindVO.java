package com.wisrc.sys.webapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorktileBindVO {
    @JsonProperty("access_token")
    private String accessToken;
    private String state;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "WorktileBindVO{" +
                "accessToken='" + accessToken + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
