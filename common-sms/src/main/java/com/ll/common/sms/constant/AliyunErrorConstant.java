package com.ll.common.sms.constant;

public enum AliyunErrorConstant {

    SMS_1005("-1005","内容含有违禁词"),
    SMS_UNDELIV("UNDELIV","失败,建议联系平台核查原因"),
    SMS_REJECTD("REJECTD","驳回"),
    SMS_MBBLACK("MBBLACK","黑名单"),
    SMS_KEYWORD("KEYWORD","关键字屏蔽"),
    SMS_10("-10","余额不足"),
    SMS_37("-37","关机"),
    SMS_DELIVRD("DELIVRD","成功");


    private String code;
    private String message;

    private AliyunErrorConstant(String code, String message) {
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
