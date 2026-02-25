package com.gstsgy.webapi.utils;

import com.gstsgy.base.utils.generator.GeneratorService;
import com.gstsgy.permission.bean.db.Form;
import com.gstsgy.webapi.bean.db.*;

public class Tools {
    public static void main(String[] args) {
        GeneratorService.generateS(AccountBook.class);
        GeneratorService.generateS(BabyBase.class);
        GeneratorService.generateS(BabyInfoLog.class);
        GeneratorService.generateS(BabyVaccine.class);
        GeneratorService.generateS(Family.class);
        GeneratorService.generateS(FamilyMembers.class);
        GeneratorService.generateS(Transactions.class);
    }
}
