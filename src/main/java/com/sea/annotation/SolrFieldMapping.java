package com.sea.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/7/27.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SolrFieldMapping {
    String value() default  "#default";
}
