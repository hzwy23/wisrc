package com.wisrc.zuul.vo;


import com.wisrc.zuul.constant.RestCodeEnum;
import lombok.Data;

@Data
public class ResultBody {

    private int code;

    private String message;

    private Object data;


    public static ResultBody success() {
        ResultBody result = new ResultBody();
        result.setCode(RestCodeEnum.SUCCESS.getCode());
        result.setMessage(RestCodeEnum.SUCCESS.getMessage());
        result.setData("Bingo");
        return result;
    }


    public static ResultBody success(Object data) {
        ResultBody result = new ResultBody();
        result.setCode(RestCodeEnum.SUCCESS.getCode());
        result.setMessage(RestCodeEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }


    public static ResultBody success(RestCodeEnum code, Object data) {
        ResultBody result = new ResultBody();
        result.setCode(code.getCode());
        result.setMessage(code.getMessage());
        result.setData(data);
        return result;
    }

    public static ResultBody success(int code, String message, Object data) {
        ResultBody result = new ResultBody();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
