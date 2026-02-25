package com.gstsgy.webapi.repository;

import com.gstsgy.webapi.bean.db.FamilyMembers;
import com.gstsgy.base.repository.BaseRepository;

import java.util.List;

public interface FamilyMembersRepository extends BaseRepository<FamilyMembers> {

    FamilyMembers findAllByUserId(Long userId);

    List<FamilyMembers> findAllByFamilyId(Long familyId);
}