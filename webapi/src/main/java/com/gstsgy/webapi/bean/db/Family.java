package com.gstsgy.webapi.bean.db;

import com.gstsgy.base.bean.db.BusinessTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "家庭")
@Data
@Entity
public class Family extends BusinessTable {


    @Schema(description = "名称")
    private String name;

    @Schema(description = "描述")
    private String desc;

}