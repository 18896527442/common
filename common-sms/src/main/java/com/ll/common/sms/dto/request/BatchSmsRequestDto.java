package com.ll.common.sms.dto.request;

import lombok.Data;

@Data
public class BatchSmsRequestDto {

    /**
     * 接收短信的手机号码，JSON数组格式
     */
    private String phoneNumberJson;

    /**
     * 短信签名名称，JSON数组格式
     */
    private String signNameJson;

    /**
     * 短信模板CODE
     */
    private String templateCode;

    /**
     * 上行短信扩展码，JSON数组格式
     */
    private String smsUpExtendCodeJson;

    /**
     * 短信模板变量对应的实际值，JSON格式
     */
    private String templateParamJson;


}
