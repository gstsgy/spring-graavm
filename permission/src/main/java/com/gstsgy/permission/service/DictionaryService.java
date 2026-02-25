package com.gstsgy.permission.service;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.Dictionary;
import com.gstsgy.base.service.BaseService;
import com.gstsgy.permission.bean.view.WebEnumVO;

import java.util.List;
import java.util.Map;

public interface DictionaryService extends BaseService<Dictionary> {
    ResponseBean queryDictEnum(String modelCode);

    ResponseBean queryDictMap(String modelCode);

    Map queryDictMapSelf(String modelCode);

    //List<WebEnumVO> queryDictEnumSelf(Long parentId);
}