package com.ll.common.sms.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class QiniuSmsJobIdDto {

    @JSONField(name="job_id")
    private String jobId;

}
