package com.gstsgy.base;


import com.gstsgy.base.conf.FreeMarkerNativeHints;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportRuntimeHints;

@ImportRuntimeHints(FreeMarkerNativeHints.class)
@ComponentScan
public class BaseConfiguration {


}