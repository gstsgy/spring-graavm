package com.gstsgy.webapi.controller;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.base.utils.WebUtils;
import com.gstsgy.permission.bean.db.Operator;
import com.gstsgy.permission.repository.OperatorRepository;
import com.gstsgy.permission.service.OperatorService;
import com.gstsgy.webapi.bean.db.Family;
import com.gstsgy.webapi.bean.db.FamilyMembers;
import com.gstsgy.webapi.repository.FamilyMembersRepository;
import com.gstsgy.webapi.service.FamilyService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "家庭")
@RegisterReflectionForBinding({Family.class})
@RestController
@RequestMapping("family")
public class FamilyController extends BaseController<FamilyService, Family> {

    @Autowired
    private OperatorRepository userService;
    @Autowired
    private FamilyMembersRepository familyMembersRepository;

    @Operation(description = "获取家庭id")
    @GetMapping("/self")
    public ResponseBean self(){

        FamilyMembers familyMembersDO = familyMembersRepository.findAllByUserId(WebUtils.getUserId());
        if(familyMembersDO == null){
            return ResponseBean.getError("请先加入家庭");
        }

        return ResponseBean.getSuccess(familyMembersDO.getFamilyId());
    }

    @Operation(description = "获取家庭成员")
    @GetMapping("/members")
    public ResponseBean members(@RequestParam("familyId") Long familyId){


        List<FamilyMembers> familyMembersDOs = familyMembersRepository.findAllByFamilyId(familyId);
        familyMembersDOs.forEach(it->{
            Operator user= userService.findById(it.getUserId()).orElse(null);
            it.setRemark1(user.getCode());
            it.setRemark2(user.getNickName());
        });
        //List<FamilyMembersDO> users=familyMembersDOs.stream().map(it->userService.queryOne(it.getUserId())).toList();

        return ResponseBean.getSuccess(familyMembersDOs);
    }
}