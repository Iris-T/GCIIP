package cn.iris.gciip.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Iris 2022/8/6
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 缓存基本对象，Integer，String，实体类等
     * @param key 缓存键
     * @param value 缓存值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存对象，并设置有效时间
     * @param key 键
     * @param value 值
     * @param timeout 超时时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     * @param key 键
     * @param timeout 超时时间
     * @return true 成功 | false 失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     * @param key 键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true 成功 | false 失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    /**
     * 获取缓存的单个基本对象信息
     * @param key 键
     * @return 缓存数据
     */
    public <T> T getObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除缓存数据
     * @param key 键
     * @return 结果信息
     */
    public boolean delObject(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除集合对象
     * @param collection 多个对象
     * @return 影响数量
     */
    public long delObject(final Collection collection) {
        Long cnt = redisTemplate.delete(collection);
        return cnt == null ? 0 : cnt;
    }

    /**
     * 缓存List数据
     * @param key 键
     * @param dataList List数据
     * @return 影响数
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long cnt = redisTemplate.opsForList().rightPushAll(key, dataList);
        return cnt == null ? 0 : cnt;
    }

    /**
     * 获取缓存的List数据
     * @param key 键
     * @return List数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     * @param key 键
     * @param dataSet set数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations setOps = redisTemplate.boundSetOps(key);
        for (T t : dataSet) {
            setOps.add(t);
        }
        return setOps;
    }

    /**
     * 获取Set缓存
     * @param key 键
     * @return set数据
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map数据
     * @param key 键
     * @param dataMap map数据
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获取Map缓存
     * @param key 键
     * @return map数据
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 向Hash中缓存数据
     * @param key 键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的缓存数据
     * @param key 键
     * @param hKey Hash键
     * @return 缓存值
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 删除Hash中的数据
     * @param key 键
     * @param hKey Hash键
     */
    public void delCacheMapValue(final String key, final String hKey) {
        HashOperations opsForHash = redisTemplate.opsForHash();
        opsForHash.delete(key, hKey);
    }

    /**
     * 获取多个Hash缓存数据
     * @param key 键
     * @param hKeys Hash键
     * @return Hash对象列表
     */
    public <T> List<T> getMultiCacheMapValue(final String key, Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获取缓存的基本对象列表
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}
