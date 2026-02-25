package com.gstsgy.webapi.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "家庭")
@Data
@Entity
public class Family extends BaseTable {
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

    @Schema(description = "名称")
    private String name;

    @Schema(description = "描述")
    private String desc;

}