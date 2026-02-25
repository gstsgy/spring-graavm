package com.gstsgy.base.bean.anno;

import com.gstsgy.base.bean.enums.DuplicateStrategy;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UnionUnique {
    String group() default "";
    String  value() default "";
    String  message() default "%s已存在，请重新输入";
    DuplicateStrategy strategy() default DuplicateStrategy.THROW_EXCEPTION;
}
