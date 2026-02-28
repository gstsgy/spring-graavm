package com.gstsgy.permission.controller;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.RoleMenu;
import com.gstsgy.permission.service.RoleMenuService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色菜单")
@RegisterReflectionForBinding({RoleMenu.class})
@RestController
@RequestMapping("role-menu")
public class RoleMenuController extends BaseController<RoleMenuService, RoleMenu> {

    @GetMapping("menu-tree")
    @Operation(description ="获取所有菜单树")
    public ResponseBean queryMenuTree(@RequestParam("type") Integer type) {
        return service.getAllMenuTree(type);
    }

    @PostMapping("items")
    @Operation(description ="保存权限")
    public ResponseBean save(@RequestParam("roleId") Long roleId, @RequestBody List<Long> menuIds) {
        service.saveRoleMenu(roleId,menuIds);
        return ResponseBean.getSuccess(true);
    }

    @GetMapping("interface-tree")
    @Operation(description ="获取所有接口树")
    public ResponseBean queryInterfaceTree() {
        return service.getAllInterfaceTree();
    }
}