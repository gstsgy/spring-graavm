package com.gstsgy.base.utils.generator;

import java.io.*;
import java.sql.*;
import java.util.*;

public class GeneratorPO {

    // --- 配置区 ---
    private static final String JDBC_URL = "jdbc:sqlite:file:/data/pro/t2/webapi/src/main/resources/db"; // 切换为 jdbc:mysql://... 即可自动识别
    private static final String USER = "asd";
    private static final String PASS = "aa";

    private static final Set<String> BASE_COLUMNS = Set.of(
            "id", "insert_ymd", "insert_id", "update_ymd", "update_id", "is_del", "update_flag", "effective"
    );

    public static void main(String[] args) {
        // 示例：在 webapi 模块下生成 Dictionary 类
//        generatePo("permission", "dictionary","btn","form","form_btn","form_col","form_grid","form_single",
//                "menu","operator","role","role_interface","role_menu","user_role");
        generatePo("webapi", "family_members","transactions");
    }

    public static void generatePo(String moduleName, String... tableNames ) {
        for (String tableName : tableNames) {
            String className = snakeToCamel(tableName);
            className = className.substring(0, 1).toUpperCase() + className.substring(1);
            DatabaseDialect dialect = getDialect(JDBC_URL);
            String rootPath = System.getProperty("user.dir");
            String packagePath = "com.gstsgy." + moduleName + ".bean.db";
            String fullDir = rootPath + File.separator + moduleName + "/src/main/java/" + packagePath.replace(".", "/");

            File dir = new File(fullDir);
            if (!dir.exists()) dir.mkdirs();

            try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASS);
                 Statement stmt = conn.createStatement()) {
                // 获取表注释
                String tableInfoSql = dialect.getTableInfo(tableName);
                ResultSet rs = stmt.executeQuery(tableInfoSql);
                String tableComment = "";
                while (rs.next()) {
                    tableComment = dialect.getTableComment(rs);
                }


                // SQLite 不支持某些元数据方法，统一使用方言提供的 SQL
                String sql = dialect.getColumnsSql(tableName);
                 rs = stmt.executeQuery(sql);

                StringBuilder fields = new StringBuilder();
                while (rs.next()) {
                    String colName = dialect.getColumnName(rs);
                    if (BASE_COLUMNS.contains(colName.toLowerCase())) continue;

                    // 获取类型（注意：SQLite PRAGMA 不返回 Types 枚举，通过 typeName 判断）
                    String typeName = dialect.getColumnType(rs);
//                    int dataType = 0;
//                    try { dataType = rs.getInt("DATA_TYPE"); } catch (Exception ignored) {}

                    String javaType = dialect.mapJavaType(typeName);
                    String comment = dialect.getColumnComment(rs);
                    fields.append(String.format("    @Schema(description = \"%s\")",comment)).append("\n");
                    fields.append("    private ").append(javaType).append(" ").append(snakeToCamel(colName)).append(";\n\n");
                }

                saveToFile(new File(dir, className + ".java"), packagePath, className,tableComment, fields.toString());
                System.out.println("成功生成 PO: " + className + " -> " + fullDir);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static DatabaseDialect getDialect(String url) {
        if (url.contains("sqlite")) return new SqliteDialect();
        if (url.contains("mysql")) return new MySqlDialect();
        throw new RuntimeException("暂不支持的数据库方言");
    }

    private static void saveToFile(File file, String pkg, String className,String comment, String fields) throws IOException {
        String content = "package " + pkg + ";\n\n" +
                "import com.gstsgy.base.bean.db.BaseTable;\n" +
                "import jakarta.persistence.Entity;\n" +
                "import io.swagger.v3.oas.annotations.media.Schema;\n" +
                "import lombok.Data;\n" +
                "import lombok.EqualsAndHashCode;\n\n" +
                "@EqualsAndHashCode(callSuper = true)\n" +
                "@Schema(description = \""+comment+"\")\n"+
                "@Data\n" +
                "@Entity\n" +
                "public class " + className + " extends BaseTable {\n" +
                fields +
                "}";
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.print(content);
        }
    }

    private static String snakeToCamel(String str) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpper = false;
        for (char c : str.toLowerCase().toCharArray()) {
            if (c == '_') nextUpper = true;
            else {
                if (nextUpper) { sb.append(Character.toUpperCase(c)); nextUpper = false; }
                else sb.append(c);
            }
        }
        return sb.toString();
    }
}


