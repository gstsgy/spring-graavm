package com.gstsgy.permission;

import com.gstsgy.base.utils.generator.GeneratorService;
import com.gstsgy.permission.bean.db.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EntityScan
@ComponentScan
public class PermissionConf {
    public static void main(String[] args) {
//        GeneratorService.generateS(Btn.class, Dictionary.class, Form.class, FormBtn.class, FormCol.class,
//                FormGrid.class, FormSingle.class, Menu.class, Operator.class, Role.class, RoleInterface.class, RoleMenu.class,
//                UserRole.class);
        GeneratorService.generateS(Form.class);
        System.out.println("Hello world!");
    }
}