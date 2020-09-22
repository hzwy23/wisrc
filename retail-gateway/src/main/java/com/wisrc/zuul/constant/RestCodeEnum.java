package com.wisrc.zuul.constant;

public enum RestCodeEnum {


    /****************************   码表  **********************************/

    SUCCESS(200, "Success"),
    ERROR_400_JSON(400, "Bad Request"),
    ERROR_401_JSON(401, "Unauthorized"),
    ERROR_403_JSON(403, "Forbidden"),
    ERROR_404_JSON(404, "Not Found"),
    ERROR_415_JSON(415, "Unsupported Media Type"),
    ERROR_500_JSON(500, "Internal Server Error"),
    LOGIN_FAILED(100001, "用户名或密码错误"),
    UNHANDLED_EXCEPTION(100002, "未处理异常"),
    ACCESS_DENIED(100403, "Access Denied"),
    NO_LOGIN(200403, "登陆信息无效，请重新登陆"),
    ROUTE_ID_NOT_FOUND(100100, "路由ID不存在"),

    ZUUL_WHITE_LIST_DELETE_FAILED(3000005, "删除白名单失败"),
    ZUUL_WHITE_LIST_ADD_FAILED(3000004, "新增白名单失败"),
    ZUUL_WHITE_LIST_NO_DATA(3000003, "白名单ID不存在"),
    ZUUL_ROUTE_UPDATE_ERROR(3000002, "更新路由配置信息失败"),
    ZUUL_ROUTE_ADD_ERROR(3000001, "添加路由配置信息失败");

    /*********************************************************************/

    private final int code;
    private final String message;

    // 枚举默认构造函数为 private
    RestCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
