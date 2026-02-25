package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.bean.db.Btn;
import com.gstsgy.permission.repository.BtnRepository;
import com.gstsgy.permission.service.BtnService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class BtnServiceImpl extends BaseServiceImpl<Btn, BtnRepository> implements BtnService {
}