/**
 * @(#)IdUtil.java 2012-4-9
 * <p>
 * Copyright 2000-2012 by ChinanetCenter Corporation.
 * <p>
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * ChinanetCenter Corporation ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with ChinanetCenter.
 */

package com.housekeeper.util;

import java.util.UUID;

/**
 * ID相关工具类
 *
 * @author yezy
 */
public final class IdUtil {
    private IdUtil() {
    }

    public static boolean isNullId(Long id) {
        return id == null || id.longValue() <= 0;
    }

    public static boolean isNotNullId(Long id) {
        return !isNullId(id);
    }

    public static boolean isNullId(Integer id) {
        return id == null || id.intValue() <= 0;
    }

    public static boolean isNotNullId(Integer id) {
        return !isNullId(id);
    }

    /**
     * 生成32位UUID字符串
     */
    public static String genUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成UUID字符串,带前缀
     */
    public static String genUuidWithPre(String prefix) {
        return prefix + genUuid();
    }
}
