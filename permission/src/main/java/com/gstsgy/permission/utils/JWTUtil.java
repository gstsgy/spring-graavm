package com.gstsgy.permission.utils;

import java.util.Date;
import java.util.Objects;

import javax.crypto.SecretKey;

import com.gstsgy.base.utils.SpringUtils;
import com.gstsgy.base.utils.WebUtils;
import com.gstsgy.permission.bean.db.Operator;
import com.gstsgy.permission.service.OperatorService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


public class JWTUtil {



    public static SecretKey generalKeyByDecoders() {

        return Keys.hmacShaKeyFor(Decoders.BASE64.decode("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ="));

    }


    public static String createJwtToken(Operator user) {

        //创建token,在token中加入必备信息
        return Jwts.builder()
                .setId(user.getId() + "")
                .setSubject(user.getPasswd())
                .signWith(generalKeyByDecoders())
                .setIssuedAt(new Date()) //设置创建时间戳
                .setExpiration(new Date(System.currentTimeMillis() + AuthWhiteUtil.timeout))
                .compact();

    }

    private static OperatorService userService;

    /**
     * 验签token的方法
     */
    public static void validateToken(String jwtToken) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(generalKeyByDecoders())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        //解析出用户的自定义信息
        Operator user = new Operator();
        user.setPasswd(claim.getSubject());
        user.setId(Long.parseLong(claim.getId()));
        if (userService == null) {
            userService = SpringUtils.getBean(OperatorService.class);
        }
        Operator tmp = userService.queryOneById(user.getId());
        if (!Objects.equals(tmp.getPasswd(), user.getPasswd())) {
            throw new RuntimeException("密码已变更");
        }
        // 校验接口权限

        WebUtils.setUserId(user.getId());
    }


    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoiMTExMTExIiwiaWF0IjoxNjc1MjM3NDkwLCJleHAiOjE2NzY1MzM0OTB9.Re2k4oXdq58LaDKnGdNh4-nDUmmyzWTxk8pqqYXKOi8";
        validateToken(token);
        System.out.println(token);
    }

}
