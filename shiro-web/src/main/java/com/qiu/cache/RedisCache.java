package com.qiu.cache;

import com.qiu.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

@Component
public class RedisCache<k, v> implements Cache<k, v> {

    @Resource
    private JedisUtil jedisUtil;

    //写一个前缀
    private final String CACHE_PREFIX = "qiu-cache:";

    private byte[] getKey(k k) {
        if (k instanceof String) {
            return (CACHE_PREFIX + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }

    @Override
    public v get(k k) throws CacheException {
        System.out.println("From redis中获取数据");
        byte[] value = jedisUtil.get(getKey(k));
        if (value != null) {
            return (v) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public v put(k k, v v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key, value);
        jedisUtil.expire(key, 600);
        return v;
    }

    @Override
    public v remove(k k) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = jedisUtil.get(key);
        jedisUtil.del(key);
        if (value != null) {
            return (v) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<k> keys() {
        return null;
    }

    @Override
    public Collection<v> values() {
        return null;
    }
}
