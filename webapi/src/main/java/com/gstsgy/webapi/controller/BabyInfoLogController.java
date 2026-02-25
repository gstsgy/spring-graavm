package com.gstsgy.webapi.controller;

import com.gstsgy.webapi.bean.db.BabyInfoLog;
import com.gstsgy.webapi.service.BabyInfoLogService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "baby基础信息log")
@RegisterReflectionForBinding({BabyInfoLog.class})
@RestController
@RequestMapping("baby-info-log")
public class BabyInfoLogController extends BaseController<BabyInfoLogService, BabyInfoLog> {
}