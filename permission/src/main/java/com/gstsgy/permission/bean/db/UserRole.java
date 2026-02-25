package com.gstsgy.permission.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户角色")
@Data
@Entity
public class UserRole extends BaseTable {
    @Schema(description = "")
    private Long userId;

    @Schema(description = "")
    private Long roleId;

}