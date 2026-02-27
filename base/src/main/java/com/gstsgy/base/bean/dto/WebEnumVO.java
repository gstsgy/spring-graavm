package com.gstsgy.base.bean.dto;

import lombok.Data;

import java.util.List;

/**
 * @Classname WebEnum
 * @Description TODO
 * @Date 2020/12/25 上午11:06
 * @Created by guyue
 */
@Data
public class WebEnumVO {
    private Long id;

    private String model;

    private Object value;  //

    private String label;

    private List<WebEnumVO> children;

    public WebEnumVO(){

    }

    public WebEnumVO(Object value, String label) {
        this.value = value;
        this.label = label;
    }
}
