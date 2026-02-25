package com.gstsgy.webapi.service.impl;

import com.gstsgy.webapi.bean.db.BabyBase;
import com.gstsgy.webapi.repository.BabyBaseRepository;
import com.gstsgy.webapi.service.BabyBaseService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class BabyBaseServiceImpl extends BaseServiceImpl<BabyBase, BabyBaseRepository> implements BabyBaseService {
}