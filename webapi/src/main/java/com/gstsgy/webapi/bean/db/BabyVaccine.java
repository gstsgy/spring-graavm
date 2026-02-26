package com.gstsgy.webapi.bean.db;

import com.gstsgy.base.bean.db.BusinessTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "baby疫苗")
@Data
@Entity
public class BabyVaccine extends BusinessTable {


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