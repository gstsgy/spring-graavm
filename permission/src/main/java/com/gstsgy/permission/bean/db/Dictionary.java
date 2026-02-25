package com.gstsgy.permission.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "数据字典")
@Data
@Entity
public class Dictionary extends BaseTable {
    @Schema(description = "model编码")
    private String modelCode;

    @Schema(description = "字典key")
    private String dictKey;

    @Schema(description = "字典值")
    private String dictValue;

    @Schema(description = "顺序")
    private Integer seq;

    @Schema(description = "")
    private Long parentId;

}