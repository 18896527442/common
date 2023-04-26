package com.ll.common.core.exception;

import com.ll.common.core.constant.ResponseCode;

/**
 * 用户账号使用受限
 */
public class UseLimitErrorException extends BusinessException{

    public UseLimitErrorException() {
        this(ResponseCode.USE_LIMIT.getMessage());
    }

    public UseLimitErrorException(String errorMsgTemplate, Object... params) {
        super(ResponseCode.USE_LIMIT.getCode(), errorMsgTemplate, params);
    }
}
