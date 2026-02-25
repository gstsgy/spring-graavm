package com.gstsgy.permission.service;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.Form;
import com.gstsgy.base.service.BaseService;

public interface FormService extends BaseService<Form> {

    ResponseBean getCols( Long formId);

    ResponseBean getGrids( Long formId);

    ResponseBean getBtns( Long formId);

    ResponseBean getSingles( Long formId);
}