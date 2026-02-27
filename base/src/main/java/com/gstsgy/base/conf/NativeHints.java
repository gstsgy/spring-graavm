package com.gstsgy.base.conf;

import com.gstsgy.base.bean.dto.PageQueryVO;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class NativeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.resources().registerPattern("report/*.ftl");
        hints.resources().registerType(PageQueryVO.class);
    }
}

