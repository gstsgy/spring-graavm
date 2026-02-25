package com.gstsgy.base.bean.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class QueryVO {
    private String orderBy;
    private boolean asc;

    public void setOrderBy(String orderBy){
        if(StringUtils.hasText(orderBy)){
            boolean isValid = orderBy.matches("^[a-zA-Z_][a-zA-Z0-9_]{0,19}$");
            if(isValid){
                this.orderBy = orderBy;
            }
        }
    }
}
