package com.gstsgy.webapi.service;

import com.gstsgy.webapi.bean.db.AccountBook;
import com.gstsgy.base.service.BaseService;

import java.util.List;

public interface AccountBookService extends BaseService<AccountBook> {
    List<AccountBook> getAccountBookList();
}