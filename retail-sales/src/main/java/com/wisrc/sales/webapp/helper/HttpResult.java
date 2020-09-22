package com.wisrc.sales.webapp.helper;

import lombok.Data;

/**
 * Created by fate
 * http reqeust result
 */
@Data
public class HttpResult {

    // 响应码
    private Integer code;

    // 响应体
    private String body;

    public HttpResult() {
        super();
    }

    public HttpResult(Integer code, String body) {
        super();
        this.code = code;
        this.body = body;
    }


}