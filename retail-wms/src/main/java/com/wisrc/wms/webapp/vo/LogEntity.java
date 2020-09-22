package com.wisrc.wms.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Administrator
 */
public class LogEntity {
    @ApiModelProperty("请求地址")
    private String requestUrl;
    @ApiModelProperty("请求时间")
    private String requestTime;
    @ApiModelProperty("请求方法")
    private String requestMethod;
    @ApiModelProperty("请求参数")
    private String requestParams;
    @ApiModelProperty("请求的json数据")
    private String requestBodyJson;
    @ApiModelProperty("返回的状态")
    private int requestStatus;
    @ApiModelProperty("响应信息")
    private String responseMessage;
    @ApiModelProperty("请求返回的数据")
    private String responseData;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getRequestBodyJson() {
        return requestBodyJson;
    }

    public void setRequestBodyJson(String requestBodyJson) {
        this.requestBodyJson = requestBodyJson;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getRequestUrl() {

        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
