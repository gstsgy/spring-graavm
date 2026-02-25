package com.gstsgy.webapi.controller;

import com.gstsgy.webapi.bean.db.BabyBase;
import com.gstsgy.webapi.service.BabyBaseService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "baby基础信息")
@RegisterReflectionForBinding({BabyBase.class})
@RestController
@RequestMapping("baby-base")
public class BabyBaseController extends BaseController<BabyBaseService, BabyBase> {
}