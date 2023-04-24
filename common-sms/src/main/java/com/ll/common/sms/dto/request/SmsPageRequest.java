package com.ll.common.sms.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmsPageRequest<T> implements Serializable {

    private Integer current;

    private Integer pageSize;

    private T params;

}
