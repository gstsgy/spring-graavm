package com.gstsgy.webapi.bean.db;

import com.gstsgy.base.bean.db.BusinessTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "账本")
@Data
@Entity
public class AccountBook extends BusinessTable {
    @Schema(description = "")
    private Long familyId;

    @Schema(description = "")
    private String name;


    @Schema(description = "")
    private String desc;
}