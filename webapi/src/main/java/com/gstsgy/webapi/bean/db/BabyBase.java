package com.gstsgy.webapi.bean.db;

import com.gstsgy.base.bean.db.BusinessTable;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "baby基础信息")
@Data
@Entity
public class BabyBase extends BusinessTable {

    @Schema(description = "")
    private Long familyId;

    @Schema(description = "姓名")
    private String nickName;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "生日")
    private String birthday;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "电话号")
    private String phone;

    @Schema(description = "体温")
    private String temperature;

    @Schema(description = "体重")
    private String weight;

    @Schema(description = "疫苗")
    private String vaccine;

}