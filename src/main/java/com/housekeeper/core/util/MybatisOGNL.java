package com.housekeeper.core.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 自定义OGNL表达式处理类
 *
 * @author yezy
 * @since 2019/2/21
 */
public class MybatisOGNL {

    private static final String EQ = "eq";
    private static final String LIKE = "like";

    /**
     * 判断是否为空
     *
     * @param o 判断对象
     * @return true空，false非空
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return StringUtils.isBlank(o.toString());
        } else if (o instanceof Collection) {
            return ((Collection) o).isEmpty();
        } else if (o instanceof Map) {
            return ((Map) o).isEmpty();
        } else if (o.getClass().isArray()) {
            return Array.getLength(o) == 0;
        }
        return false;
    }

    /**
     * 判断是否未空
     *
     * @param o 判断对象
     * @return true非空，false空
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 模糊查询判断，默认模糊
     *
     * @param o
     * @return
     */
    public static boolean isLike(Object o) {
        return o == null || o.toString().equals(LIKE);
    }

    /**
     * 精确查询判断，默认模糊
     *
     * @param o
     * @return
     */
    public static boolean isEq(Object o) {
        return o != null && o.toString().equals(EQ);
    }

    public static String getLikeColumnValue(String value, String likeOrEq) {
        if (StringUtils.isNotBlank(value) && !EQ.equals(likeOrEq)) { //不空，且不是eq加%+%，组装模糊查询
            return "%" + value + "%";
        }
        return value;
    }

    public static boolean isEqual(Object property, String compareValue) {
        return property != null && property.toString().equals(compareValue);
    }
}
