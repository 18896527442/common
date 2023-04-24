package com.ll.common.spring.web.interceptor;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ll.common.core.constant.ResponseCode;
import com.ll.common.core.dto.ErrorLabelsDto;
import com.ll.common.core.dto.Result;
import com.ll.common.core.exception.BusinessException;
import com.ll.common.core.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现全局的 Controller 层的异常处理
 */
@RestControllerAdvice
@Order(2)
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Result handleException(Exception e) {

        LOGGER.error(e.getMessage(), e);
        Result result = new Result();
        result.setCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
        result.setData(e.getMessage());
        result.setMsg(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
        return result;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    Result handleException(RuntimeException e) {
        LOGGER.error(e.getMessage(), e);
        Result result = new Result();
        result.setCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
        result.setMsg(e.getMessage());
        result.setData(null);
        return result;
    }

    /**
     * 处理-参数类型不符合规则读取异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        LOGGER.error(e.getMessage(), e);
        Result<String> result = new Result<>();
        result.setCode(ResponseCode.SERVER_VALIDATION.getCode());
        if (e.getCause() instanceof InvalidFormatException) {
            String message = e.getMessage();
            result.setData(message.substring(0, message.indexOf(';')));
        } else {
            result.setData(e.getMessage());
        }
        result.setMsg(ResponseCode.SERVER_VALIDATION.getMessage());
        return result;
    }

    /**
     * 处理所有业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    Result handleBusinessException(BusinessException e) {
        LOGGER.error(e.getMessage(), e);
        Result result = new Result();
        result.setCode(e.getCode());
        result.setMsg(e.getErrMsg());
        result.setData(e.getData());
        return result;
    }

    /**
     * 处理所有接口数据验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LOGGER.error(e.getMessage(), e);
        Result result = new Result();
        result.setCode(ResponseCode.SERVER_VALIDATION.getCode());
        result.setMsg(ResponseCode.SERVER_VALIDATION.getMessage());
        List<ObjectError> list1 = e.getBindingResult().getAllErrors();
        List<FieldError> list2 = e.getBindingResult().getFieldErrors();

        List<ErrorLabelsDto> errorLabelsDtoList = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            ErrorLabelsDto errorLabelsDto = new ErrorLabelsDto();
            errorLabelsDto.setMessage(fieldError.getDefaultMessage());
            errorLabelsDto.setLabel(fieldError.getField());
            errorLabelsDtoList.add(errorLabelsDto);
        });
        result.setData(errorLabelsDtoList);
        return result;
    }


    @ExceptionHandler({NotLoginException.class})
    @ResponseBody
    public Result handleNotLoginException(NotLoginException e) {
        LOGGER.error(e.getMessage(), e);
        Result result = new Result();

        if(e.getType().equals(NotLoginException.BE_REPLACED) || e.getType().equals(NotLoginException.KICK_OUT)){
            result.setCode(ResponseCode.KICKOUT.getCode());
            result.setData(e.getMessage());
            result.setMsg(ResponseCode.KICKOUT.getMessage());
        } else {
            result.setCode(ResponseCode.NOT_LOGGED_IN.getCode());
            result.setData(e.getMessage());
            result.setMsg(ResponseCode.NOT_LOGGED_IN.getMessage());
        }

        return result;
    }

    @ExceptionHandler({NotPermissionException.class, NotRoleException.class})
    @ResponseBody
    public Result handleNotPermissionException(Exception e) {
        LOGGER.error(e.getMessage(), e);

        
        Result result = new Result();
        result.setCode(ResponseCode.UNAUTHORIZED.getCode());
        result.setData(e.getMessage());
        result.setMsg(ResponseCode.UNAUTHORIZED.getMessage());
        return result;
    }

    @ExceptionHandler({SQLException.class})
    @ResponseBody
    public Result handleException(SQLException e) {
        LOGGER.error(e.getMessage(), e);
        Result result = new Result();
        result.setCode(ResponseCode.DATA_ERROR.getCode());
        result.setData(e.getMessage());
        result.setMsg(ResponseCode.DATA_ERROR.getMessage());
        return result;
    }

}
