package com.ll.common.mybatis.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

public class SmartLambdaQueryWrapper<T> extends LambdaQueryWrapper<T> {
    public SmartLambdaQueryWrapper() {
    }

    public SmartLambdaQueryWrapper(T entity) {
        super(entity);
    }

    public SmartLambdaQueryWrapper(Class<T> entityClass) {
        super(entityClass);
    }

    /**
     * 批量eq
     * @param obj
     * @param null2IsNull
     * @param fields
     * @return
     */
    public SmartLambdaQueryWrapper<T> eqs(T obj, boolean null2IsNull, SFunction<T, ?> ... fields){
        Assert.notEmpty(fields,"fields 不能为空");
        Assert.notNull(obj,"查询对象不能为空");
        for (SFunction<T, ?> field : fields) {
            Object val = field.apply(obj);
            if (val != null){
                eq(field,val);
            }else{
                if (null2IsNull){
                    eq(field,null);
                }
            }
        }
        return this;
    }


    public SmartLambdaQueryWrapper<T> eqs(T obj, SFunction<T, ?> ... fields){
        return eqs(obj,false,fields);
    }

    /**
     *
     * @param obj
     * @param ignoreBlank 忽略 空白字符串
     * @param fields
     * @return
     */
    public SmartLambdaQueryWrapper<T> likes(T obj, boolean ignoreBlank, SFunction<T, ?> ... fields){
        Assert.notEmpty(fields,"fields 不能为空");
        Assert.notNull(obj,"查询对象不能为空");

        for (SFunction<T, ?> field : fields) {
            Object val = field.apply(obj);
            if (StrUtil.isNotBlank((CharSequence) val)){
                like(field,val);
            }else{
                if (!ignoreBlank){
                    like(field,val);
                }
            }
        }
        return this;
    }

    /**
     * 批量like 默认忽略空白符
     * @param obj
     * @param fields
     * @return
     */
    public SmartLambdaQueryWrapper<T> likes(T obj, SFunction<T, ?> ... fields){
        return likes(obj,true,fields);
    }






    }
