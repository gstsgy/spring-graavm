package com.gstsgy.permission.conf;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionSwaggerConf {
    @Bean
    public GroupedOpenApi permissionSwagger() {
        return GroupedOpenApi.builder()
                .packagesToScan("com.gstsgy.permission.controller")
                .group("权限接口")
                .build();
    }
}
