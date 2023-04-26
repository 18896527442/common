package com.ll.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ll.common.utils.DateUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class SmartMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "createTime", DateUtil.current());
        this.fillStrategy(metaObject, "updateTime", DateUtil.current());
        this.fillStrategy(metaObject, "isEnabled", 1);
        this.fillStrategy(metaObject, "isDeleted", 0);
        this.fillStrategy(metaObject, "displaySort", 1);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "updateTime", DateUtil.current());
    }

    @Override
    public SmartMetaObjectHandler fillStrategy(MetaObject metaObject, String fieldName, Object fieldVal) {
        try{
            Object value = metaObject.getValue(fieldName);
            if (null != value) return this;
        }catch (Exception e){

        }

        setFieldValByName(fieldName, fieldVal, metaObject);
        return this;
    }
}
