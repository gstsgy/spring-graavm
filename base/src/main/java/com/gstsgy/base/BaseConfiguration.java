package com.gstsgy.base;


import com.gstsgy.base.conf.FreeMarkerNativeHints;
import jakarta.persistence.EntityListeners;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@ImportRuntimeHints(FreeMarkerNativeHints.class)
@ComponentScan
public class BaseConfiguration {


}