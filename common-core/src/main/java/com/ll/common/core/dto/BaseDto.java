package com.ll.common.core.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BaseDto {
    private Integer current=1;

    private Integer pageSize=20;
}
