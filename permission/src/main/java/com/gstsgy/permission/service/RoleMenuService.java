package com.gstsgy.permission.service;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.RoleMenu;
import com.gstsgy.base.service.BaseService;

public interface RoleMenuService extends BaseService<RoleMenu> {
    ResponseBean getAllMenuTree(Integer type);

    ResponseBean getAllMenuTree(Integer type, Long userId);

    ResponseBean getAllFuns(Integer type, Long userId);
}