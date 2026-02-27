package com.gstsgy.permission.controller;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.*;
import com.gstsgy.permission.service.FormService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(description ="form")
    @PostMapping("cols")
    public  ResponseBean addFromCol(@RequestBody List<FormCol> formColDOList , @RequestParam("formId")Long formId) {
        return service.addFromCol(formColDOList,formId);
    }

    @Operation(description ="form")
    @PostMapping("single")
    public  ResponseBean addFromSingle(@RequestBody List<FormSingle> formColDOList ,@RequestParam("formId") Long formId) {
        return service.addFromSingle(formColDOList,formId);
    }

    @Operation(description ="form")
    @PostMapping("grids")
    public  ResponseBean addFromGrid(@RequestBody List<FormGrid> formGridDOS,@RequestParam("formId") Long formId) {
        return service.addFromGrid(formGridDOS,formId);
    }

    @Operation(description ="添加按钮")
    @PostMapping("btns")
    public  ResponseBean addFromBtn(@RequestBody List<FormBtn> formBtnDOS,@RequestParam("formId") Long formId) {
        return service.addFromBtn(formBtnDOS,formId);
    }


    @Operation(description ="获取表")
    @GetMapping("tables")
    public  ResponseBean getTables() {
        //List<String> tableNames =  TableInfoHelper.getTableInfos().stream().map(it->it.getTableName()).toList();
        return service.getAllTable();
    }

    @Operation(description ="获取表字段")
    @GetMapping("table-columns")
    public  ResponseBean getTableColumns(@RequestParam("tableName") String tableName) {
        //List<String> tableNames =  TableInfoHelper.getTableInfos().stream().map(it->it.getTableName()).toList();
        return service.getTableColumn(tableName);
    }
}