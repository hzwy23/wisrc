package com.wisrc.quality.webapp.utils;

public enum ResultCode {

    /**
     * 定义所有的状态码信息
     */
    SUCCESS(200, "成功"),
    AUTHORIZATION(403, "权限不足"),
    UNKNOW_ERROR(580, "未知错误，请联系管理员"),
    INTERNAL_ERROR(1000, "链接出现错误"),
    INVALID_ARGUMENT(1001, "无效参数"),
    MISSING_ARGUMENT(1002, "缺少参数"),
    ALREADY_EXISTS(1003, "用户已存在"),
    NOT_EXISTS(1004, "用户或密码错误"),
    INVALID_PASSWORD(1005, "用户或密码错误"),
    UNAUTHORIZED_REQUEST(1006, "未被授权的请求"),
    INVALID_TOKEN(1007, "无效Token"),
    ACCESS_DENIED(1008, "访问被拒绝"),
    INVALID_REQUEST(1009, "无效请求"),
    INVALID_AUTHORIZE_CODE(1010, "非法的code"),
    TOKEN_EXPIRED(1011, "Token过期"),
    DATA_EXPIRED(1012, "数据已过期"),
    FIND_FAILED(1013, "数据查询失败"),
    CREATE_FAILED(1014, "数据创建失败"),
    UPDATE_FAILED(1015, "数据更新失败"),
    DELETE_FAILED(1016, "数据删除失败"),
    INVALID_PARENT(1017, "非法上级参数"),
    STILL_PARENT(1018, "无法操作，存在下级参数"),
    STILL_RELATE(1019, "无法操作，存在关联参数"),
    SUPER_USER_DELETE(1020, "超级用户无法被删除"),
    CODE_IS_EXISTS(1021, "权限代码已存在"),
    ERROR_URL(1022, "无效菜单链接"),
    NO_IMAGE(1023, "图片资源不存在"),
    NONESSARYAUTHORITY(1023, "链接菜单默认存在“菜单权限代码_search”，请使用其他权限代码");

    /**
     * code = 9999 为自定义错误，可以自己注明错误原因 ResultCode(9999, String errorMsg)
     */

    private int code;

    private String msg;

    ResultCode(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public static String getMsg(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.msg;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }
}