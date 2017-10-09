package com.lxb.common.annotation;

import java.lang.annotation.*;

/**
 * @Description 初始化继承BaseService的service
 * @Author Liaoxb
 * @Date 2017/9/30 15:21:21
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseService {
}
