package com.housekeeper.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collections;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yezy
 * @since 2019/4/1
 */
public class ReflectionUtil {

    private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     */
    public static Object getFieldValue(final Object object, final String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }
    }

    /**
     * 直接读取对象属性值, 经过getter函数.
     */
    public static String getProperty(final Object object, final String propertyName) {
        try {
            return BeanUtils.getProperty(object, propertyName);
        } catch (Exception e) {
            throw convertCheckedExceptionToUnchecked(e);
        }
    }

    /**
     * 直接设置对象属性值, 经过setter函数.
     */
    public static void setProperty(Object object, String fieldName, String value) {
        if (StringUtils.isNotBlank(value)) {
            try {
                BeanUtils.setProperty(object, fieldName, value);
            } catch (Exception e) {
                throw convertCheckedExceptionToUnchecked(e);
            }
        } else {
            ReflectionUtil.setFieldValue(object, fieldName, null);
        }
    }

    /**
     * 获取对象的DeclaredFields.
     *
     * @param object
     * @return
     */
    public static Field[] getDeclaredFields(final Object object) {
        Validate.notNull(object, "object不能为空");
        return object.getClass().getDeclaredFields();
    }

    public static boolean isFieldSame(Object domain, Object lastDomain, String fieldName) {
        Object value = getFieldValue(domain, fieldName);
        Object oldValue = getFieldValue(lastDomain, fieldName);
        if (value == null) {
            return oldValue == null || "".equals(oldValue) || Collections.emptyMap().equals(oldValue);
        } else if (oldValue == null) {
            return "".equals(value) || Collections.emptyMap().equals(value);
        } else {
            return oldValue.equals(value);
        }
    }

    /**
     * 将checked exception转换为unchecked exception.
     * @param e 待转换的异常
     * @return 转换后的unchecked异常
     */
    private static RuntimeException convertCheckedExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException)
            return new IllegalArgumentException(e.getMessage(), e);
        else if (e instanceof InvocationTargetException) {
            Throwable target = ((InvocationTargetException) e).getTargetException();
            if (target instanceof RuntimeException) {
                return (RuntimeException) target;
            }
            return new RuntimeException("Invocation Target Exception.", target);
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField.
     *
     * 如向上转型到Object仍无法找到, 返回null.
     */
    private static Field getDeclaredField(final Object object, final String fieldName) {
        Validate.notNull(object, "object不能为空");
        Validate.notEmpty(fieldName, "fieldName");
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 强行设置Field可访问.
     */
    private static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }
}
