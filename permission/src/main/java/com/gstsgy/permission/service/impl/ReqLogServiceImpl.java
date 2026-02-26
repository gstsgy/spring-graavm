package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.bean.db.ReqLog;
import com.gstsgy.permission.repository.ReqLogRepository;
import com.gstsgy.permission.service.ReqLogService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class ReqLogServiceImpl extends BaseServiceImpl<ReqLog, ReqLogRepository> implements ReqLogService {
}