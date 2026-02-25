package com.gstsgy.permission.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "表单表")
@Data
@Entity
public class Form extends BaseTable {
    @Schema(description = "")
    private String serverUrl;

    @Schema(description = "")
    private String path;

    @Schema(description = "")
    private String name;

    @Schema(description = "是否开启多选框")
    private Boolean isSelected;

}