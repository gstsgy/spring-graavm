package com.gstsgy.permission.controller;

import com.gstsgy.permission.bean.db.Role;
import com.gstsgy.permission.service.RoleService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "角色表")
@RegisterReflectionForBinding({Role.class})
@RestController
@RequestMapping("role")
public class RoleController extends BaseController<RoleService, Role> {
}