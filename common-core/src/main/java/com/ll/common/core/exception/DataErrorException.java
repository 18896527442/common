package com.ll.common.core.exception;


import com.ll.common.core.constant.ResponseCode;

/**
 * 数据异常
 */
public class DataErrorException extends BusinessException{
    public DataErrorException() {
        this(ResponseCode.DATA_ERROR.getMessage());
    }

    public DataErrorException(String errorMsgTemplate, Object... params) {
        super(ResponseCode.DATA_ERROR.getCode(), errorMsgTemplate, params);
    }
}
