package com.gstsgy.permission.service.impl;

import com.gstsgy.base.bean.dto.LangCode;
import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.base.bean.exception.ParamUnmatchedException;
import com.gstsgy.base.utils.DateUtil;
import com.gstsgy.base.utils.Encrypt;
import com.gstsgy.base.utils.JSON;
import com.gstsgy.base.utils.WebUtils;
import com.gstsgy.permission.bean.db.Operator;
import com.gstsgy.permission.repository.OperatorRepository;
import com.gstsgy.permission.service.OperatorService;
import com.gstsgy.permission.utils.GoogleAuthenticatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class OperatorServiceImpl extends BaseServiceImpl<Operator, OperatorRepository> implements OperatorService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Operator saveOne(Operator obj) {
        obj.setPasswdUpdateYmd(DateUtil.getDate());
        obj.setPasswd("admin123.");
        obj.setPasswd(encrypt(obj));
        return super.saveOne(obj);
    }


    @Override
    public Operator queryOneByCode(String code) {
        return repository.findOperatorByCode(code);
    }

    @Override
    public Operator queryOneByEmail(String email) {
        return repository.findOperatorByEmail(email);
    }

    @Override
    public Operator queryOneByWxId(String wxId) {
        return repository.findOperatorByWxId(wxId);
    }

    @Override
    public String encrypt(Operator user) {
        return Encrypt.encryptToMD5(user.getCode() + user.getPasswd());
    }

    @Override
    public String wxLogin(String appId, String secret, String jsCode) {
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", appId, secret, jsCode);
        // 发起 GET 请求
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        System.out.println(JSON.toJson(response.getBody()));
        System.out.println(Objects.requireNonNull(response.getBody()).get("openid").toString());
        // 返回结果
        // return Objects.requireNonNull(response.getBody()).toString();
        return Objects.requireNonNull(response.getBody()).get("openid").toString();
    }

    @Override
    public boolean bindWxId(String openId, Operator operator) {
        operator.setWxId(openId);
         repository.save(operator);
         return true;
    }

    @Override
    public ResponseBean getQr() {
        String searet = GoogleAuthenticatorUtils.createSecret();

        String base64 = null;
        base64 = GoogleAuthenticatorUtils.getOtpAuthUrl(searet, WebUtils.getUserId()+"");
        return ResponseBean.getSuccess(base64);
    }
    @Override
    public boolean bindQr(String secret) {
        Optional<Operator> operatorDO = repository.findById(WebUtils.getUserId());
        if(operatorDO.isEmpty() || StringUtils.hasText(operatorDO.get().getTotpsecret())){
            return false;
        }
        String key = "N0b/5bEeZtZgZCvNB/F0LxqHlpNlCyDmHcXbv0YJgVI=";
        Base64.Encoder encoder = Base64.getEncoder();
        String encode = encoder.encodeToString(Encrypt.encryptToAES(secret,key));
        operatorDO.get().setTotpsecret(encode);
        repository.save(operatorDO.get());
        return true;
    }

    @Override
    public Operator updatePassWd(Operator user, String oldPwd) {
        return null;
    }

    @Override
    public Operator restPassWd(Operator user) {
        Optional<Operator> tmp = repository.findById(user.getId());
        if(tmp.isEmpty()){
            throw  new ParamUnmatchedException("用户不存在");
        }
        Operator operator = tmp.get();
        if (operator.getId().equals(1L)) {
            throw  new ParamUnmatchedException("管理员密不允许修改");
        }

        operator.setPasswdUpdateYmd(DateUtil.getDate());
        operator.setPasswd("admin123.");
        operator.setPasswd(encrypt(operator));
        repository.save(operator);
        return operator;
    }

    @Override
    public Operator self() {
        Optional<Operator> operator= repository.findById(WebUtils.getUserId());
        if(operator.isEmpty()){
            return null;
        }
        Operator operator1 = operator.get();
        operator1.setPasswd(null);
        //operator1.setTotpsecret(null);
        return operator1;
    }

    @Override
    public ResponseBean updateUserpw(Operator user, String oldPwd) throws Exception {
        user.setPasswd(user.getPasswd());


        Operator operator = repository.findOperatorByCode(user.getCode());

        if (operator != null) {
            if (operator.getId().equals(1L)) {
                return ResponseBean.getParamUnmatchedException(LangCode.of("permission.admin_disable_update"));
            }
            if (!StringUtils.hasLength(oldPwd)) {
                return ResponseBean.getParamEmptyException(LangCode.of("permission.old_passwd"));
            }
            Operator old = operator.clone();
            old.setPasswd(oldPwd);


            if (!Objects.equals(encrypt(old), operator.getPasswd())) {
                return ResponseBean.getParamUnmatchedException(LangCode.of("permission.erroneous"), LangCode.of("permission.old_passwd"));
            }
            if (Objects.equals(encrypt(old), encrypt(user))) {
                return ResponseBean.getParamUnmatchedException(LangCode.of("permission.old_passwd_not_equals_new"));
            }
            operator.setPasswdUpdateYmd(DateUtil.getDate());
            operator.setPasswd(encrypt(user));
            repository.save(operator);
            return ResponseBean.getSuccess(true);
        } else {
            return ResponseBean.getParamUnmatchedException(LangCode.of("permission.erroneous"), LangCode.of("permission.user_code"));
        }
    }
}