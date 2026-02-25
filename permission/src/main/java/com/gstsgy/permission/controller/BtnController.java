package com.gstsgy.permission.controller;

import com.gstsgy.permission.bean.db.Btn;
import com.gstsgy.permission.service.BtnService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "按钮表")
@RegisterReflectionForBinding({Btn.class})
@RestController
@RequestMapping("btn")
public class BtnController extends BaseController<BtnService, Btn> {
}