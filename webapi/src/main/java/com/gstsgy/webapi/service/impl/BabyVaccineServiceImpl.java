package com.gstsgy.webapi.service.impl;

import com.gstsgy.webapi.bean.db.BabyVaccine;
import com.gstsgy.webapi.repository.BabyVaccineRepository;
import com.gstsgy.webapi.service.BabyVaccineService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class BabyVaccineServiceImpl extends BaseServiceImpl<BabyVaccine, BabyVaccineRepository> implements BabyVaccineService {
}