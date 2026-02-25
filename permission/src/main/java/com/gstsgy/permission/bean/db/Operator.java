package com.gstsgy.permission.bean.db;

import com.gstsgy.base.bean.anno.NotUpdate;
import com.gstsgy.base.bean.anno.Unique;
import com.gstsgy.base.bean.db.BaseTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "操作人")
@Data
@Entity
public class Operator extends BaseTable implements Cloneable{
    @Schema(description = "ni cheng")
    private String nickName;
    @NotUpdate
    @Schema(description = "password")
    private String passwd;

    @Schema(description = "")
    private String email;

    @Schema(description = "")
    private Integer gender;

    @Schema(description = "")
    private String birthday;

    @Schema(description = "")
    private String position;

    @Unique()
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "")
    private String code;
    @NotUpdate
    @Schema(description = "")
    private String passwdUpdateYmd;

    @Schema(description = "微信唯一Id")
    private String wxId;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "")
    private String totpsecret;
    public Operator clone(){
        try {
            return (Operator) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}