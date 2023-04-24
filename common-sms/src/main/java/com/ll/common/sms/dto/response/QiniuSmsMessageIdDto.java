package com.ll.common.sms.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class QiniuSmsMessageIdDto {

    @JSONField(name="message_id")
    private String messageId;

}
