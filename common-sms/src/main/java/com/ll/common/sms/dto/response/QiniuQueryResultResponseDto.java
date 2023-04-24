package com.ll.common.sms.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class QiniuQueryResultResponseDto {

    private List<QiniuQueryResultItemResponseDto> items;

    private Integer page;

    @JSONField(name="page_size")
    private Integer pageSize;

    private Integer total;

}
