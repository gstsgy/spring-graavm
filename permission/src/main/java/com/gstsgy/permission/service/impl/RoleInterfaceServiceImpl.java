package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.bean.db.RoleInterface;
import com.gstsgy.permission.repository.RoleInterfaceRepository;
import com.gstsgy.permission.service.RoleInterfaceService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class RoleInterfaceServiceImpl extends BaseServiceImpl<RoleInterface, RoleInterfaceRepository> implements RoleInterfaceService {
}