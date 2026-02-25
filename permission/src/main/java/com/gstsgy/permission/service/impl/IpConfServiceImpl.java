package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.bean.db.IpConf;
import com.gstsgy.permission.repository.IpConfRepository;
import com.gstsgy.permission.service.IpConfService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class IpConfServiceImpl extends BaseServiceImpl<IpConf, IpConfRepository> implements IpConfService {
}