package com.gstsgy.permission.controller;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.Dictionary;
import com.gstsgy.permission.bean.view.WebEnumVO;
import com.gstsgy.permission.service.DictionaryService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "数据字典")
@RegisterReflectionForBinding({Dictionary.class, WebEnumVO.class})
@RestController
@RequestMapping("dictionary")
public class DictionaryController extends BaseController<DictionaryService, Dictionary> {

    @Operation(description="查询字典枚举")
    @GetMapping("dictsenum")
    public ResponseBean queryDictEnum( @RequestParam("modelCode") String modelCode) {
        return service.queryDictEnum( modelCode);
    }

    @Operation(description="查询字典Map")
    @GetMapping("dictsmap")
    public ResponseBean queryDictMap(@RequestParam("modelCode")String modelCode) {
        return service.queryDictMap( modelCode);
    }
}