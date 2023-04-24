package com.ll.common.core.dto;

/**
 * @version 1.0
 * @ClassName FeignExceptionDto
 * @Description
 * @Date 2019/8/14 19:09
 **/
public class FeignExceptionDto {

    private String timestamp;

    private Integer status;

    private String message;

    private String path;
    private String error;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
