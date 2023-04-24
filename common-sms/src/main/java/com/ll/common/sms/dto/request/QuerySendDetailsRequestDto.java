package com.ll.common.sms.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class QuerySendDetailsRequestDto {

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 发送日期
     */
    private Date sendDate;

    /**
     * 流水号，消息id
     */
    private String bizId;

    /**
     *  发送任务返回的 id
     */
    private String outId;

}
