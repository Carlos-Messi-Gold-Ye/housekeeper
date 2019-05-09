package com.housekeeper.core.util;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * 国际化工具类
 *
 * @author yezy
 * @since 2019/3/5
 */
public class I18nUtil {

    private static MessageSource messageSource;

    private I18nUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void setMessageSource(MessageSource messageSource) {
        I18nUtil.messageSource = messageSource;
    }

    public static String getMessage(String code, Locale locale, String... args) {
        return messageSource.getMessage(code, args, locale);
    }
}
