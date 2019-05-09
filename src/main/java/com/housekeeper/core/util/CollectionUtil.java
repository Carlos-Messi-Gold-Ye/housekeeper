package com.housekeeper.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 集合工具类
 *
 * @author yezy
 * @since 2019/3/24
 */
public final class CollectionUtil {

    private CollectionUtil() {
    }

    public static <T> Set<T> merge(Collection<T> t1, Collection<T> t2) {
        if (t1 == null && t2 == null) {
            return new HashSet<>();
        } else if (t1 == null) {
            return new HashSet<>(t2);
        } else if (t2 == null) {
            return new HashSet<>(t1);
        }
        Set<T> set = new HashSet<>(t1);
        set.addAll(t2);
        return set;
    }

    public static <T> Set<T> removeAll(Collection<T> t1, Collection<T> t2) {
        if (t1 == null) {
            return new HashSet<>();
        } else if (t2 == null) {
            return new HashSet<>(t1);
        }
        Set<T> set = new HashSet<>(t1);
        set.removeAll(t2);
        return set;
    }

    public static <T> Set<T> retainAll(Collection<T> t1, Collection<T> t2) {
        if (t1 == null || t2 == null) {
            return new HashSet<>();
        }
        Set<T> set = new HashSet<>(Math.min(t1.size(), t2.size()));

        for (Iterator iter = t1.iterator(); iter.hasNext();) {
            T obj = (T) iter.next();
            if (t2.contains(obj)) {
                set.add(obj);
            }
        }
        return set;
    }

    public static <T> List<T> newArrayList(T... e) {
        List<T> result = new ArrayList<>();
        for (T e1 : e) {
            result.add(e1);
        }
        return result;
    }
}
