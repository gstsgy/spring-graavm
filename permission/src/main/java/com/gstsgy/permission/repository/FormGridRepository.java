package com.gstsgy.permission.repository;

import com.gstsgy.base.repository.BaseRepository;
import com.gstsgy.permission.bean.db.FormGrid;

import java.util.List;

public interface FormGridRepository extends BaseRepository<FormGrid> {

    List<FormGrid> findFormGridsByFormId(Long formId);
}