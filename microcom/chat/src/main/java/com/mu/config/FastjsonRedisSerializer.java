package com.mu.config;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author MUZUKI
 * @Classname sd
 * @Description TODO
 * @Date 2023/4/24 11:56
 */



    public class FastjsonRedisSerializer<T> implements RedisSerializer<T> {

    private final Class<T> clazz;

    public FastjsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t).getBytes();
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String str = new String(bytes);
        return JSON.parseObject(str, clazz);
    }
}