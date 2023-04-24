package com.ll.common.core.constant;

public enum ResponseCode {
    SUCCESS(200,"执行成功"),
    NOT_LOGGED_IN(302,"未登录"),

    KICKOUT(303,"账号已被顶下线"),
    INTERCEPT_RESPONSE_SUCCESS(310,"拦截响应成功"),
    UNAUTHORIZED(401,"请求未授权"),
    FORBIDDEN(403,"IP未授权"),
    INTERNAL_SERVER_ERROR(500,"系统异常，请稍后再试"),
    EXCEL_VALIDATE_ERROR(505,"excel导入异常"),
    PATIENT_AREA_CODE_ERROR(506,"当前区域超出送餐范围"),
    DATA_ERROR(600,"数据异常，操作失败"),
    SUPPLIER_STATUS_WAIT(805,"无权查看"),
    USE_LIMIT(900,"用户账号使用受限"),
    SERVER_VALIDATION(999,"服务端验证错误");

    private Integer code;
    private String message;

    private ResponseCode(Integer code, String message ){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
