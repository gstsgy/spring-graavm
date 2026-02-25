package com.gstsgy.base.service.impl;


import java.util.*;
import com.gstsgy.base.bean.anno.NotUpdate;
import com.gstsgy.base.bean.anno.Unique;
import com.gstsgy.base.bean.dto.PageQueryVO;
import com.gstsgy.base.bean.db.BaseTable;
import com.gstsgy.base.bean.exception.APIException;
import com.gstsgy.base.repository.BaseRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import com.gstsgy.base.service.BaseService;
import org.springframework.util.ReflectionUtils;

public abstract  class BaseServiceImpl<T extends BaseTable,M extends BaseRepository<T>> implements BaseService<T>{

    @Autowired
    protected M repository;
    @Override
    public T saveOne(T obj) {
        BeanWrapper wrapper = new BeanWrapperImpl(obj);
        Map<String, Object> uniqueValues = new HashMap<>();
        List<String> notUpdateFields = new ArrayList<>();

        // 1. 一次性收集所有注解信息
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            // 处理唯一键
            Unique unique = field.getAnnotation(Unique.class);
            if (unique != null) {
                Object value = wrapper.getPropertyValue(field.getName());
                if (value != null) uniqueValues.put(field.getName(), value);
            }
            // 处理不可更新字段
            if (field.isAnnotationPresent(NotUpdate.class)) {
                notUpdateFields.add(field.getName());
            }
        });

        // 2. 唯一性校验 (如果是更新，排除自身)
        if (!uniqueValues.isEmpty()) {
            Specification<T> spec = (root, query, cb) -> {
                List<Predicate> predicates = uniqueValues.entrySet().stream()
                        .map(e -> cb.equal(root.get(e.getKey()), e.getValue()))
                        .toList();

                Predicate orLogic = cb.or(predicates.toArray(new Predicate[0]));
                if (obj.getId() != null) {
                    return cb.and(orLogic, cb.notEqual(root.get("id"), obj.getId()));
                }
                return orLogic;
            };

            if (repository.count(spec) > 0) {
                throw new APIException(HttpStatus.CONFLICT, "数据违反唯一性约束");
            }
        }

        // 3. 不可更新字段保护
        if (obj.getId() != null && !notUpdateFields.isEmpty()) {
            repository.findById(obj.getId()).ifPresent(oldEntity -> {
                BeanWrapper oldWrapper = new BeanWrapperImpl(oldEntity);
                for (String fieldName : notUpdateFields) {
                    // 将数据库里的旧值重新赋给待保存的对象，覆盖前端传来的值
                    wrapper.setPropertyValue(fieldName, oldWrapper.getPropertyValue(fieldName));
                }
            });
        }

        return repository.save(obj);
    }


    @Transactional
    @Override
    public List<T> batchSave(List<T> objs) {
        if (objs == null || objs.isEmpty()) return Collections.emptyList();

        // 1. 预处理：只反射一次获取字段配置
        Class<?> clazz = objs.get(0).getClass();
        List<String> uniqueFields = new ArrayList<>();
        List<String> notUpdateFields = new ArrayList<>();
        ReflectionUtils.doWithFields(clazz, field -> {
            if (field.isAnnotationPresent(Unique.class)) uniqueFields.add(field.getName());
            if (field.isAnnotationPresent(NotUpdate.class)) notUpdateFields.add(field.getName());
        });

        // 2. 批量获取旧数据 (处理 NotUpdate)
        List<Long> ids = objs.stream().map(T::getId).filter(Objects::nonNull).toList();
        Map<Object, T> oldEntitiesMap = new HashMap<>();
        if (!ids.isEmpty() && !notUpdateFields.isEmpty()) {
            // 一次性查出所有旧数据，避免 saveOne 里的 findById
            repository.findAllById(ids).forEach(old -> oldEntitiesMap.put(old.getId(), old));
        }

        // 3. 处理数据
        for (T obj : objs) {
            BeanWrapper wrapper = new BeanWrapperImpl(obj);

            // A. 处理唯一性 (此处逻辑较复杂，建议保留 saveOne 的校验或改为局部批量校验)
            // 为保证简单，这里调用精简后的校验逻辑，或保留 saveOne
            checkUnique(obj, uniqueFields, wrapper);

            // B. 处理不可更新字段 (从 Map 获取，无需再查库)
            if (obj.getId() != null && oldEntitiesMap.containsKey(obj.getId())) {
                BeanWrapper oldWrapper = new BeanWrapperImpl(oldEntitiesMap.get(obj.getId()));
                for (String field : notUpdateFields) {
                    wrapper.setPropertyValue(field, oldWrapper.getPropertyValue(field));
                }
            }
        }

        // 4. 调用 JPA 批量保存
        return repository.saveAll(objs);
    }

    // 提取唯一性校验为独立方法
    private void checkUnique(T obj, List<String> uniqueFields, BeanWrapper wrapper) {
        if (uniqueFields.isEmpty()) return;

        Specification<T> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String field : uniqueFields) {
                Object val = wrapper.getPropertyValue(field);
                if (val != null) predicates.add(cb.equal(root.get(field), val));
            }
            if (predicates.isEmpty()) return null;

            Predicate orLogic = cb.or(predicates.toArray(new Predicate[0]));
            return obj.getId() != null ? cb.and(orLogic, cb.notEqual(root.get("id"), obj.getId())) : orLogic;
        };

        if (repository.count(spec) > 0) {
            throw new APIException(HttpStatus.CONFLICT, "数据违反唯一性约束");
        }
    }


    @Override
    public void deleteById(Long id) {
         repository.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
         repository.deleteAllById(ids);
    }

    @Override
    public T queryOneById(Long id) {
        return repository.findById(id).orElse( null);
    }

    @Override
    public List<T> query(T obj) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                // 忽略所有 null 字段
                .withIgnoreNullValues()
                // 只要字段是字符串，就执行包含查询（like %value%）
                // 如果不加这行，字符串默认也是 eq
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                // 忽略大小写

                .withIgnoreCase().withIgnorePaths("updateFlag");

        // 创建 Example 实例
        Example<T> example = Example.of(obj, matcher);

        // 执行查询
        return repository.findAll(example);
    }

    @Override
    public Map query(T obj, PageQueryVO pageQuery) {
        // 1. 定义匹配规则 (Matcher)
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase() // 忽略大小写
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // 字符串模糊匹配
                .withIgnoreNullValues().withIgnorePaths("updateFlag"); // 忽略 null 字段

        // 2. 创建 Example (Probe + Matcher)
        Example<T> example = Example.of(obj, matcher);
        int pageNum = pageQuery.getPageNum();
        if (pageNum > 0) {
            pageNum = pageNum - 1; // 转换为 Spring 识别的从 0 开始
        }
        // 3. 创建分页请求 (Pageable)
        Pageable pageable = PageRequest.of(pageNum, pageQuery.getPageSize(), Sort.by("insertYmd").descending());

        // 4. 执行分页查询
        // JpaRepository 自动支持：Page<T> findAll(Example<S> example, Pageable pageable)
        Page<T> pages= repository.findAll(example, pageable);
        Map map = new HashMap();
        map.put("total", pages.getTotalElements());
        map.put("records", pages.getContent());

        return map;
    }
}
