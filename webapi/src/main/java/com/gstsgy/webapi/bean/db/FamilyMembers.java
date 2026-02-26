package com.gstsgy.webapi.bean.db;

import com.gstsgy.base.bean.db.BusinessTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "家庭成员")
@Data
@Entity
public class FamilyMembers extends BusinessTable {


    @Schema(description = "")
    private Long familyId;

    @Schema(description = "")
    private Long userId;

    @Schema(description = "家庭角色")
    private Integer type;

}