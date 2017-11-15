package com.lxb.common.utils;

import java.util.Map;

/**
 * @Description 对象转换工具类
 * @Author Liaoxb
 * @Date 2017/11/15 0015 11:57:57
 */
public class ObjectConvert {

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        org.apache.commons.beanutils.BeanUtils.populate(obj, map);

        return obj;
    }

    public static Map<?, ?> objectToMap(Object obj) {
        if(obj == null)
            return null;

        return new org.apache.commons.beanutils.BeanMap(obj);
    }

}
