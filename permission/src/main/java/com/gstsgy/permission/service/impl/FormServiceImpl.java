package com.gstsgy.permission.service.impl;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.Btn;
import com.gstsgy.permission.bean.db.Form;
import com.gstsgy.permission.bean.db.FormBtn;
import com.gstsgy.permission.repository.*;
import com.gstsgy.permission.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
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
}