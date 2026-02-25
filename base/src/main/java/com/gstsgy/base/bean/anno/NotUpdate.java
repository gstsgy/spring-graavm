package com.gstsgy.base.bean.anno;

import java.lang.annotation.*;

/**
 * 不允许手动修改
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface NotUpdate {
}
