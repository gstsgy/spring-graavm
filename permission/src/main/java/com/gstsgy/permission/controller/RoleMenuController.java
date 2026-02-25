package com.gstsgy.permission.controller;

import com.gstsgy.permission.bean.db.RoleMenu;
import com.gstsgy.permission.service.RoleMenuService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "角色菜单")
@RegisterReflectionForBinding({RoleMenu.class})
@RestController
@RequestMapping("role-menu")
public class RoleMenuController extends BaseController<RoleMenuService, RoleMenu> {
}