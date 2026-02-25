package com.gstsgy.base.bean.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
@Data
public class PageQueryVO extends QueryVO{
    private int pageNum;
    private int pageSize;
    
} 
