package com.gstsgy.permission.controller;

import com.gstsgy.permission.bean.db.FormSingle;
import com.gstsgy.permission.service.FormSingleService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "单表列表")
@RegisterReflectionForBinding({FormSingle.class})
@RestController
@RequestMapping("form-single")
public class FormSingleController extends BaseController<FormSingleService, FormSingle> {
}