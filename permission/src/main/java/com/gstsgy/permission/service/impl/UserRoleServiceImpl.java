package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.bean.db.UserRole;
import com.gstsgy.permission.repository.UserRoleRepository;
import com.gstsgy.permission.service.UserRoleService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, UserRoleRepository> implements UserRoleService {
}