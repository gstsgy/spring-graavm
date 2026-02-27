package com.gstsgy.permission.repository;

import com.gstsgy.permission.bean.db.Form;
import com.gstsgy.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface FormRepository extends BaseRepository<Form> {
    @Query(value = "SELECT \n" +
            "        table_name as tableName,\n" +
            "        table_comment as tableComment\n" +
            "    FROM \n" +
            "        information_schema.tables \n" +
            "    WHERE \n" +
            "        table_schema = :dbName  and left(table_name,5) != 'BATCH' and right(table_name,4) != '_seq';", nativeQuery = true)
    List<Map> selectTable(@Param("dbName") String dbName);

    @Query(value = "SELECT \n" +
            "        COLUMN_NAME AS columnName,\n" +
            "        DATA_TYPE AS dataType,\n" +
            "        CHARACTER_MAXIMUM_LENGTH AS length,\n" +
            "        IS_NULLABLE AS isNullable,\n" +
            "        COLUMN_KEY AS columnKey,\n" +
            "        COLUMN_DEFAULT AS defaultValue,\n" +
            "        EXTRA AS extra,\n" +
            "        COLUMN_COMMENT AS columnComment\n" +
            "    FROM \n" +
            "        INFORMATION_SCHEMA.COLUMNS \n" +
            "    WHERE \n" +
            "        TABLE_SCHEMA = :dbName \n" +
            "        AND TABLE_NAME = :tableName;",nativeQuery = true)
    List<Map> selectTableColumn(@Param("dbName")String dbName, @Param("tableName")String tableName);
}