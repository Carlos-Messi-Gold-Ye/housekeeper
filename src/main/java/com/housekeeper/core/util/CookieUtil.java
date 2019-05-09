/*
 * @(#)CookieUtil.java 2019-02-23
 *
 * Copyright 2000-2019 by ChinanetCenter Corporation.
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ChinanetCenter Corporation ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with ChinanetCenter.
 */
package com.housekeeper.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yezy
 */
public final class CookieUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookieUtil.class);

    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String key, String value) {
        Cookie cookie = getCookie(request, key);
        if (cookie == null) {
            cookie = new Cookie(key, value);
        } else {
            cookie.setValue(value);
        }
        cookie.setSecure(request.isSecure());
        cookie.setPath(request.getContextPath());
        addHttpOnlyCookie(response, cookie);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie的值
     *
     * @param request  request
     * @param response response
     * @param key      cookie的key
     * @param remove   使用后是否删除该cookie
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, HttpServletResponse response, String key, boolean remove) {
        String value = null;
        Cookie cookie = getCookie(request, key);
        if (cookie != null) {
            if (StringUtils.isNotBlank(cookie.getValue())) {
                try {
                    value = cookie.getValue();
                } catch (Exception e) {
                    LOGGER.error("encrypt error,value:{},msg:{}", new Object[]{cookie.getValue(), e.getMessage()});
                }
            }
            if (remove) {
                //使用完成后，删除该cookie
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return value;
    }

    /**
     * @param response HttpServletResponse类型的响应
     * @param cookie   要设置httpOnly的cookie对象
     */
    private static void addHttpOnlyCookie(HttpServletResponse response, Cookie cookie) {
        //依次取得cookie中的名称、值、最大生存时间、路径、域和是否为安全协议信息
        StringBuffer strBufferCookie = new StringBuffer();
        strBufferCookie.append(cookie.getName() + "=" + cookie.getValue() + "; ");

        if (cookie.getMaxAge() >= 0) {
            strBufferCookie.append("Max-Age=" + cookie.getMaxAge() + "; ");
        }

        if (StringUtils.isNotBlank(cookie.getDomain())) {
            strBufferCookie.append("Domain=" + cookie.getDomain() + "; ");
        }

        if (StringUtils.isNotBlank(cookie.getPath())) {
            strBufferCookie.append("Path=" + cookie.getPath() + "; ");
        }

        if (cookie.getSecure()) {
            strBufferCookie.append("Secure; ");
        }
        strBufferCookie.append("HttpOnly");
        response.addHeader("Set-Cookie", strBufferCookie.toString());
    }

    private static Cookie getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(key, cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

}
