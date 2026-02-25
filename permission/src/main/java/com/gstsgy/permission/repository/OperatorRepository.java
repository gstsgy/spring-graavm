package com.gstsgy.permission.repository;

import com.gstsgy.base.repository.BaseRepository;
import com.gstsgy.permission.bean.db.Operator;

public interface OperatorRepository extends BaseRepository<Operator> {
    Operator findOperatorByCode(String code);

    Operator findOperatorByEmail(String code);

    Operator findOperatorByWxId(String wxId);
}