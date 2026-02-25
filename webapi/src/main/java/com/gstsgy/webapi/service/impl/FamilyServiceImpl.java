package com.gstsgy.webapi.service.impl;

import com.gstsgy.webapi.bean.db.Family;
import com.gstsgy.webapi.repository.FamilyRepository;
import com.gstsgy.webapi.service.FamilyService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class FamilyServiceImpl extends BaseServiceImpl<Family, FamilyRepository> implements FamilyService {
}