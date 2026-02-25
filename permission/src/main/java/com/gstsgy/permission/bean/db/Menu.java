package com.gstsgy.permission.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import com.gstsgy.base.bean.enums.BooleanEnum;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "菜单")
@Data
@Entity
public class Menu extends BaseTable {
    @Schema(description = "")
    private Integer seq;

    @Schema(description = "name")
    private String name;

    @Schema(description = "")
    private Long parentId;

    @Schema(description = "")
    private String ico;

    @Schema(description = "router")
    private String path;

    @Schema(description = "vue file where is ")
    private String posi;

    @Schema(description = "one bs,two cs")
    private Integer type;

    @Schema(description = "")
    private BooleanEnum isLeaf;

}