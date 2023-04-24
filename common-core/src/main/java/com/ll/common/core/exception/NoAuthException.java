package com.ll.common.core.exception;


import com.smart.ll.core.constant.ResponseCode;

/**
 * 未授权异常
 */
public class NoAuthException extends BusinessException{
    public NoAuthException() {
        this(ResponseCode.UNAUTHORIZED.getMessage());
    }

    public NoAuthException(String errorMsgTemplate, Object... params) {
        super(ResponseCode.UNAUTHORIZED.getCode(), errorMsgTemplate, params);
    }
}
