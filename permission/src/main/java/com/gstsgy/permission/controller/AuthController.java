package com.gstsgy.permission.controller;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.base.utils.Encrypt;
import com.gstsgy.base.utils.WebUtils;
import com.gstsgy.permission.bean.db.Btn;
import com.gstsgy.permission.bean.db.Operator;
import com.gstsgy.permission.bean.view.MenuTreeVO;
import com.gstsgy.permission.conf.IpConfCache;
import com.gstsgy.permission.service.OperatorService;
import com.gstsgy.permission.service.RoleMenuService;
import com.gstsgy.permission.utils.GoogleAuthenticatorUtils;
import com.gstsgy.permission.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@Tag(name = "登录接口")
@RestController
@RequestMapping("auth")

@RegisterReflectionForBinding({MenuTreeVO.class})
public class AuthController {
    @Autowired
    private OperatorService userService;
    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 登陆认证接口
     *
     * @param userDTO
     * @return
     */
    @Operation(description = "登录")
    @PostMapping("/login")
    public ResponseBean login(@RequestBody Operator userDTO, @RequestParam("code") int code) {
        if (userDTO == null) {
            return ResponseBean.getParamEmptyException("用户名和密码");
        }
        Operator queryOne = userService.queryOneByCode(userDTO.getCode());
        if (queryOne == null) {
            queryOne = userService.queryOneByEmail(userDTO.getCode());
        }
        if (queryOne == null) {
            IpConfCache.addLoginCount();
            return ResponseBean.getParamUnmatchedException("用户名不存在");
        }

        if (!queryOne.getPasswd().equals(userService.encrypt(userDTO))) {
            IpConfCache.addLoginCount();
            return ResponseBean.getParamUnmatchedException("密码不正确");
        }
        if(StringUtils.hasText(queryOne.getTotpsecret())){
            String key = "N0b/5bEeZtZgZCvNB/F0LxqHlpNlCyDmHcXbv0YJgVI=";
            Base64.Decoder decoder = Base64.getDecoder();

            String source =new String(Objects.requireNonNull(Encrypt.decryptToAES(decoder.decode(queryOne.getTotpsecret()), key)), StandardCharsets.UTF_8);
            if(!GoogleAuthenticatorUtils.authorize(source,code)){
                return ResponseBean.getParamUnmatchedException("验证码不正确");
            }
        }

        String token = JWTUtil.createJwtToken(queryOne);
        return ResponseBean.getSuccess(token);
    }

    /**
     * 登陆认证接口
     *
     * @param userDTO
     * @return
     */
    @Operation(description = "WX登录")
    @PostMapping("/wxlogin1")
    public ResponseBean login(@RequestParam("appId")String appId, @RequestParam("secret")String secret, @RequestParam("jsCode")String jsCode, @RequestBody Operator userDTO) {
        if (userDTO == null) {
            return ResponseBean.getParamEmptyException("用户名和密码");
        }
        Operator queryOne = userService.queryOneByCode(userDTO.getCode());
        if (queryOne == null) {
            queryOne = userService.queryOneByEmail(userDTO.getCode());
        }
        if (queryOne == null) {
            IpConfCache.addLoginCount();
            return ResponseBean.getParamUnmatchedException("用户名不存在");
        }

        if (!queryOne.getPasswd().equals(userService.encrypt(userDTO))) {
            IpConfCache.addLoginCount();
            return ResponseBean.getParamUnmatchedException("密码不正确");

        }

        if (queryOne.getId().equals(1L)) {

            return ResponseBean.getParamUnmatchedException("运维账号禁止登录微信");
        }

        String token = JWTUtil.createJwtToken(queryOne);

        String wxId = userService.wxLogin(appId, secret, jsCode);

        userService.bindWxId(wxId, queryOne);

        return ResponseBean.getSuccess(token);
    }

    /**
     * 登陆认证接口
     *
     * @param
     * @return
     */
    @Operation(description = "WX登录")
    @PostMapping("/wxlogin")
    public ResponseBean login(@RequestParam("appId")String appId, @RequestParam("secret")String secret, @RequestParam("jsCode")String jsCode) {
        String wxId = userService.wxLogin(appId, secret, jsCode);
        Operator queryOne = userService.queryOneByWxId(wxId);
        if (queryOne == null) {
            IpConfCache.addLoginCount();
            return ResponseBean.getNoLogin("该微信未绑定账号");
        }

        String token = JWTUtil.createJwtToken(queryOne);
        return ResponseBean.getSuccess(token);
    }

    @Operation(description = "获取菜单")
    @GetMapping("/menus")
    public ResponseBean getMenus(@RequestParam("type")Integer type) {

        return roleMenuService.getAllMenuTree(type, WebUtils.getUserId());
    }

    @Operation(description = "获取功能")
    @GetMapping("/funs")
    public ResponseBean getFuns(@RequestParam("type")Integer type) {
        return roleMenuService.getAllFuns(type, WebUtils.getUserId());
    }
}
