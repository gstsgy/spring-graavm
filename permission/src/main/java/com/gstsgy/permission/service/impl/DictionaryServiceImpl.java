package com.gstsgy.permission.service.impl;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.base.bean.dto.WebEnumVO;
import com.gstsgy.base.bean.exception.ServerException;
import com.gstsgy.permission.bean.db.Dictionary;
import com.gstsgy.permission.repository.DictionaryRepository;
import com.gstsgy.permission.service.DictionaryService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary, DictionaryRepository> implements DictionaryService {
    private static String MAGIC_NUMBER="9527";

    @Transactional
    public Dictionary saveOne(Dictionary obj) {
        if(obj.getId()==null) {
            return super.saveOne(obj);
        }
        Optional<Dictionary> obj1 = repository.findById(obj.getId());
        if(obj1.isEmpty()) {
            throw new ServerException("数据不存在");
        }
        Dictionary dictionary = obj1.get();
        if(!dictionary.getDictKey().equals(obj.getDictKey())) {
            // 更新子的model
            repository.updateModelKeyByParentId(obj.getDictKey(),dictionary.getId());
        }
        dictionary.setDictKey(obj.getDictKey());
        dictionary.setDictValue(obj.getDictValue());

        return super.saveOne(dictionary);
    }

    @Transactional
    public void deleteById(Long id) {
        // 2. 递归查找所有子孙节点的 ID
        List<Long> allIdsToDelete = new ArrayList<>();
        findAllChildIds(id, allIdsToDelete);

        // 3. 将当前节点 ID 也加入待删除列表
        allIdsToDelete.add(id);

        // 4. 批量删除（性能优于循环调用 deleteById）
        repository.deleteAllByIdInBatch(allIdsToDelete);
    }

    /**
     * 递归获取所有子节点 ID
     */
    private void findAllChildIds(Long parentId, List<Long> idList) {
        List<Dictionary> children = repository.findAllByParentId(parentId);
        for (Dictionary child : children) {
            idList.add(child.getId());
            // 递归进入下一层
            findAllChildIds(child.getId(), idList);
        }
    }



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



    public List<WebEnumVO> queryDictEnumSelf(Long parentId) {
        // 1. 一次性获取所有数据（避免 N+1）
        List<Dictionary> allDicts = repository.findAll();
        if (allDicts.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. 将所有数据转换为 VO，并按 parentId 分组缓存
        Map<Long, List<WebEnumVO>> childrenMap = allDicts.stream()
                .map(this::convertToVO)
                .collect(Collectors.groupingBy(
                        it -> it.getRawParentId() != null ? it.getRawParentId() : -1L
                ));

        // 3. 从指定的 parentId 开始构建树
        return buildTree(parentId == null ? -1L : parentId, childrenMap);
    }

    // 辅助方法：递归组装
    private List<WebEnumVO> buildTree(Long pid, Map<Long, List<WebEnumVO>> childrenMap) {
        List<WebEnumVO> children = childrenMap.getOrDefault(pid, Collections.emptyList());
        children.forEach(child -> {
            child.setChildren(buildTree(child.getId(), childrenMap));
        });
        return children;
    }

    // 辅助方法：转换逻辑
    private WebEnumVO convertToVO(Dictionary it) {
        Object dictKey = (it.getDictKey().matches("-?\\d+") &&
                !"9527".equals(it.getDictKey()) &&
                it.getDictKey().length() < 8)
                ? Integer.parseInt(it.getDictKey()) : it.getDictKey();

        WebEnumVO vo = new WebEnumVO(dictKey, it.getDictValue());
        vo.setId(it.getId());
        vo.setModel(it.getModelCode());
        vo.setRawParentId(it.getParentId()); // 需要在 VO 中暂存一个 parentId 字段用于分组
        return vo;
    }

}