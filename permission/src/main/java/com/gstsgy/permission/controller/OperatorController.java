package com.gstsgy.permission.controller;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.Operator;
import com.gstsgy.permission.service.OperatorService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.*;

@Tag(name = "操作人")
@RegisterReflectionForBinding({Operator.class})
@RestController
@RequestMapping("operator")
public class OperatorController extends BaseController<OperatorService, Operator> {

    @GetMapping("self")
    public ResponseBean self(){
        return ResponseBean.getSuccess(service.self());
    }

    @GetMapping("qr")
    public ResponseBean qr(){
        return service.getQr();
    }

    @PutMapping("qr")
    public ResponseBean bindQr(@RequestParam("secret")String secret){
        return ResponseBean.getSuccess(service.bindQr(secret));
    }

    @Operation(description ="修改密码")
    @PutMapping("/userpw")
    public ResponseBean updateUserpw(@RequestBody Operator user,@Parameter(description ="加到请求头里") @RequestHeader("oldPwd") String oldPwd) throws Exception {
        return service.updateUserpw(user,oldPwd);
    }

}