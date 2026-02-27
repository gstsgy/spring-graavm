package com.gstsgy.permission.service;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.*;
import com.gstsgy.base.service.BaseService;

import java.util.List;

public interface FormService extends BaseService<Form> {

    ResponseBean getCols( Long formId);

    ResponseBean getGrids( Long formId);

    ResponseBean getBtns( Long formId);

    ResponseBean getSingles( Long formId);

    ResponseBean addFromCol(List<FormCol> formColDOList, Long formId);

    ResponseBean addFromSingle(List<FormSingle> formColDOList, Long formId);

    ResponseBean addFromGrid(List<FormGrid> formGridDOS, Long formId);

    ResponseBean addFromBtn(List<FormBtn> formBtnDOS, Long formId);

    ResponseBean getAllTable();

    ResponseBean getTableColumn(String tableName);
}