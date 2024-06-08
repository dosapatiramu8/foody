package com.foody.notification.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import reactor.core.publisher.Sinks;

import java.io.*;

public class RedisObjectSerializer implements RedisSerializer<Object> {

    private final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return new byte[0];
        }
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream)) {
            objectOutputStream.writeObject(o);
            return byteStream.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Cannot serialize object", e);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteStream)) {
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException("Cannot deserialize object", e);
        }
    }
}
