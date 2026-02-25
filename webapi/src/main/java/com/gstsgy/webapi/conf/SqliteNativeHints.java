package com.gstsgy.webapi.conf;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.hibernate.community.dialect.SQLiteDialect;

public class SqliteNativeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        // 显式注册方言类，防止被 GraalVM 优化掉
        hints.reflection().registerType(SQLiteDialect.class, MemberCategory.values());
    }
}

