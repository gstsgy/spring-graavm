package com.gstsgy.webapi.controller;

import com.gstsgy.webapi.bean.db.FamilyMembers;
import com.gstsgy.webapi.service.FamilyMembersService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "家庭成员")
@RegisterReflectionForBinding({FamilyMembers.class})
@RestController
@RequestMapping("family-members")
public class FamilyMembersController extends BaseController<FamilyMembersService, FamilyMembers> {
}