package com.gstsgy.permission.service;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.RoleMenu;
import com.gstsgy.base.service.BaseService;

import java.util.List;

public interface RoleMenuService extends BaseService<RoleMenu> {
    ResponseBean getAllMenuTree(Integer type);

    ResponseBean getAllInterfaceTree();

    ResponseBean getAllMenuTree(Integer type, Long userId);

    ResponseBean getAllFuns(Integer type, Long userId);

    void saveRoleMenu(Long roleId, List<Long> menuIds);
}