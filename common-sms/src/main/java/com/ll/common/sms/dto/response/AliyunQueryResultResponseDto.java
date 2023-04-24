package com.ll.common.sms.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class AliyunQueryResultResponseDto {

    @JSONField(name = "Code")
    private String code;

    @JSONField(name="Message")
    private String message;

    @JSONField(name = "RequestId")
    private String requestId;

    @JSONField(name="TotalCount")
    private String totalCount;

    @JSONField(name = "SmsSendDetailDTOs")
    private AliyunSmsSendDetailDtos aliyunSmsSendDetailDtos;

}
