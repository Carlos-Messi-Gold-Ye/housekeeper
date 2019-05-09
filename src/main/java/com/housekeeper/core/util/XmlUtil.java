package com.housekeeper.core.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author yezy
 */
public class XmlUtil {

    private static Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    public static <T> String toXml(T bean) {
        try {
            return XmlObjectMapperBuilder.defaultXmlMapper().get().writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            logger.error("serialize xml error:{}.", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromXml(String xml, Class<T> beanClass) {
        try {
            return XmlObjectMapperBuilder.defaultXmlMapper().get().readValue(xml, beanClass);
        } catch (IOException e) {
            logger.error("deserialize xml error:{}.", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
