package com.msk3cloud.core.fieldparse;

import java.lang.annotation.*;

/**
 * 字段标识
 * @author Rao
 * @Date 2022/01/12
 **/
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Documented
public @interface FieldKey {

    /**
     * 字段名称
     * @return
     */
    String value();

}
