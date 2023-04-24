package com.ll.common.core.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName PageDto
 * @Description
 * @Author zhangheng 
 * @Date 2018/12/26 18:16
 **/
@Data
public class PageRequest<T> implements Serializable {

    private Integer pageNum;

    private Integer pageSize;

    private T params;
}
