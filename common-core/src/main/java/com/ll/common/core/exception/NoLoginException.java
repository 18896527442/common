package com.ll.common.core.exception;


import com.smart.ll.core.constant.ResponseCode;

/**
 * 未登陆异常
 */
public class NoLoginException extends BusinessException{
    public NoLoginException() {
        this(ResponseCode.NOT_LOGGED_IN.getMessage());
    }

    public NoLoginException(String errorMsgTemplate, Object... params) {
        super(ResponseCode.NOT_LOGGED_IN.getCode(), errorMsgTemplate, params);
    }
}
