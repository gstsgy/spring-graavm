package com.gstsgy.webapi.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "baby疫苗")
@Data
@Entity
public class BabyVaccine extends BaseTable {
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

    @Schema(description = "描述")
    private String desc;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "值")
    private String value;

}