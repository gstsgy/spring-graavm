package com.gstsgy.permission.controller;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.Form;
import com.gstsgy.permission.service.FormService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "表单表")
@RegisterReflectionForBinding({Form.class})
@RestController
@RequestMapping("form")
public class FormController extends BaseController<FormService, Form> {

    @GetMapping("cols")
    public ResponseBean getCols(@RequestParam("formId") Long formId){
        return service.getCols(formId);
    }

    @GetMapping("grids")
    public ResponseBean getGrids(@RequestParam("formId") Long formId){
        return service.getGrids(formId);
    }

    @GetMapping("btns")
    public ResponseBean getBtns(@RequestParam("formId") Long formId){
        return service.getBtns(formId);
    }

    @GetMapping("singles")
    public ResponseBean getSingles(@RequestParam("formId") Long formId){
        return service.getSingles(formId);
    }
}