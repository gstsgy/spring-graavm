package com.gstsgy.base.utils.generator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class SqliteDialect implements DatabaseDialect {
    @Override
    public String getTableInfo(String tableName) {
        return "";
    }

    @Override
    public String getTableComment(ResultSet rs) throws SQLException {
        return "";
    }

    @Override
    public String getColumnsSql(String tableName) {
        return "PRAGMA table_info(" + tableName + ")";
    }

    @Override
    public String getColumnComment(ResultSet rs) throws SQLException {
        // SQLite PRAGMA 默认不带注释，如需注释需查询 sqlite_master 解析，这里预留空或返回默认
        return "";
    }

    @Override
    public String mapJavaType(String typeName) {
        String type = typeName.toUpperCase(Locale.ROOT);
        if (type.contains("INT")) return "Integer";
        if (type.contains("BIGINT")) return "Long"; // SQLite 建议主键用 Long
        if (type.contains("CHAR") || type.contains("TEXT")) return "String";
        if (type.contains("REAL") || type.contains("DOUBLE")) return "Double";
        if (type.contains("DATE") || type.contains("TIME")) return "java.time.LocalDateTime";
        return "String";
    }

    @Override
    public String getColumnName(ResultSet rs) throws SQLException {
        return rs.getString("name"); // SQLite PRAGMA 返回的列名键是 'name'
    }

    @Override
    public String getColumnType(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

