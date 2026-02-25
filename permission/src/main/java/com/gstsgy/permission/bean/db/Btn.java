package com.gstsgy.permission.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "按钮表")
@Data
@Entity
public class Btn extends BaseTable {
    @Schema(description = "")
    private String name;

    @Schema(description = "")
    private String type;

    @Schema(description = "")
    private String code;

    @Schema(description = "")
    private String icon;

}