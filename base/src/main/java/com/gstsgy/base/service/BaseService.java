package com.gstsgy.base.service;

import java.util.List;
import java.util.Map;

import com.gstsgy.base.bean.dto.PageQueryVO;
import com.gstsgy.base.bean.db.BaseTable;
import org.springframework.data.domain.Page;

public interface BaseService<T extends BaseTable> {
    T saveOne(T obj);

    List<T> batchSave(List<T> objs);

    void deleteById(Long id);

    void deleteByIds(List<Long> ids);

    T queryOneById(Long id);

    List<T> query(T obj);

    Map query(T obj, PageQueryVO pageQuery);
}
