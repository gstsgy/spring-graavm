package com.gstsgy.webapi.controller;

import com.gstsgy.webapi.bean.db.Transactions;
import com.gstsgy.webapi.service.TransactionsService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "消费流水")
@RegisterReflectionForBinding({Transactions.class})
@RestController
@RequestMapping("transactions")
public class TransactionsController extends BaseController<TransactionsService, Transactions> {
}