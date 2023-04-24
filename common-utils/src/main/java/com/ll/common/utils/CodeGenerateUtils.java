package com.ll.common.utils;

import cn.hutool.core.util.ReflectUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Field;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 编码生成器(暂时采用uuid代替)
 */
@Component
public class CodeGenerateUtils<T> {

	@Autowired
	private SnowflakeIdUtils snowflakeIdUtils;

	public String getCode() {
		return snowflakeIdUtils.snowflakeId().toString();
	}

	/**
	 * 获取对应的编码
	 * @param entity :业务对象
	 * @return code
	 */
	public String getCode(T entity) {

		return (String) ReflectUtil.getFieldValue(entity, "id");
	}
}
