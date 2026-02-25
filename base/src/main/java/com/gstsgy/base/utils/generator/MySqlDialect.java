package com.gstsgy.base.utils.generator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class MySqlDialect implements DatabaseDialect {
    @Override
    public String getTableInfo(String tableName) {
        return String.format("SHOW TABLE STATUS LIKE '%s';", tableName);
    }

    @Override
    public String getTableComment(ResultSet rs) throws SQLException {
        String comment = rs.getString("Comment");
        return (comment == null || comment.isEmpty()) ? "" : comment;
    }

    @Override
    public String getColumnsSql(String tableName) {
        return String.format("SHOW FULL COLUMNS FROM %s;", tableName);
    }

    @Override
    public String getColumnComment(ResultSet rs) throws SQLException {
        String comment = rs.getString("Comment");
        return (comment == null || comment.isEmpty()) ? "" : comment;
    }

    @Override
    public String mapJavaType(String typeName) {
        typeName = typeName.toLowerCase();
        if(typeName.equals("int") || typeName.equals("integer")) {
            return "Integer";
        }
        else if(typeName.equals("long") || typeName.equals("bigint")) {
            return "Long";
        }
        else if(typeName.equals("decimal") || typeName.equals("double")|| typeName.equals("float")) {
            return "java.math.BigDecimal";
        }
        else if(typeName.startsWith("bit") ) {
            return "Boolean";
        }
        else if(typeName.contains("date") || typeName.contains("time") || typeName.contains("timestamp")) {
           return  "java.time.LocalDateTime";
        }
        else{
            return "String";
        }
    }

    @Override
    public String getColumnName(ResultSet rs) throws SQLException {
        return rs.getString("Field");
    }

    @Override
    public String getColumnType(ResultSet rs) throws SQLException {
        return rs.getString("Type");
    }
}

