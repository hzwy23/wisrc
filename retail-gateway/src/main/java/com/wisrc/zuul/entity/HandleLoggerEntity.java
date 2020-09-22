package com.wisrc.zuul.entity;

import java.util.Map;

public class HandleLoggerEntity {
    private String username;
    private String handleTime;
    private String requestUri;
    private int responseStatus;
    private String requestMethod;
    private String tokenExpireTime;
    private String clientIpAddr;
    private Map<String, String> params;
    private String paramsJson;

    public String getClientIpAddr() {
        return clientIpAddr;
    }

    public void setClientIpAddr(String clientIpAddr) {
        this.clientIpAddr = clientIpAddr;
    }

    public String getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(String tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }


    public String getParamsJson() {
        return paramsJson;
    }

    public void setParamsJson(String paramsJson) {
        this.paramsJson = paramsJson;
    }

    @Override
    public String toString() {
        return "HandleLoggerEntity{" +
                "username='" + username + '\'' +
                ", handleTime='" + handleTime + '\'' +
                ", requestUri='" + requestUri + '\'' +
                ", responseStatus=" + responseStatus +
                ", requestMethod='" + requestMethod + '\'' +
                ", tokenExpireTime='" + tokenExpireTime + '\'' +
                ", clientIpAddr='" + clientIpAddr + '\'' +
                ", params=" + params +
                '}';
    }
}
