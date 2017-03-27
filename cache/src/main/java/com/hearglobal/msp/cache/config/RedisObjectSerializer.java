package com.hearglobal.msp.cache.config;

import com.hearglobal.msp.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Redis object缓存序列号方法
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
class RedisObjectSerializer implements RedisSerializer<Object> {
    private Logger logger = LoggerFactory.getLogger(RedisObjectSerializer.class);
    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverter();
    private static final byte[] EMPTY_ARRAY = new byte[0];

    @Override
    public Object deserialize(byte[] bytes) {
        if (isEmpty(bytes)) {
            return null;
        }
        try {
            return deserializer.convert(bytes);
        } catch (Exception ex) {
            logger.error("RedisObjectSerializer deserialize bytes:{},size:{}",bytes,bytes.length);
            throw new SerializationException("Cannot deserialize", ex);
        }
    }

    @Override
    public byte[] serialize(Object object) {
        if (object == null) {
            return EMPTY_ARRAY;
        }
        try {
            byte[] bytes = serializer.convert(object);
            logger.debug("RedisObjectSerializer serialize byte[]:{},size:{}",bytes,bytes.length);
            return bytes;
        } catch (Exception ex) {
            logger.error("RedisObjectSerializer serialize object:{}", ObjectUtil.toString(object));
            return EMPTY_ARRAY;
        }
    }

    private boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }
}
