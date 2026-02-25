package com.gstsgy.base.bean.enums;

public enum BooleanEnum implements BaseEnum {
    TRUE(1,"是"),  FALSE(0,"否")

    ;
    private Integer code;
    private String name;

    BooleanEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    @Override
    public Integer getValue() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

}
