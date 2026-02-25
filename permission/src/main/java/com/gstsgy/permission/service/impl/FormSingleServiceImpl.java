package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.bean.db.FormSingle;
import com.gstsgy.permission.repository.FormSingleRepository;
import com.gstsgy.permission.service.FormSingleService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class FormSingleServiceImpl extends BaseServiceImpl<FormSingle, FormSingleRepository> implements FormSingleService {
}