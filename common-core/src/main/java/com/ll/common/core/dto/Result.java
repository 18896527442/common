package com.ll.common.core.dto;

import com.ll.common.core.constant.ResponseCode;
import lombok.Data;

import java.io.Serializable;


@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -1L;

    public Result(){
        super();
    }

    public Result(Integer code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(T data){
        super();
        this.data=data;
    }

    /** 消息码. */
    private Integer code = ResponseCode.SUCCESS.getCode();

    /** 提示信息. */
    private String msg = ResponseCode.SUCCESS.getMessage();

    /** 具体的内容. */
    private T data;

}