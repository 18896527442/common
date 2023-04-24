package com.ll.common.core.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ll.common.core.constant.ResponseCode;
import com.ll.common.core.dto.Result;
import com.ll.common.core.utils.ResultUtil;
import lombok.Data;

import java.util.Arrays;

@Data
public class BusinessException extends RuntimeException {

    private Integer code;
    private String errMsg;
    private Object data;

    public BusinessException(Integer code, String errMsg, Object... data) {
        super(ResultUtil.getErrorMsg(code, errMsg));
        this.code = code;
        this.errMsg = errMsg;
        if(!(ObjectUtil.isNull(data) || CollUtil.isEmpty(Arrays.asList((Object[]) data)))) {
            this.data = data;
        }
    }

//    public BusinessException(Integer code, String errMsg) {
//        super(ResultUtil.getErrorMsg(code, errMsg));
//        this.code = code;
//        this.errMsg = errMsg;
//    }

    public BusinessException(ResponseCode responseCode) {
        super(ResultUtil.getErrorMsg(responseCode.getCode(), responseCode.getMessage()));
        this.code = responseCode.getCode();
        this.errMsg = responseCode.getMessage();
    }

//    public BusinessException(String label, String message) {
//        super(ResultUtil.getErrorMsg(ResponseCode.SERVER_VALIDATION.getCode(), ResponseCode.SERVER_VALIDATION.getMessage()));
//        List<ErrorLabelsDto> errorLabelsDtoList = new ArrayList<>();
//        ErrorLabelsDto errorLabelsDto = new ErrorLabelsDto();
//        errorLabelsDto.setMessage(message);
//        errorLabelsDto.setLabel(label);
//        errorLabelsDtoList.add(errorLabelsDto);
//
//        this.code = ResponseCode.SERVER_VALIDATION.getCode();
//        this.errMsg = ResponseCode.SERVER_VALIDATION.getMessage();
//        this.data = errorLabelsDtoList;
//    }

    public BusinessException(String message) {
//        super(ResultUtil.getErrorMsg(ResponseCode.DATA_ERROR.getCode(), message));
        this.code = ResponseCode.DATA_ERROR.getCode();
        this.errMsg = message;
    }

    public Result<Object> getResult(){
        Result<Object> bean = new Result<>();
        bean.setMsg(getMessage());
        bean.setCode(getCode());
        return bean;
    }

}
