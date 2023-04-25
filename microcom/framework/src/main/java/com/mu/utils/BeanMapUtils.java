package com.mu.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MUZUKI
 * @Classname BeanMapUtils
 * @Description TODO
 * @Date 2023/4/24 21:51
 */

public class BeanMapUtils {

    /**
     * 将Java Bean对象转换为Map对象
     *
     * @param bean Java Bean对象
     * @return Map对象
     */
    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String propertyName = propertyDescriptor.getName();
                if (!"class".equals(propertyName)) {
                    Method readMethod = propertyDescriptor.getReadMethod();
                    Object value = readMethod.invoke(bean);
                    map.put(propertyName, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将Map对象转换为Java Bean对象
     *
     * @param map  Map对象
     * @param bean Java Bean对象类型
     * @return Java Bean对象
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> bean) {
        try {
            T obj = bean.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(bean);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String propertyName = propertyDescriptor.getName();
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    writeMethod.invoke(obj, value);
                }
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}