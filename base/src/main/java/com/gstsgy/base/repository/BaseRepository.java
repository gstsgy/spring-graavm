package com.gstsgy.base.repository;

import com.gstsgy.base.bean.db.BaseTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseRepository<T extends BaseTable> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
