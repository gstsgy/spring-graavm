package com.gstsgy.permission.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "单表列表")
@Data
@Entity
public class FormSingle extends BaseTable {
    @Schema(description = "表单id")
    private Long formId;

    @Schema(description = "")
    private String code;

    @Schema(description = "")
    private String label;

    @Schema(description = "")
    private String type;

    @Schema(description = "")
    private Integer isShow;

    @Schema(description = "")
    private Integer disable;

    @Schema(description = "")
    private Integer required;

    @Schema(description = "")
    private String trigger;

    @Schema(description = "")
    private Integer order;

    @Schema(description = "数据字典model值")
    private String optionModel;

    @Schema(description = "默认值")
    private String defaultValue;

    @Schema(description = "宽度")
    private Integer width;

    @Schema(description = "是否排序")
    private Boolean isSort;

    @Schema(description = "是否开启过滤")
    private Boolean isFilter;

}