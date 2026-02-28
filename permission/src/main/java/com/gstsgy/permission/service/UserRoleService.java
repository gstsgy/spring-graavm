package com.gstsgy.permission.service;

import com.gstsgy.permission.bean.db.UserRole;
import com.gstsgy.base.service.BaseService;

import java.util.List;

public interface UserRoleService extends BaseService<UserRole> {

    void saveByUser(Long userId, List<Long> roleIds);

    void saveByRole(Long roleId, List<Long> userIds);
}