package com.gstsgy.webapi.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "家庭成员")
@Data
@Entity
public class FamilyMembers extends BaseTable {
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
    private Long familyId;

    @Schema(description = "")
    private Long userId;

    @Schema(description = "家庭角色")
    private Integer type;

}