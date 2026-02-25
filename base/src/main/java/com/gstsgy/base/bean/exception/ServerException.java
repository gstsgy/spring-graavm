package com.gstsgy.base.bean.exception;

import com.gstsgy.base.bean.dto.LangCode;
import com.gstsgy.base.utils.LangUtils;
import org.springframework.http.HttpStatus;
/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2021/10/26 上午10:29
 */
public class ServerException extends APIException{
    public ServerException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, LangUtils.getMessage("common.except500",message));
    }

    public ServerException(LangCode langCode) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, LangUtils.getMessage("common.except500",LangUtils.getMessage(langCode.getLangCode())));
    }
}
