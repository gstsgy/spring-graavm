package com.gstsgy.permission.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色表")
@Data
@Entity
public class Role extends BaseTable {
    @Schema(description = "")
    private String name;

    @Schema(description = "")
    private String desc;

}