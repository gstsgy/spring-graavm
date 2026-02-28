package com.gstsgy.permission.controller;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.UserRole;
import com.gstsgy.permission.service.UserRoleService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户角色")
@RegisterReflectionForBinding({UserRole.class})
@RestController
@RequestMapping("user-role")
public class UserRoleController extends BaseController<UserRoleService, UserRole> {

    @PostMapping("user")
    public ResponseBean saveByUser(@RequestParam("userId") Long userId , @RequestBody List<Long> roleIds) {
        service.saveByUser(userId,roleIds);
        return  ResponseBean.getSuccess(true);
    }

    @PostMapping("role")
    public ResponseBean saveByRole(@RequestParam("roleId") Long roleId , @RequestBody List<Long> userIds) {
        service.saveByRole(roleId,userIds);
        return  ResponseBean.getSuccess(true);
    }
}