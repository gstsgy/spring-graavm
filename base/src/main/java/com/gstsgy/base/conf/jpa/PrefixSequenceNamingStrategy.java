package com.gstsgy.base.conf.jpa;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class PrefixSequenceNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        if (name == null) return null;

        String text = name.getText();
        // 检查是否以 _SEQ 结尾，如果是，则重构为 SEQ_ 开头
        if (text.endsWith("_SEQ")) {
            String baseName = text.substring(0, text.length() - 4);
            return Identifier.toIdentifier("SEQ_" + baseName);
        }
        return super.toPhysicalSequenceName(name, context);
    }
}

