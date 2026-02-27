package com.gstsgy.permission.controller;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.Menu;
import com.gstsgy.permission.bean.view.MenuVO;
import com.gstsgy.permission.service.MenuService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "菜单")
@RegisterReflectionForBinding({Menu.class, MenuVO.class})
@RestController
@RequestMapping("menu")
public class MenuController extends BaseController<MenuService, Menu> {
    @Operation(description ="获取菜单树")
    @GetMapping("tree")
    public ResponseBean GetMenuTree(@RequestParam(value = "type",required = false)  Integer type) {
        return service.GetMenuTree(type);
    }
}