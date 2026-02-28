package com.gstsgy.permission.repository;

import com.gstsgy.base.repository.BaseRepository;
import com.gstsgy.permission.bean.db.Dictionary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DictionaryRepository extends BaseRepository<Dictionary> {

    List<Dictionary> findDictionariesByModelCodeOrderBySeq(String modelCode);

    List<Dictionary> findAllByParentId(Long parentId);

    List<Dictionary> findAllByParentIdIsNull();

    @Modifying
    @Query("update Dictionary d set d.modelCode = ?1 where d.parentId = ?2")
    int updateModelKeyByParentId(String modelCode, Long parentId);
}