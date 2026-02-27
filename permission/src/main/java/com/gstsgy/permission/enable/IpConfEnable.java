package com.gstsgy.permission.enable;

import com.gstsgy.permission.bean.db.IpConf;
import com.gstsgy.permission.conf.IpConfCache;
import com.gstsgy.permission.repository.IpConfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IpConfEnable implements CommandLineRunner{

    @Autowired
    private IpConfRepository ipConfMapper;
    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        List<IpConf> ips = ipConfMapper.findAll();
        ips.forEach(ip->{
            if(ip.getType()==1){
                IpConfCache.addIpBlacklist(ip.getIp());
            }else if(ip.getType()==2){
                IpConfCache.addIpWhitelist(ip.getIp());
            }
        });
    }
    
}
