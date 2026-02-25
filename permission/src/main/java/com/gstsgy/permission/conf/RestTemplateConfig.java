package com.gstsgy.permission.conf;

import com.gstsgy.base.conf.CustomJacksonHttpMessageConverter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // 创建 RestTemplate 实例， 我这里使用的OkHttp
        RestTemplate restTemplate =  builder
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(5))
                //.additionalInterceptors(new Resttemp())
                .build();
        // 替换默认的 Jackson 消息转换器实现
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> next = iterator.next();
            if (next instanceof MappingJackson2HttpMessageConverter) {
                iterator.remove();
                break;
            }
        }
        messageConverters.add(new CustomJacksonHttpMessageConverter());
        //restTemplate.setc
        return restTemplate;




    }
}
