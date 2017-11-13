package com.luntan.deppon.core.annotation;

import java.lang.annotation.*;

/**
 * Created by cubc-luntan 2016/11/26.
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Clear {

}
