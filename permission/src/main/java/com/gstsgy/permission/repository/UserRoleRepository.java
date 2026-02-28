package com.gstsgy.permission.repository;

import com.gstsgy.base.repository.BaseRepository;
import com.gstsgy.permission.bean.db.UserRole;
import java.util.List;

public interface UserRoleRepository extends BaseRepository<UserRole> {
    List<UserRole> findByUserId(Long userId);

    void deleteAllByUserId(Long userId);

    void deleteAllByRoleId(Long roleId);
}