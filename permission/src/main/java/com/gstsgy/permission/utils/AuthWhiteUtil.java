package com.gstsgy.permission.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class AuthWhiteUtil {
    private static List<String> urls = new ArrayList<>();
    public static boolean isOpen = true;
    /**
     * 密钥15天过期
     */
    public static Integer timeout = 1000 * 60 * 60 * 24 * 15;
    static {
        urls.add("/auth/login");
        urls.add("/auth/wxlogin1");
        urls.add("/auth/wxlogin");
        //urls.add("/auth/wxlogin");
        //urls.add("/user/bind-wx");
        //urls.add("/auth/login");
    }

    public static void addUrl(String url){
        urls.add(url);
    }

    public static boolean matches(String url){
        return urls.stream().anyMatch(it -> Pattern.matches(it, url));
    }

    public static void main(String[] args) {
        System.out.println(matches("/"));
        System.out.println(matches("/v"));

        System.out.println(matches("/auth/login"));
    }
}
