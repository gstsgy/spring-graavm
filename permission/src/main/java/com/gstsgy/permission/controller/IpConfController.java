package com.gstsgy.permission.controller;

import com.gstsgy.permission.bean.db.IpConf;
import com.gstsgy.permission.service.IpConfService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ip")
@RegisterReflectionForBinding({IpConf.class})
@RestController
@RequestMapping("ip-conf")
public class IpConfController extends BaseController<IpConfService, IpConf> {
}