package com.housekeeper.core.log;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yezy
 * @since 2019/1/18
 */
public class LogValuePair {

    private static final String EQ = "=";

    private final String name;

    private final Object value;

    private LogValuePair(String name, Object value) {
        this.name = Optional.ofNullable(name).orElse(LogConstants.EMPTY_STR);
        this.value = Optional.ofNullable(value).map(Object::toString).orElse(LogConstants.EMPTY_STR);
    }

    private LogValuePair(String name, String value, int maxLength) {
        this(name, StringUtils.truncate(value, maxLength));
    }

    public static LogValuePair of(String name, Object value) {
        return new LogValuePair(name, value);
    }

    public static LogValuePair of(String name, String value, int maxLength) {
        return new LogValuePair(name, value, maxLength);
    }

    @Override
    public String toString() {
        return this.name + EQ + this.value;
    }
}
