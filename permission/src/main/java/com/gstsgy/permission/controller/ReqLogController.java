package com.gstsgy.permission.controller;

import com.gstsgy.permission.bean.db.ReqLog;
import com.gstsgy.permission.service.ReqLogService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "请求日志")
@RegisterReflectionForBinding({ReqLog.class})
@RestController
@RequestMapping("req-log")
public class ReqLogController extends BaseController<ReqLogService, ReqLog> {
}