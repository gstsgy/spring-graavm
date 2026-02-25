package com.gstsgy.permission.bean.db;

import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "表单列表")
@Data
@Entity
public class FormCol extends BaseTable {
    @Schema(description = "表单id")
    private Long formId;

    @Schema(description = "code")
    private String code;

    @Schema(description = "名称")
    private String label;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "是否展示")
    private Integer isShow;

    @Schema(description = "是否禁用")
    private Integer disable;

    @Schema(description = "是否必填")
    private Integer required;

    @Schema(description = "")
    private String trigger;

    @Schema(description = "顺序")
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