package com.zsp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * 
 * @author zhaoshiping
 * @since 2018-02-28
 */
@Service
public class RedisServicesWrapper {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 同步数据到redis上
     * 
     * @param k
     * @param v
     */
    public <K,V> void loadDataToRedisDb(K k, V v) {
        try {
       //     String value = JsonUtil.toJson(v);
           // redisTemplate.opsForValue().set((String) k, value);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 从redis上获取Key对应的值， 并转换为期望的对象 如果返回null，则表示没有数据
     * 
     * @param k
     * @param vClass
     * @return
     */
    public <K,V> V get(K k, Class<V> vClass) {
        String value = (String) redisTemplate.opsForValue().get((String) k);
        try {
      //      V v = (V) JsonUtil.fromJson(value, vClass);
          //  return v;
        } catch (Exception e) {
            log.error("",e);
        }

        return null;
    }

    /**
     * 失效keys
     * 
     * @param keys
     */
    public void expireKeys(String... keys) {
        for (String key : keys) {
            redisTemplate.expire(key, 0, TimeUnit.SECONDS);
        }
    }
}
