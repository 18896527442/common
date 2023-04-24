package com.ll.common.sms.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class QiniuQueryResultItemResponseDto {

    private String content;

    private Integer count;

    @JSONField(name="created_at")
    private Date createdAt;

    @JSONField(name="delivrd_at")
    private Date delivrdAt;

    private String error;

    @JSONField(name="job_id")
    private String jobId;

    @JSONField(name="message_id")
    private String messageId;

    private String mobile;

    private String status;

    private String type;

}
