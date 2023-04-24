package com.ll.common.sms.constant;

import java.util.Objects;

public enum ChannelTypeConstant {

    AliyunSMS(1, "aliyunSms"),

    QiniuSMS(2, "qiniuSms");

    ChannelTypeConstant(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * code
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 获取messageType 类型字段
     *
     * @param code
     * @return
     */
    public static ChannelTypeConstant getMessageType(Integer code) {
        for (ChannelTypeConstant data : ChannelTypeConstant.values()) {
            if (Objects.equals(data.getCode(), code)) {
                return data;
            }
        }
        return null;
    }

}
