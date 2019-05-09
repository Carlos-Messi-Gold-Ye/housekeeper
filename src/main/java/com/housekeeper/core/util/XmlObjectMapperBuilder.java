package com.housekeeper.core.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

/**
 * @author yezy
 */
public class XmlObjectMapperBuilder {

    private static final ObjectMapper DEFAULT_OBJECT_MAPPER;

    static {
        DEFAULT_OBJECT_MAPPER = createMapper();
    }

    public static ObjectMapper getXmlMapper() {
        return DEFAULT_OBJECT_MAPPER;
    }

    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new XmlMapper();
        mapper.setDateFormat(new SimpleDateFormat());
        AnnotationIntrospector primary = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
        mapper.setAnnotationIntrospector(AnnotationIntrospector.pair(secondary, primary));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        return mapper;
    }

    private final ObjectMapper objectMapper;

    public XmlObjectMapperBuilder() {
        this.objectMapper = DEFAULT_OBJECT_MAPPER;
    }

    public ObjectMapper get() {
        return objectMapper;
    }

    public static XmlObjectMapperBuilder defaultXmlMapper() {
        return new XmlObjectMapperBuilder();
    }
}
