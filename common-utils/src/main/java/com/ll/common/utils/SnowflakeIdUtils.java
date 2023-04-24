package com.ll.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdUtils {

	private static final String ACCOUNT_PREFIX = "3";

	private final Snowflake snowflake;

	public SnowflakeIdUtils() {
		snowflake = IdUtil.getSnowflake();
	}

	public Long snowflakeId() {
		return snowflake.nextId();
	}


	public String accountSnowflakeId() {
		return new StringBuilder(ACCOUNT_PREFIX).append(snowflakeId()).toString();
	}

}
