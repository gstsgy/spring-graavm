package com.gstsgy.permission.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "")
@Data
@Entity
public class IpConf extends BaseTable {
    @Schema(description = "ip")
    private String ip;

    @Schema(description = "类型 1 黑名单，2白名单")
    private Integer type;

    @Schema(description = "描述")
    private String desc;

}