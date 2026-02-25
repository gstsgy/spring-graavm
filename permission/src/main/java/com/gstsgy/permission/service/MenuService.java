package com.gstsgy.permission.service;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.Menu;
import com.gstsgy.base.service.BaseService;

public interface MenuService extends BaseService<Menu> {
    ResponseBean GetMenuTree(Integer type);
}