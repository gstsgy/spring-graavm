package com.gstsgy.permission.controller;

import com.gstsgy.permission.bean.db.FormCol;
import com.gstsgy.permission.service.FormColService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "表单列表")
@RegisterReflectionForBinding({FormCol.class})
@RestController
@RequestMapping("form-col")
public class FormColController extends BaseController<FormColService, FormCol> {
}