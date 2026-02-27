package com.gstsgy.permission.repository;

import com.gstsgy.base.repository.BaseRepository;
import com.gstsgy.permission.bean.db.FormSingle;


import java.util.List;


public interface FormSingleRepository extends BaseRepository<FormSingle> {
    List<FormSingle> findFormSinglesByFormId(Long formId);


    void deleteAllByFormId(Long formId);

}