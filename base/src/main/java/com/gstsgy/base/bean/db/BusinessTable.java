package com.gstsgy.base.bean.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BusinessTable extends BaseTable{
    private String remark;//备用字段

    private String remark1;//备用字段

    private String remark2;//备用字段

    private String remark3;//备用字段

    private String remark4;//备用字段

    private String remark5;//备用字段
}
