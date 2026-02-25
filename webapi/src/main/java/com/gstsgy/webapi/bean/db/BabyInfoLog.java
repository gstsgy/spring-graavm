package com.gstsgy.webapi.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "baby基础信息log")
@Data
@Entity
public class BabyInfoLog extends BaseTable {
    @Schema(description = "")
    private String remark;

    @Schema(description = "")
    private String remark1;

    @Schema(description = "")
    private String remark2;

    @Schema(description = "")
    private String remark3;

    @Schema(description = "")
    private String remark4;

    @Schema(description = "")
    private String remark5;

    @Schema(description = "")
    private Long babyId;

    @Schema(description = "日期")
    private String date;

    @Schema(description = "时间")
    private String time;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "数值")
    private String value;

}