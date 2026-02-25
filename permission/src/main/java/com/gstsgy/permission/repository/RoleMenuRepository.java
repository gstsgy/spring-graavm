package com.gstsgy.permission.repository;

import com.gstsgy.base.repository.BaseRepository;
import com.gstsgy.permission.bean.db.RoleMenu;
import java.util.List;

public interface RoleMenuRepository extends BaseRepository<RoleMenu> {

    List<RoleMenu> findByRoleId(Long roleId);

    List<RoleMenu> findByRoleIdIn(List<Long> roleIds);
}