package com.gstsgy.webapi.bean.db;

import com.gstsgy.base.bean.db.BusinessTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "消费流水")
@Data
@Entity
public class Transactions extends BusinessTable {

    @Schema(description = "")
    private Long userId;

    @Schema(description = "")
    private Integer model;

    @Schema(description = "")
    private String time;

    @Schema(description = "")
    private String value;

    @Schema(description = "")
    private String type;

    @Schema(description = "")
    private Long accountId;

    @Schema(description = "")
    private String desc;

}