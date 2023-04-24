package com.ll.common.sms.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class SmsSendDetailDto {

    /**
     * 短信内容
     */
    @JSONField(name = "Content")
    private String content;

    /**
     * 运营商短信状态码
     */
    @JSONField(name = "ErrCode")
    private String errCode;

    /**
     * 外部流水扩展字段
     */
    @JSONField(name = "OutId")
    private String outId;

    /**
     * 接收短信的手机号码
     */
    @JSONField(name = "PhoneNum")
    private String phoneNum;

    /**
     * 短信接收日期和时间
     */
    @JSONField(name = "ReceiveDate")
    private String receiveDate;

    /**
     * 短信发送日期和时间
     */
    @JSONField(name = "SendDate")
    private String sendDate;

    /**
     * 短信发送状态;1等待回执，2发送成功，3发送失败
     */
    @JSONField(name = "SendStatus")
    private Integer sendStatus;

    /**
     * 短信模板ID
     */
    @JSONField(name = "TemplateCode")
    private String templateCode;

}
