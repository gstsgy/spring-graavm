package com.gstsgy.webapi.controller;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.base.bean.enums.WebEnumVO;
import com.gstsgy.webapi.bean.db.AccountBook;
import com.gstsgy.webapi.service.AccountBookService;
import com.gstsgy.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "账本")
@RegisterReflectionForBinding({AccountBook.class})
@RestController
@RequestMapping("account_book")
public class AccountBookController extends BaseController<AccountBookService, AccountBook> {
    @Autowired
    private AccountBookService accountBookService;
    @Operation(description = "获取所有账本")
    @GetMapping("/all-self")
    public ResponseBean self(){
        return ResponseBean.getSuccess(accountBookService.getAccountBookList());
    }
    @Operation(description = "获取所有账本")
    @GetMapping("/all-self-enum")
    public ResponseBean selfEnum(){
        List<WebEnumVO> enums= accountBookService.getAccountBookList().stream().map(it->new WebEnumVO(it.getId(), it.getName())).toList();
        return ResponseBean.getSuccess(enums);
    }
}