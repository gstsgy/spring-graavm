package com.gstsgy.base.bean.exception;

import com.gstsgy.base.bean.dto.LangCode;
import com.gstsgy.base.utils.LangUtils;
import org.springframework.http.HttpStatus;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2021/9/26 下午5:41
 */
public class ParamEmptyException extends APIException {

    public ParamEmptyException( String message) {
        super(HttpStatus.PRECONDITION_REQUIRED, LangUtils.getMessage("common.except428",message));
    }

    public ParamEmptyException( LangCode langCode) {
        super(HttpStatus.PRECONDITION_REQUIRED, LangUtils.getMessage("common.except428",LangUtils.getMessage(langCode.getLangCode())));
    }
}
