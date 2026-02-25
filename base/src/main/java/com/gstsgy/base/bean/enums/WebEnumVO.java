package com.gstsgy.base.bean.enums;

import lombok.Data;

@Data
public class WebEnumVO {
    private Object value;  //

    private String label;

    public WebEnumVO(){

    }

    public WebEnumVO(Object value, String label) {
        this.value = value;
        this.label = label;
    }
}

