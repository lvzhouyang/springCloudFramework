package com.hearglobal.msp.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/**
 * Json utils.
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.21
 */
public class JsonUtils {

    /**
     * The constant OBJECT_MAPPER.
     */
    public static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    /**
     * 初始化ObjectMapper
     *
     * @return object mapper
     * @since 2017.03.21
     */
    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }

    /**
     * 将一个object转换成json对象的字符串
     *
     * @param o the o
     * @return the string
     * @since 2017.03.21
     */
    public static String object2Json(Object o) {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = new JsonFactory().createGenerator(sw);
            OBJECT_MAPPER.writeValue(gen, o);
        } catch (IOException e) {
            throw new RuntimeException("不能序列化对象为Json", e);
        } finally {
            if (null != gen) {
                try {
                    gen.close();
                } catch (IOException e) {
                    throw new RuntimeException("不能序列化对象为Json", e);
                }
            }
        }
        return sw.toString();
    }

    /**
     * 将一个object转换成Map对象
     *
     * @param o the o
     * @return the map
     * @since 2017.03.21
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> object2Map(Object o) {
        return OBJECT_MAPPER.convertValue(o,Map.class);
    }


    /**
     * 将 json 字段串转换为 对象.
     *
     * @param <T>   the type parameter
     * @param json  字符串
     * @param clazz 需要转换为的类
     * @return t
     * @since 2017.03.21
     */
    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + json, e);
        }
    }

    /**
     * 将 json 字段串转换为 List.
     *
     * @param <T>   the type parameter
     * @param json  the json
     * @param clazz the clazz
     * @return list
     * @throws IOException the io exception
     * @since 2017.03.21
     */
    public static <T> List<T> json2List(String json,Class<T> clazz) throws IOException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);

        return OBJECT_MAPPER.readValue(json, type);
    }


    /**
     * 将 json 字段串转换为 数据.
     *
     * @param <T>   the type parameter
     * @param json  the json
     * @param clazz the clazz
     * @return t [ ]
     * @throws IOException the io exception
     * @since 2017.03.21
     */
    public static <T>  T[] json2Array(String json,Class<T[]> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);

    }

    /**
     * 将一个JsonNode 转换成指定类型的对象
     *
     * @param <T>      the type parameter
     * @param jsonNode the json node
     * @param clazz    the clazz
     * @return the t
     * @since 2017.03.21
     */
    public static <T> T node2Object(JsonNode jsonNode, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.treeToValue(jsonNode, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + jsonNode.toString(), e);
        }
    }

    /**
     * 将一个object转换成JsonNode 对象
     *
     * @param o the o
     * @return the json node
     * @since 2017.03.21
     */
    public static JsonNode object2Node(Object o) {
        try {
            if(o == null) {
                return OBJECT_MAPPER.createObjectNode();
            } else {
                return OBJECT_MAPPER.convertValue(o, JsonNode.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("不能序列化对象为Json", e);
        }
    }

    /**
     * Validate boolean.
     *
     * @param input the input
     * @return the boolean
     * @since 2017.03.21
     */
    public static boolean validate(String input) {
        input = input.trim();
        boolean ret = new JsonValidator().validate(input);
        return ret;
    }

}
