package com.gstsgy.base.conf;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.support.JdbcUtils;

public class CamelCaseColumnMapRowMapper extends ColumnMapRowMapper {
    @Override
    protected String getColumnKey(String columnName) {
        // 使用 Spring 官方工具类直接转驼峰
        return JdbcUtils.convertUnderscoreNameToPropertyName(columnName);
    }
}

