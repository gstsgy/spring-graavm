package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.bean.db.FormGrid;
import com.gstsgy.permission.repository.FormGridRepository;
import com.gstsgy.permission.service.FormGridService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class FormGridServiceImpl extends BaseServiceImpl<FormGrid, FormGridRepository> implements FormGridService {
}