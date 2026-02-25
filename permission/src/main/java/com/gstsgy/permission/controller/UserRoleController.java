package com.gstsgy.permission.controller;

import com.gstsgy.permission.bean.db.UserRole;
import com.gstsgy.permission.service.UserRoleService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户角色")
@RegisterReflectionForBinding({UserRole.class})
@RestController
@RequestMapping("user-role")
public class UserRoleController extends BaseController<UserRoleService, UserRole> {
}