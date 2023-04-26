package com.ll.common.core.exception;

import com.ll.common.core.constant.ResponseCode;

/**
 * Excel导入异常
 */
public class ExcelErrorException extends BusinessException{
    public ExcelErrorException() {
        this(ResponseCode.EXCEL_VALIDATE_ERROR.getMessage());
    }

    public ExcelErrorException(String errorMsgTemplate, Object... params) {
        super(ResponseCode.EXCEL_VALIDATE_ERROR.getCode(), errorMsgTemplate, params);
    }
}
