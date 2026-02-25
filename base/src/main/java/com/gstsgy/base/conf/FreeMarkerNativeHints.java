package com.gstsgy.base.conf;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class FreeMarkerNativeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        // 1. 注册 FreeMarker 渲染核心类
        hints.reflection().registerType(freemarker.template.Configuration.class,
                MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);

        // 2. 注册 Map 接口及其实现类（因为 SQL 参数是存放在 Map 里的）
        hints.reflection().registerType(java.util.Map.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(java.util.HashMap.class, MemberCategory.INVOKE_PUBLIC_METHODS);

        // 3. 注册 FreeMarker 内部模型类 (渲染模板必用)
        hints.reflection().registerType(freemarker.template.Template.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(freemarker.core.Environment.class, MemberCategory.INVOKE_PUBLIC_METHODS);
    }
}

