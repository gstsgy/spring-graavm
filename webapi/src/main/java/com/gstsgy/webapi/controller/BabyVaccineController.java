package com.gstsgy.webapi.controller;

import com.gstsgy.webapi.bean.db.BabyVaccine;
import com.gstsgy.webapi.service.BabyVaccineService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "baby疫苗")
@RegisterReflectionForBinding({BabyVaccine.class})
@RestController
@RequestMapping("baby-vaccine")
public class BabyVaccineController extends BaseController<BabyVaccineService, BabyVaccine> {
}