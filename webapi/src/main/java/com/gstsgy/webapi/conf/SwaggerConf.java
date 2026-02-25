package com.gstsgy.webapi.conf;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConf {
    @Bean
    public GroupedOpenApi springOpenAPI() {
        return GroupedOpenApi.builder()
                .packagesToScan("com.gstsgy.webapi.controller")
                .group("测试接口")
                .build();
    }
}
