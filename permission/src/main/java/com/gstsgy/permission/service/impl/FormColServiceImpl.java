package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.bean.db.FormCol;
import com.gstsgy.permission.repository.FormColRepository;
import com.gstsgy.permission.service.FormColService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class FormColServiceImpl extends BaseServiceImpl<FormCol, FormColRepository> implements FormColService {
}