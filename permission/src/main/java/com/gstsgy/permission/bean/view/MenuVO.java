package com.gstsgy.permission.bean.view;

import com.gstsgy.permission.bean.db.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuVO {
    private String key;
    private String title;
    private Long id; // 用于内存匹配
    private List<MenuVO> children;

    public MenuVO(Menu it) {
        this.key = it.getId().toString();
        this.title = it.getName();
        this.id = it.getId();
    }
}
