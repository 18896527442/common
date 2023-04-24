package com.ll.common.sms.constant;

public class QiniuErrorConstant {

    //失败原因转换


    private String code;
    private String message;

    private QiniuErrorConstant(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
