package com.gstsgy.permission.utils;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;


public class GoogleAuthenticatorUtils {


    private static final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    /** 获取密钥 **/
    public static String createSecret() {

        final GoogleAuthenticatorKey gak = gAuth.createCredentials();
        return gak.getKey();
    }

    /** 验证身份 **/
    public static boolean authorize(final String secret, final int otp) {

        return gAuth.authorize(secret, otp);
    }

    /** 生成OTP认证URL */
    public static String getOtpAuthUrl(String secret, String account) {
        final String format = "otpauth://totp/%s?secret=%s&issuer=%s";
        return String.format(format, account, secret, "dao.app");
    }
}
