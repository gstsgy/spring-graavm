package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.bean.db.UserRole;
import com.gstsgy.permission.repository.UserRoleRepository;
import com.gstsgy.permission.service.UserRoleService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, UserRoleRepository> implements UserRoleService {
    @Transactional
    @Override
    public void saveByUser(Long userId, List<Long> roleIds) {
        // 删除该用户所有角色
        // 重新新增呢
        repository.deleteAllByUserId(userId);
        List<UserRole> userRoles= roleIds.stream().map(it->{
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(it);
            return userRole;
        }).toList();
        repository.saveAll(userRoles);
    }
    @Transactional
    @Override
    public void saveByRole(Long roleId, List<Long> userIds) {
        repository.deleteAllByRoleId(roleId);
        List<UserRole> userRoles= userIds.stream().map(it->{
            UserRole userRole = new UserRole();
            userRole.setUserId(it);
            userRole.setRoleId(roleId);
            return userRole;
        }).toList();
        repository.saveAll(userRoles);
    }
}