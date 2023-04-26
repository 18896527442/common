package com.ll.common.spring.web.controller;


import com.ll.common.core.dto.PageRequest;

import java.util.Objects;

public abstract class BaseController {

    public BaseController() {
    }

    protected <T> PageRequest<T> checkPageRequestParams(PageRequest<T> pageRequest) {
        if (Objects.isNull(pageRequest)) {
            pageRequest = new PageRequest();
            pageRequest.setPageNum(1);
            pageRequest.setPageSize(20);
        } else {
            if (Objects.isNull(pageRequest.getPageNum()) || pageRequest.getPageNum() <= 0) {
                pageRequest.setPageNum(1);
            }

            if (Objects.isNull(pageRequest.getPageSize()) || pageRequest.getPageSize() <= 0) {
                pageRequest.setPageSize(20);
            }
        }

        return pageRequest;
    }
}