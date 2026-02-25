package com.gstsgy.webapi.repository;

import com.gstsgy.webapi.bean.db.AccountBook;
import com.gstsgy.base.repository.BaseRepository;

import java.util.List;

public interface AccountBookRepository extends BaseRepository<AccountBook> {
    List<AccountBook> findAllByFamilyId(Long familyId);
}