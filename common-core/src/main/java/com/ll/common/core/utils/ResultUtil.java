package com.ll.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.ll.common.core.constant.ResponseCode;
import com.ll.common.core.dto.Result;

/**
 * @author zhangheng
 */
public class ResultUtil {

    public static <T> Result<T> success(String msg) {
        return new Result<>(ResponseCode.SUCCESS.getCode(), msg, null);
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.setCode(ResponseCode.SUCCESS.getCode());
        result.setMsg(ResponseCode.SUCCESS.getMessage());
        result.setData(object);
        return result;
    }

    public static <T> Result<T> success(T t, String msg) {
        return new Result<>(ResponseCode.SUCCESS.getCode(), msg, t);
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(ResponseCode.DATA_ERROR.getCode(), msg, null);
    }

    public static <T> Result<T> error(Integer code,String msg,T t) {
        return new Result<>(code, msg, t);
    }


    public static Result isSuccess(Integer isSuccess) {
        Result result = new Result();
        if (isSuccess != null && isSuccess > 0) {
            result.setCode(ResponseCode.SUCCESS.getCode());
            result.setMsg(ResponseCode.SUCCESS.getMessage());
        } else {
            result.setCode(ResponseCode.DATA_ERROR.getCode());
            result.setMsg(ResponseCode.DATA_ERROR.getMessage());
        }

        return result;
    }

    public static String getErrorMsg(Integer code, String msg) {
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return JSON.toJSONString(result);
    }
}
