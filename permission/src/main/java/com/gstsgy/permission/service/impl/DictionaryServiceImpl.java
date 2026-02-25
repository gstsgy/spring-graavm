package com.gstsgy.permission.service.impl;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.Dictionary;
import com.gstsgy.permission.bean.view.WebEnumVO;
import com.gstsgy.permission.repository.DictionaryRepository;
import com.gstsgy.permission.service.DictionaryService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary, DictionaryRepository> implements DictionaryService {
    private static String MAGIC_NUMBER="9527";
    @Override
    public ResponseBean queryDictEnum(String modelCode) {
        if(modelCode==null||"".equals(modelCode)){
            modelCode = MAGIC_NUMBER;
        }

        List<Dictionary> list = this.repository.findDictionariesByModelCodeOrderBySeq(modelCode);

        List<WebEnumVO> resultData = list.stream().map(it->new WebEnumVO(it.getDictKey().matches("-?\\d+")&&!"9527".equals(it.getDictKey())&&it.getDictKey().length()<8?
                Integer.parseInt(it.getDictKey()):it.getDictKey(),it.getDictValue())).collect(Collectors.toList());

        return ResponseBean.getSuccess(resultData);
    }
    @Override
    public  ResponseBean  queryDictMap(String modelCode) {

        return ResponseBean.getSuccess(queryDictMapSelf(modelCode));
    }

    @Override
    public Map queryDictMapSelf(String modelCode) {
        if(modelCode==null||"".equals(modelCode)){
            modelCode = MAGIC_NUMBER;
        }
        List<Dictionary> list = this.repository.findDictionariesByModelCodeOrderBySeq(modelCode);

        return list.stream().collect(Collectors.toMap(it-> it.getDictKey().matches("-?\\d*")&&!"9527".equals(it.getDictKey())&&it.getDictKey().length()<8?
                Integer.parseInt(it.getDictKey()):it.getDictKey(), Dictionary::getDictValue));
    }



//    @Override
//    public List<WebEnumVO> queryDictEnumSelf(Long parentId) {
//        QueryWrapper<DictionaryDO> queryWrapper = new QueryWrapper<>();
//        if(parentId!=null){
//            queryWrapper.lambda().eq(DictionaryDO::getParentId,parentId);
//        }else {
//            queryWrapper.lambda().isNull(DictionaryDO::getParentId);
//        }
//        List<DictionaryDO> list = dictionaryMapper.selectList(queryWrapper);
//        if(list==null){
//            return null;
//        }
//
//        return list.stream().map(it->{
//            var webvo=new WebEnumVO(it.getDictKey().matches("-?\\d+")&&!"9527".equals(it.getDictKey())&&it.getDictKey().length()<8?
//                    Integer.parseInt(it.getDictKey()):it.getDictKey(),it.getDictValue());
//            webvo.setId(it.getId());
//            webvo.setModel(it.getModelCode());
//            webvo.setChildren(queryDictEnumSelf(it.getId()));
//            return webvo;
//
//        }).collect(Collectors.toList());
//    }
}