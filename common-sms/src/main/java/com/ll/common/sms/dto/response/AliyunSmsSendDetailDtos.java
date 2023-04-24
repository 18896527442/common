package com.ll.common.sms.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class AliyunSmsSendDetailDtos {

    @JSONField(name = "SmsSendDetailDTO")
    private List<SmsSendDetailDto> smsSendDetailDto;
}
