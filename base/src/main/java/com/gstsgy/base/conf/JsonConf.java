package com.gstsgy.base.conf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.gstsgy.base.bean.enums.BaseEnum;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import java.io.IOException;

@Configuration
public class JsonConf {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance)
                        .serializerByType(Long.TYPE, ToStringSerializer.instance);
            }
        };
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer enumSerializerCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.serializerByType(BaseEnum.class, new JsonSerializer<BaseEnum>() {
            @Override
            public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeNumber(value.getValue());
            }
        });
    }
//    @Value("${mybatis-plus.type-enums-package}")
//    private String packagePattern;

//    public Set<Class<?>> getEnumCLass() throws IOException {
//        Set<Class<?>> classes = new HashSet<>();
//        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
//                + ClassUtils.convertClassNameToResourcePath(packagePattern) + "/**/*.class");
//        for (Resource resource : resources) {
//            try {
//                ClassMetadata classMetadata = new CachingMetadataReaderFactory().getMetadataReader(resource).getClassMetadata();
//                Class<?> clazz = Resources.classForName(classMetadata.getClassName());
//                if(Arrays.asList(clazz.getInterfaces()).contains(BaseEnum.class)){
//                    classes.add(clazz);
//                }
//            } catch (Throwable ignored) {
//
//            }
//        }
//        return classes;
//    }

//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer enumDeSerializerCustomizer() throws IOException {
//        Map<Class<?>,JsonDeserializer<?>> map = new HashMap<>();
//        Set<Class<?>> classes =  getEnumCLass();
//
//        for(Class<?> enumClass:classes){
//            map.put(enumClass,new EnumJsonDeserializer(enumClass));
//        }
//        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.deserializersByType(map);
//    }
}
