package com.gstsgy.permission.repository;

import com.gstsgy.base.repository.BaseRepository;
import com.gstsgy.permission.bean.db.FormCol;

import java.util.List;

public interface FormColRepository extends BaseRepository<FormCol> {

    List<FormCol> findFormColsByFormId(Long formId);
}