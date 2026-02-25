package com.gstsgy.permission.service;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.Operator;
import com.gstsgy.base.service.BaseService;

public interface OperatorService extends BaseService<Operator> {
    Operator queryOneByCode(String code);

    Operator queryOneByEmail(String email);

    Operator queryOneByWxId(String wxId);

    String encrypt(Operator user);

    String wxLogin(String appId,String secret,String jsCode);

    boolean bindWxId(String openId, Operator operator);

    ResponseBean getQr();

    boolean bindQr(String secret);

    Operator updatePassWd(Operator user, String oldPwd);

    Operator restPassWd(Operator user);

    Operator self();

    ResponseBean updateUserpw(Operator user, String oldPwd) throws Exception;
}