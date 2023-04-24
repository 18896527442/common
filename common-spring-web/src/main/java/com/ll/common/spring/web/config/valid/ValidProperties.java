package com.ll.common.spring.web.config.valid;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ll.valid")
@Data
public class ValidProperties {
    private boolean enable = true;
    private String basePackage = "ll.smart";
}
