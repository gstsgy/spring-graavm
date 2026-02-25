package com.gstsgy.base.utils;

import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DataSourceUtils {
    private static DataSource dataSource;

    private static String dataBaseName;

    public static String getDataBaseName() {
        if (StringUtils.hasText(dataBaseName)) {
            return dataBaseName;
        }
        if (dataSource == null) {
            dataSource = SpringUtils.getBean(DataSource.class);
        }

        // 使用 try-with-resources 确保连接正确关闭
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String url = metaData.getURL();
            // 简单的解析逻辑示例，实际应用中可能需要根据数据库类型调整
            // 适用于类似 jdbc:mysql://host:port/dbname 的格式
            int lastSlashIndex = url.lastIndexOf('/');
            if (lastSlashIndex < 0 || lastSlashIndex >= url.length() - 1) {
                return "unknown";
            }

            String afterSlash = url.substring(lastSlashIndex + 1);

            // 去除可能的查询参数
            int questionMarkIndex = afterSlash.indexOf('?');
            if (questionMarkIndex >= 0) {
                return afterSlash.substring(0, questionMarkIndex);
            }

            dataBaseName = afterSlash;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get database name from DataSource", e);
        }

        return dataBaseName;
    }

}