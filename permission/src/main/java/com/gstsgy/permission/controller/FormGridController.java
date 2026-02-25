package com.gstsgy.permission.controller;

import com.gstsgy.permission.bean.db.FormGrid;
import com.gstsgy.permission.service.FormGridService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "表单列表")
@RegisterReflectionForBinding({FormGrid.class})
@RestController
@RequestMapping("form-grid")
public class FormGridController extends BaseController<FormGridService, FormGrid> {
}