package com.gstsgy.webapi.enable;

import com.gstsgy.permission.utils.AuthWhiteUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PermissionEnable implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        //AuthWhiteUtil.isOpen = false;
        AuthWhiteUtil.addUrl("/swagger-ui/.*");
        AuthWhiteUtil.addUrl("/v3/.*");
        AuthWhiteUtil.addUrl("/report/.*");

    }
}
