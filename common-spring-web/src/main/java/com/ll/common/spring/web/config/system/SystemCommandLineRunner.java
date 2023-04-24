package com.ll.common.spring.web.config.system;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author chensiyuan26
 * @date 2021/8/19
 */
@Component
@Order(value = 1)
@Slf4j
public class SystemCommandLineRunner implements CommandLineRunner {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private String port;

    @Value("${management.server.port}")
    private String actuatorPort;

//    @Value("${server.servlet.context-path}")
//    private String path;


    @Override
    public void run(String... args) throws Exception {
        System.out.println(StrUtil.format("{}项目启动成功！访问地址：http://localhost:{}", applicationName,port));
        System.out.println(StrUtil.format("Swagger接口访问：http://localhost:{}/swagger-ui/index.html",port));
        System.out.println(StrUtil.format("API文档访问：http://localhost:{}/doc.html",port));
        System.out.println(StrUtil.format("API文档访问：http://localhost:{}/v2/api-docs",port));
        System.out.println(StrUtil.format("Druid访问：http://localhost:{}/druid",port));
        System.out.println(StrUtil.format("Actuator访问：http://localhost:{}/actuator",actuatorPort));

        sendMessage();
    }

    public void sendMessage(){

    }

}


