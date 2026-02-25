package com.gstsgy.permission.bean.view;

import lombok.Data;

@Data
public class PageVO {
    private Integer pageSize;
    private Integer pageNum;
    private  Boolean isAsc;
    private  String   order;
}
