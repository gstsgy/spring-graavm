package com.gstsgy.permission.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "请求日志")
@Data
@Entity
public class ReqLog extends BaseTable {


    private String ip;

    private String url;

    private String data;

    private String header;

    private String method;
}