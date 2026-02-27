package com.gstsgy.permission.service.impl;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.base.utils.DataSourceUtils;
import com.gstsgy.permission.bean.db.*;
import com.gstsgy.permission.repository.*;
import com.gstsgy.permission.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class FormServiceImpl extends BaseServiceImpl<Form, FormRepository> implements FormService {
    @Autowired
    private FormColRepository formColRepository;
    @Autowired
    private FormGridRepository formGridRepository;
    @Autowired
    private FormBtnRepository formBtnRepository;
    @Autowired
    private FormSingleRepository formSingleRepository;

    @Autowired
    private BtnRepository btnRepository;

    @Override
    public ResponseBean getCols(Long formId) {
        return ResponseBean.getSuccess(formColRepository.findFormColsByFormId(formId));
    }

    @Override
    public ResponseBean getGrids(Long formId) {
        return ResponseBean.getSuccess(formGridRepository.findFormGridsByFormId(formId));
    }

    @Override
    public ResponseBean getBtns(Long formId) {
        List<FormBtn> formBtns=formBtnRepository.findFormBtnsByFormId(formId);
        // 2. 提取所有 ID 并去重/过滤 null
        List<Long> btnIds = formBtns.stream()
                .map(FormBtn::getBtnId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (btnIds.isEmpty()) {
            return ResponseBean.getSuccess(Collections.emptyList());
        }

        // 3. 一次性查询所有按钮 (SQL: SELECT * FROM btn WHERE id IN (...))
        List<Btn> btns = btnRepository.findAllById(btnIds);

        return ResponseBean.getSuccess(btns);
    }

    @Override
    public ResponseBean getSingles(Long formId) {
        return ResponseBean.getSuccess(formSingleRepository.findFormSinglesByFormId(formId));
    }

    @CacheEvict(value = "col",key = "#formId")
    @Override
    public ResponseBean addFromCol(List<FormCol> formColDOList, Long formId) {
        if (formColDOList.isEmpty()) {
            formColRepository.deleteAllByFormId(formId);
            return ResponseBean.getSuccess(true);
        }
        List<FormCol> list = formColRepository.findFormColsByFormId(formColDOList.get(0).getFormId());
        List<Long> ids = formColDOList.stream().map(FormCol::getId).filter(Objects::nonNull).toList();
        List<Long> idsss = list.stream().map(FormCol::getId).filter(id -> !ids.contains(id)).toList();
        if (!idsss.isEmpty()) {
            formColRepository.deleteAllById(idsss);
        }
        formColRepository.saveAll(formColDOList);
        return ResponseBean.getSuccess(true);
    }

    @CacheEvict(value = "single",key = "#formId")
    @Override
    public ResponseBean addFromSingle(List<FormSingle> formColDOList, Long formId) {
        if (formColDOList.isEmpty()) {

            formSingleRepository.deleteAllByFormId(formId);
            return ResponseBean.getSuccess(true);
        }

        List<FormSingle> list = formSingleRepository.findFormSinglesByFormId(formColDOList.get(0).getFormId());
        List<Long> ids = formColDOList.stream().map(FormSingle::getId).filter(Objects::nonNull).toList();
        List<Long> idsss = list.stream().map(FormSingle::getId).filter(id -> !ids.contains(id)).toList();
        if (!idsss.isEmpty()) {
            formSingleRepository.deleteAllById(idsss);
        }

        formSingleRepository.saveAll(formColDOList);
        return ResponseBean.getSuccess(true);
    }
    @CacheEvict(value = "grid",key = "#formId")
    @Override
    public ResponseBean addFromGrid(List<FormGrid> formGridDOS,Long formId) {
        if (formGridDOS.isEmpty()) {

            formGridRepository.deleteAllByFormId(formId);
            return ResponseBean.getSuccess(true);
        }

        List<FormGrid> list = formGridRepository.findFormGridsByFormId(formGridDOS.get(0).getFormId());
        List<Long> ids = formGridDOS.stream().map(FormGrid::getId).filter(Objects::nonNull).toList();
        List<Long> idsss = list.stream().map(FormGrid::getId).filter(id -> !ids.contains(id)).toList();
        if (!idsss.isEmpty()) {
            formGridRepository.deleteAllById(idsss);
        }
        formGridRepository.saveAll(formGridDOS);

        return ResponseBean.getSuccess(true);
    }
    @CacheEvict(value = "btn",key = "#formId")
    @Override
    public ResponseBean addFromBtn(List<FormBtn> formBtnDOS,Long formId) {

        if (formBtnDOS.isEmpty()) {

            formBtnRepository.deleteAllByFormId(formId);
            return ResponseBean.getSuccess(true);
        }

        List<FormBtn> list = formBtnRepository.findFormBtnsByFormId(formId);
        List<Long> ids = formBtnDOS.stream().map(FormBtn::getId).filter(Objects::nonNull).toList();
        List<Long> idsss = list.stream().map(FormBtn::getId).filter(id -> !ids.contains(id)).toList();
        if (!idsss.isEmpty()) {
            formBtnRepository.deleteAllById(idsss);
        }
        formBtnRepository.saveAll(formBtnDOS);
        return ResponseBean.getSuccess(true);
        // QueryWrapper<FormBtnDO> deleteWrapper = new QueryWrapper<>();
        // deleteWrapper.lambda().eq(FormBtnDO::getFormId, formId);
        // formBtnMapper.delete(deleteWrapper);


        // formBtnDOS.forEach(item -> {
        //     formBtnMapper.insert(item);
        // });
        // return ResponseBean.getSuccess(true);
    }


    @Override
    public ResponseBean getAllTable() {
        // TODO Auto-generated method stub
        return ResponseBean.getSuccess(repository.selectTable(DataSourceUtils.getDataBaseName()));
    }

    @Override
    public ResponseBean getTableColumn(String tableName) {
        // TODO Auto-generated method stub
        return ResponseBean.getSuccess(repository.selectTableColumn(DataSourceUtils.getDataBaseName(),tableName));
    }
}