package com.ll.common.spring.web.dto;

import lombok.Data;

/**
 * @version 1.0
 * @ClassName FeignExceptionDto
 * @Description
 * @Author zhangheng 
 * @Date 2019/8/14 19:09
 **/
@Data
public class FeignExceptionDto {

    private String timestamp;

    private Integer status;

    private String message;

    private String path;
    private String error;


}
