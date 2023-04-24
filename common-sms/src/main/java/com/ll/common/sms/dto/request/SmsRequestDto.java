package com.ll.common.sms.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SmsRequestDto {

    /**
     * 接收短信的手机号码
     */
    private String[] phoneNumber;

    /**
     * 短信模板ID
     */
    private String templateCode;

    /**
     * 短信模板变量对应的实际值
     */
    private List<TemplateParamDto> templateParam;

    /**
     * 上行短信扩展码，无特殊需要此字段的用户请忽略此字段
     */
    private String smsUpExtendCode;

    /**
     * 外部流水扩展字段
     */
    private String outId;

}
