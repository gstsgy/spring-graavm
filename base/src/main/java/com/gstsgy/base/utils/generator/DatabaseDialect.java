package com.gstsgy.base.utils.generator;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseDialect {
    String getTableInfo(String tableName);

    String getTableComment(ResultSet rs) throws SQLException;
    // 获取查询列信息的 SQL (SQLite 和 MySQL 略有不同)
    String getColumnsSql(String tableName);

    // 将数据库类型转换为 Java 类型
    String mapJavaType(String typeName);

    // 从 ResultSet 中获取列名
    String getColumnName(ResultSet rs) throws SQLException;

    String getColumnType(ResultSet rs) throws SQLException;

    String getColumnComment(ResultSet rs) throws SQLException;
}

