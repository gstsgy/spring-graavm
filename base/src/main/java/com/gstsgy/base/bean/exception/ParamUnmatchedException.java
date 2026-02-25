package com.gstsgy.base.bean.exception;

import com.gstsgy.base.utils.LangUtils;
import org.springframework.http.HttpStatus;
import com.gstsgy.base.bean.dto.LangCode;
/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2021/10/26 上午10:23
 */
public class ParamUnmatchedException extends APIException{
    public ParamUnmatchedException( String message) {
        super(HttpStatus.PRECONDITION_FAILED, LangUtils.getMessage("common.except412",message));
    }
    public ParamUnmatchedException( String message,Object...args) {
        super(HttpStatus.PRECONDITION_FAILED, LangUtils.getMessage("common.except412",String.format(message,args)));
    }

    public ParamUnmatchedException(LangCode langCode ) {
        super(HttpStatus.PRECONDITION_FAILED, LangUtils.getMessage("common.except412",LangUtils.getMessage(langCode.getLangCode())));
    }
    public ParamUnmatchedException( LangCode langCode,LangCode...args) {
        super(HttpStatus.PRECONDITION_FAILED, LangUtils.getMessage("common.except412",LangUtils.getMessageByLangCode(langCode,args)));
    }
    public ParamUnmatchedException( LangCode langCode,Object...args) {
        super(HttpStatus.PRECONDITION_FAILED, LangUtils.getMessage("common.except412",LangUtils.getMessage(langCode.getLangCode(),args)));
    }
}
