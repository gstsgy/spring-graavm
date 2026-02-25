package com.gstsgy.permission.controller;

import com.gstsgy.permission.bean.db.FormBtn;
import com.gstsgy.permission.service.FormBtnService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "按钮表")
@RegisterReflectionForBinding({FormBtn.class})
@RestController
@RequestMapping("form-btn")
public class FormBtnController extends BaseController<FormBtnService, FormBtn> {
}