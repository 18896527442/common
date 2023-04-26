package com.ll.common.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ll.common.redis.annotation.SmtCachePut;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface SmartBaseMapper<T> extends BaseMapper<T> {
    /**
     * 重新更新方法，返回实体对象
     * @param entity
     * @param updateWrapper
     * @param id
     * @return
     */
    @SmtCachePut()
    default T update(@Param("et") T entity, @Param("ew") Wrapper<T> updateWrapper, String id) {
        T ret = null;
        int update = update(entity, updateWrapper);
        if (update != 0) {
            ret = selectById(id);
        }
        return ret;
    }

    /**
     * 批量插入数据
     * @param entityList
     * @return
     */
    Integer insertBatchSomeColumn(Collection<T> entityList);

    default Integer insertBatchSomeColumn(List<T> list, boolean b) throws IllegalAccessException {
        if (b && CollectionUtils.isNotEmpty(list)) {
            for (T t : list) {
                setValueIfNull(t);
            }
        }
        return insertBatchSomeColumn(list);
    }

    default void setValueIfNull(T t) throws IllegalAccessException {
        Field[] declaredFields = t.getClass().getDeclaredFields();
        setDefaultValueByType(declaredFields, t);

        Field[] superFields = t.getClass().getSuperclass().getDeclaredFields();
        setDefaultValueByType(superFields, t);
    }

    default void setDefaultValueByType(Field[] superFields, T t) throws IllegalAccessException {
        for (Field declaredField : superFields) {
            declaredField.setAccessible(true);
            if (declaredField.get(t) != null) {
                continue;
            }
            if (declaredField.getType().equals(String.class)) {
                declaredField.set(t, StringUtils.EMPTY);
            }
            if (declaredField.getType().equals(Integer.class)) {
                declaredField.set(t, 0);
            }
            if (declaredField.getType().equals(BigDecimal.class)) {
                declaredField.set(t, BigDecimal.ZERO);
            }
            if (declaredField.getType().equals(Date.class)) {
                declaredField.set(t, new Date());
            }
        }
    }
}
