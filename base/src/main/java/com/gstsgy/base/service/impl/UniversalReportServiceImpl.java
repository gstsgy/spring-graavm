package com.gstsgy.base.service.impl;

import com.gstsgy.base.conf.CamelCaseColumnMapRowMapper;
import com.gstsgy.base.service.UniversalReportService;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UniversalReportServiceImpl implements UniversalReportService {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private freemarker.template.Configuration freemarkerConfig;

    private static final CamelCaseColumnMapRowMapper ROW_MAPPER = new CamelCaseColumnMapRowMapper();

    // FreeMarker 配置
    //private final Configuration freemarkerConfig = new Configuration(Configuration.VERSION_2_3_32);

    public List<Map<String, Object>> execute(String fileName,String methodName, Map<String, Object> params) {
        try {
            // 2. 执行查询 (Map 进，Map 出)
            return jdbcTemplate.query(getSqlFromMacro(fileName,methodName,params), params,ROW_MAPPER);
        } catch (Exception e) {
            throw new RuntimeException("SQL 渲染或执行失败: " + e.getMessage());
        }
    }
    public Map<String, Object> pageExecute(String fileName, String methodName, Map<String, Object> params) {
        try {
            // 1. 获取基础 SQL (由 FreeMarker 渲染后的)
            String baseSql = getSqlFromMacro(fileName, methodName, params);

            // 2. 处理分页参数 (默认第1页，每页10条)
            int page = Integer.parseInt(params.getOrDefault("pageNum", "1").toString());
            int size = Integer.parseInt(params.getOrDefault("pageSize", "10").toString());
            int offset = (page - 1) * size;

            // 3. 构造总数查询 SQL (将原 SQL 包裹)
            String countSql = "SELECT COUNT(*) FROM (" + baseSql + ") total";
            Long total = jdbcTemplate.queryForObject(countSql, params, Long.class);
            if(total==0){
                return Map.of(
                        "total",  0,
                        "records", Collections.emptyList()
                );
            }

            // 4. 构造分页查询 SQL (拼接 LIMIT)
            String pageSql = baseSql + " LIMIT " + offset + ", " + size;
            List<Map<String, Object>> data = jdbcTemplate.query(pageSql, params, ROW_MAPPER);

            // 5. 返回标准分页格式
            return Map.of(
                    "total", total != null ? total : 0,
                    "records", data
            );
        } catch (Exception e) {
            throw new RuntimeException("分页查询执行失败: " + e.getMessage(), e);
        }
    }

    /**
     * @param templatePath 模板相对于 resource/report 的路径，如 "user_sql.ftl"
     * @param macroName    要调用的宏名，如 "findList"
     * @param data         传入的参数 Map (包含 userId, desc 等)
     */
    public String getSqlFromMacro(String templatePath, String macroName, Map<String, Object> data) throws IOException, TemplateException {
        // 1. 构造一个临时的“执行脚本”
        // 效果等同于在 ftl 中写：<#import 'xxx.ftl' as t><@t.findList />
        String loaderScript = String.format("<#import '%s' as t><@t.%s />", templatePath+".ftl", macroName);

        // 2. 基于配置创建一个匿名模板
        // freemarkerConfig 会自动根据你之前的配置去 classpath:/report/ 下找文件
        Template temp = new Template("loader", loaderScript, freemarkerConfig);

        // 3. 执行渲染
        StringWriter out = new StringWriter();
        temp.process(data, out);

        // 4. 返回渲染后的 SQL 字符串
        return out.toString();
    }

// SQL 样例
//    SELECT * FROM dictionary
//    WHERE 1=1
//<#if name?? && name != "">
//    AND name LIKE :name
//            </#if>
//<#if type??>
//    AND type = :type
//            </#if>

}

