package com.gstsgy.permission.bean.view;

import com.gstsgy.permission.bean.db.FormBtn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FormBtnVO extends FormBtn {
    @Schema(description = "名称")
    private String name;

    public FormBtnVO(){

    }

    public FormBtnVO(Long id,String name){
        this.setId(id);
        this.name = name;
    }
}
