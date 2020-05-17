package com.kbmendoza.dataproj.retail.prepinvoice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sparkapp")
@Data
public class SparkAppConfig {
    private String appName;
    private String master;
}
