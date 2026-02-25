package com.gstsgy.permission.repository;

import com.gstsgy.base.repository.BaseRepository;
import com.gstsgy.permission.bean.db.FormBtn;

import java.util.List;

public interface FormBtnRepository extends BaseRepository<FormBtn> {
    List<FormBtn> findFormBtnsByFormId(Long formId);
}