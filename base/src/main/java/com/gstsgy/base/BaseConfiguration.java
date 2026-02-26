package com.gstsgy.base;


import com.gstsgy.base.conf.NativeHints;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportRuntimeHints;

@ImportRuntimeHints(NativeHints.class)
@ComponentScan
public class BaseConfiguration {


}