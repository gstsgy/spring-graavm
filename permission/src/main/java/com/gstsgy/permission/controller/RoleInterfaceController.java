package com.gstsgy.permission.controller;

import com.gstsgy.permission.bean.db.RoleInterface;
import com.gstsgy.permission.service.RoleInterfaceService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "角色菜单")
@RegisterReflectionForBinding({RoleInterface.class})
@RestController
@RequestMapping("role-interface")
public class RoleInterfaceController extends BaseController<RoleInterfaceService, RoleInterface> {
}