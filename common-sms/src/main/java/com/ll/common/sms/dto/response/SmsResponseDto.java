package com.ll.common.sms.dto.response;

import lombok.Data;

@Data
public class SmsResponseDto {

    /**
     * 发送回执ID
     */
    private String bizId;

    /**
     * 请求状态码
     */
    private String code;

    /**
     * 状态码的描述
     */
    private String message;

    /**
     * 请求ID
     */
    private String requestId;

}
