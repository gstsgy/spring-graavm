package com.gstsgy.base.bean.db;

import com.gstsgy.base.utils.WebUtils;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SoftDelete;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@SoftDelete(columnName = "is_del") // 核心注解
@MappedSuperclass
public class BaseTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(updatable = false)
    private String insertYmd;//

    @Column(updatable = false)
    private Long insertId;//

    private String updateYmd;//

    private Long updateId;
    @Version
    private int updateFlag;//

    @PrePersist
    public void onPrePersist() {
        // 1. 获取用户信息（你日志里确认能拿到的那个方法）
        Long userId = WebUtils.getUserId();
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.insertId = userId;
        this.updateId = userId;
        this.insertYmd = now;
        this.updateYmd = now;
        // 验证日志
        System.out.println("Native-PrePersist: UserID=" + userId + ", Entity=" + this.getClass().getSimpleName());
    }

    @PreUpdate
    public void onPreUpdate() {
        // 每次执行 update SQL 前触发
        Long userId = WebUtils.getUserId();
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updateId = userId;
        this.updateYmd = now;
    }
}
