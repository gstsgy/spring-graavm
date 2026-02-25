package com.gstsgy.base.bean.db;


import com.gstsgy.base.bean.anno.NotUpdate;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SoftDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@SoftDelete(columnName = "is_del") // 核心注解
@MappedSuperclass
public class BaseTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @CreatedDate
    private String insertYmd;//

    @Column(updatable = false)
    @CreatedBy
    private Long insertId;//

    @LastModifiedDate
    private String updateYmd;//

    @LastModifiedBy
    private Long updateId;
    @Version
    private int updateFlag;//

}
