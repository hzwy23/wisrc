package com.wisrc.crawler.webapp.utils;

import java.io.Serializable;

public class Result implements Serializable {

    // 返回结果状态码
    private Integer code;
    // 返回结果指定消息
    private String msg;
    // 服务端响应数据，请求成功时，返回客户端想要的数据，请求失败时，可以附带一些详细信息
    private Object data;

    public Result() {
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(ResultCode resultCode) {
        this.setResultCode(resultCode);
        this.data = "";
    }

    public Result(ResultCode resultCode, Object data) {
        this.setResultCode(resultCode);
        this.data = data;
    }

    // 请求成功返回值，data里边只反馈了OK字符
    // 适用于发起删除，新增，编辑的请求后，服务端给客户端的响应信息
    public static Result success() {
        Result result = new Result();
        result.setData("OK");
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    // 请求成功后，指定返回数据
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }


    // 未知错误快速返回，适用于调试，断点或对错误详细信息不关注的API使用
    public static Result failure() {
        Result result = new Result();
        result.setData("Failure");
        result.setCode(ResultCode.UNKNOW_ERROR.code());
        result.setMsg(ResultCode.UNKNOW_ERROR.msg());
        return result;
    }

    public static Result failure(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    // 返回错误定义表中，已经定义好的错误码
    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData("Failure");
        return result;
    }

    // 返回指定的错误信息编码,和反馈给客户端的数据信息
    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.msg = resultCode.msg();
    }


    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}
