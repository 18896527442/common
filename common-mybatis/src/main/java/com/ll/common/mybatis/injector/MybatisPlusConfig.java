package com.ll.common.mybatis.injector;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MybatisPlusConfig {
    @Bean
    public SqlInjector orderSqlInjector() {
        return new SqlInjector();
    }
}
