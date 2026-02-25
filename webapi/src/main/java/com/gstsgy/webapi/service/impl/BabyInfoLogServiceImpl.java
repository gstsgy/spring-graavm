package com.gstsgy.webapi.service.impl;

import com.gstsgy.webapi.bean.db.BabyInfoLog;
import com.gstsgy.webapi.repository.BabyInfoLogRepository;
import com.gstsgy.webapi.service.BabyInfoLogService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class BabyInfoLogServiceImpl extends BaseServiceImpl<BabyInfoLog, BabyInfoLogRepository> implements BabyInfoLogService {
}