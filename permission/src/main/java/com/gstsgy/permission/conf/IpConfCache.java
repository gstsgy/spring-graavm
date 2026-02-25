package com.gstsgy.permission.conf;

import com.gstsgy.base.utils.SpringUtils;
import com.gstsgy.base.utils.WebUtils;
import com.gstsgy.permission.bean.db.IpConf;
import com.gstsgy.permission.repository.IpConfRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class IpConfCache {
    private static final Set<String> IP_BLACKLIST = new HashSet();
    private static final Set<String> IP_WHITELIST = new HashSet();

    private static IpConfRepository ipConfMapper;

    private static final Map<String, Integer> loginCount = new HashMap<>();

    public static void addLoginCount() {
        if(IP_WHITELIST.contains(WebUtils.getRequestIp())){
            return;
        }
        loginCount.put(WebUtils.getRequestIp(), loginCount.getOrDefault(WebUtils.getRequestIp(), 0) + 1);
        if(loginCount.get(WebUtils.getRequestIp()) > 4){
            IP_BLACKLIST.add(WebUtils.getRequestIp());
            if(ipConfMapper ==null){
                ipConfMapper = SpringUtils.getBean(IpConfRepository.class);
            }
            IpConf ipConfDO = new IpConf();
            ipConfDO.setIp(WebUtils.getRequestIp());
            ipConfDO.setType(1);
            ipConfMapper.save(ipConfDO);
            loginCount.remove(WebUtils.getRequestIp());
        }
    }

    public static Set<String> getIpBlacklist() {
        return IP_BLACKLIST;
    }

    public static Set<String> getIpWhitelist() {
        return IP_WHITELIST;
    }

    public static void addIpBlacklist(String ip) {
        IP_BLACKLIST.add(ip);
    }

    public static void addIpWhitelist(String ip) {
        IP_WHITELIST.add(ip);
    }

    public static void removeIpBlacklist(String ip) {
        IP_BLACKLIST.remove(ip);
    }

    public static void removeIpWhitelist(String ip) {
        IP_WHITELIST.remove(ip);
    }

    public static void addIpBlacklist(Set<String> ips) {
        IP_BLACKLIST.addAll(ips);
    }

    public static void addIpWhitelist(Set<String> ips) {
        IP_WHITELIST.addAll(ips);
    }
}

