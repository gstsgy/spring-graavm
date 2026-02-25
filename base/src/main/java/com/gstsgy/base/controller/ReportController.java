package com.gstsgy.base.controller;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.base.service.UniversalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    private UniversalReportService universalReportService;

    @GetMapping("list/{fileName}/{methodName}")
    public ResponseBean getList(@PathVariable("fileName") String fileName, @PathVariable("methodName") String methodName, @RequestParam Map<String, Object> params){
        return ResponseBean.getSuccess(universalReportService.execute(fileName, methodName, params));
    }

    @GetMapping("page/{fileName}/{methodName}")
    public ResponseBean getPage(@PathVariable("fileName") String fileName, @PathVariable("methodName") String methodName, @RequestParam Map<String, Object> params){
        return ResponseBean.getSuccess(universalReportService.pageExecute(fileName, methodName, params));
    }
}
