package com.gstsgy.webapi.service.impl;

import com.gstsgy.base.utils.WebUtils;
import com.gstsgy.webapi.bean.db.AccountBook;
import com.gstsgy.webapi.bean.db.FamilyMembers;
import com.gstsgy.webapi.repository.AccountBookRepository;
import com.gstsgy.webapi.repository.FamilyMembersRepository;
import com.gstsgy.webapi.service.AccountBookService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

import java.util.List;

@Service
public class AccountBookServiceImpl extends BaseServiceImpl<AccountBook, AccountBookRepository> implements AccountBookService {
    @Autowired
    private FamilyMembersRepository familyMembersRepository;
    @Override
    public List<AccountBook> getAccountBookList() {
        FamilyMembers familyMembersDO = familyMembersRepository.findAllByUserId(WebUtils.getUserId());
        if(familyMembersDO == null){
            throw new RuntimeException("请先加入家庭");
        }
        Specification<AccountBook> spec = (root, query, cb) -> {
            // 条件1: familyId 相等
            Predicate p1 = cb.equal(root.get("familyId"), familyMembersDO.getFamilyId());

            // 条件2: (insertId 相等 AND familyId 为空)
            Predicate p2 = cb.and(
                    cb.equal(root.get("insertId"), WebUtils.getUserId()),
                    cb.isNull(root.get("familyId"))
            );

            // 最终逻辑: p1 OR p2
            return cb.or(p1, p2);
        };

        //List<AccountBookDO> result = repository.findAll(spec);
//        QueryWrapper<AccountBookDO> queryWrapper1 = new QueryWrapper<>();
//        queryWrapper1.lambda().eq(AccountBookDO::getFamilyId,familyMembersDO.getFamilyId()).
//                or(i->i.eq(AccountBookDO::getInsertId, WebUtils.getUserId()).isNull(AccountBookDO::getFamilyId));
        return  repository.findAll(spec);
    }
}