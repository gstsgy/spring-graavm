package com.gstsgy.webapi.service.impl;

import com.gstsgy.webapi.bean.db.FamilyMembers;
import com.gstsgy.webapi.repository.FamilyMembersRepository;
import com.gstsgy.webapi.service.FamilyMembersService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class FamilyMembersServiceImpl extends BaseServiceImpl<FamilyMembers, FamilyMembersRepository> implements FamilyMembersService {
}