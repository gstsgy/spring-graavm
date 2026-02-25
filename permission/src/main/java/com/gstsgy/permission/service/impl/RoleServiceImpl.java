package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.bean.db.Role;
import com.gstsgy.permission.repository.RoleRepository;
import com.gstsgy.permission.service.RoleService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleRepository> implements RoleService {
}