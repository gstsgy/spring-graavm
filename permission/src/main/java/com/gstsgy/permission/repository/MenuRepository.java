package com.gstsgy.permission.repository;

import com.gstsgy.base.repository.BaseRepository;
import com.gstsgy.permission.bean.db.Menu;
import java.util.List;

public interface MenuRepository extends BaseRepository<Menu> {

    List<Menu> findAllByType(Integer type);

    List<Menu> findAllByTypeAndParentId(Integer type,Long parentId);
}