package com.ll.common.core.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ll.common.core.constant.ResponseCode;
import com.ll.common.core.dto.ErrorLabelsDto;
import com.ll.common.core.dto.Result;
import com.ll.common.utils.lambda.FieldFunction;
import com.ll.common.utils.lambda.LambdaTool;
import lombok.Getter;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务端参数验证失败异常
 */
@Getter
public class ArgumentException extends BusinessException {
   private List<FieldErrorMsg> fieldList;

    public ArgumentException(List<FieldErrorMsg> fieldList) {
        super(ResponseCode.SERVER_VALIDATION.getCode(), ResponseCode.SERVER_VALIDATION.getMessage());
        this.fieldList = fieldList;
    }

    public ArgumentException(String field, String errorMsgTemplate, Object... params) {
        super(ResponseCode.SERVER_VALIDATION.getCode(),ResponseCode.SERVER_VALIDATION.getMessage());
        this.fieldList = CollUtil.newArrayList(new FieldErrorMsg(field,errorMsgTemplate,params));
    }
    public <T> ArgumentException(FieldFunction<T,?> field, String errorMsgTemplate, Object... params) {
        super(ResponseCode.SERVER_VALIDATION.getCode(),ResponseCode.SERVER_VALIDATION.getMessage());
        this.fieldList = CollUtil.newArrayList(new FieldErrorMsg(LambdaTool.getName(field),errorMsgTemplate,params));
    }

    public static ArgumentException parse(ConstraintViolationException exception){
        List<FieldErrorMsg> list = exception.getConstraintViolations().stream().map(it -> {
            FieldErrorMsg errorMsg = new FieldErrorMsg(((PathImpl)it.getPropertyPath()).getLeafNode().getName(), it.getMessage());
            return errorMsg;

        }).collect(Collectors.toList());
        return new ArgumentException(list);
    }

    @Override
    public Result<Object> getResult() {
        Result<Object> bean = super.getResult();
        List<ErrorLabelsDto> list = fieldList.stream().map(it -> {
            ErrorLabelsDto errorLabels = new ErrorLabelsDto();
            errorLabels.setLabel(it.getField());
            errorLabels.setMessage(StrUtil.format(it.getErrorMsgTemplate(), it.params));
            return errorLabels;
        }).collect(Collectors.toList());
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("errorLabels",list);
        bean.setData(map);

        return bean;
    }

    @Getter
    public static class FieldErrorMsg{
        private String field;
        private String errorMsgTemplate;
        private Object[] params;

        public FieldErrorMsg(String field, String errorMsgTemplate, Object... params) {
            this.field = field;
            this.errorMsgTemplate = errorMsgTemplate;
            this.params = params;
        }
    }


}
