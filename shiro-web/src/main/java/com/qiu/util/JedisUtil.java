package com.qiu.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;
    //获得连接的方法
    private Jedis getResource(){
        return jedisPool.getResource();
    }
    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getResource();
        try{
            jedis.set(key,value);//设定key，value值
            return value;
        }finally {
            jedis.close();
        }
    }

    public void expire(byte[] key, int i) {
        Jedis jedis = getResource();
        try{
            jedis.expire(key,i);//设置超时时间
        }finally {
            jedis.close();
        }
    }
    //创建一个获取值的方法
    public byte[] get(byte[] key) {
        Jedis jedis = getResource();
        try{
           return jedis.get(key);
        }finally {
            jedis.close();
        }
    }

    public void del(byte[] key) {
        Jedis jedis = getResource();
        try{
            jedis.del(key);//
        }finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String sHIRO_SESSION_PREFIX) {
        Jedis jedis = getResource();
        try{
            return jedis.keys((sHIRO_SESSION_PREFIX + "*").getBytes());//
        }finally {
            jedis.close();
        }
    }
}
