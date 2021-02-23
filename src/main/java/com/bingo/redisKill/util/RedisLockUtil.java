package com.bingo.redisKill.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RedisLockUtil {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	// 获取尝试时间
	private final long EXPIRE_TIME = 5 * 10000;

	/**
	 * Redis加锁的操作
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public synchronized Boolean tryLock(String key, String value) {
		if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
			// 对应setnx命令，可以成功设置,也就是key不存在，获得锁成功
			System.err.println("=========||============" + true);
			return true;
		}
		// 设置失败，获得锁失败
		// 判断锁超时 - 防止原来的操作异常，没有运行解锁操作 ，防止死锁
		String currentValue = stringRedisTemplate.opsForValue().get(key);
		// 上个占有者超时
		if (!StringUtils.isEmpty(currentValue) && Long.valueOf(currentValue) < System.currentTimeMillis()) {
			// 获取上一个锁的时间 如果高并发的情况可能会出现已经被修改的问题 所以多一次判断保证线程的安全
			String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
			System.err.println("=====================" + oldValue.equals(currentValue));
			if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Redis解锁的操作
	 *
	 * @param key
	 * @param value
	 */
	public synchronized void unlock(String key, String value) {
		String currentValue = stringRedisTemplate.opsForValue().get(key);
		try {
			if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
				stringRedisTemplate.opsForValue().getOperations().delete(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
