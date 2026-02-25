package com.gstsgy.permission.repository;

import com.gstsgy.base.repository.BaseRepository;
import com.gstsgy.permission.bean.db.Dictionary;

import java.util.List;

public interface DictionaryRepository extends BaseRepository<Dictionary> {

    List<Dictionary> findDictionariesByModelCodeOrderBySeq(String modelCode);
}