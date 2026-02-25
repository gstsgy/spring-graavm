package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.bean.db.FormBtn;
import com.gstsgy.permission.repository.FormBtnRepository;
import com.gstsgy.permission.service.FormBtnService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class FormBtnServiceImpl extends BaseServiceImpl<FormBtn, FormBtnRepository> implements FormBtnService {
}