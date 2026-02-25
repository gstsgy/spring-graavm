package com.gstsgy.webapi.service.impl;

import com.gstsgy.webapi.bean.db.Transactions;
import com.gstsgy.webapi.repository.TransactionsRepository;
import com.gstsgy.webapi.service.TransactionsService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

@Service
public class TransactionsServiceImpl extends BaseServiceImpl<Transactions, TransactionsRepository> implements TransactionsService {
}