package com.example.springbootdemo.utils;

import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeoutCacheUtil {
	/**
	 * 默认缓存时长 单位s
	 */
	private static final int DEFAULT_TIMEOUT = 1800;
	/**
	 * 默认缓存容量
	 */
	private static final int DEFAULT_SIZE = 1000;

	/**
	 * 存储数据
	 */
	private static final Map<String, Object> data;
	private static final Map<String, Object> task;

	/**
	 * 定时器 用来控制 缓存的超时时间 private static Timer timer; timer = new Timer();
	 * timer.schedule(CacheCleanTask.cacheTask(key),DEFAULT_TIMEOUT);
	 *
	 * 1)多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，
	 * 其它任务便会自动终止运行，使用ScheduledExecutorService则没有这个问题
	 * 2)Timer内部是一个线程，任务1所需的时间超过了两个任务间的间隔时会导致问题 3)Timer执行周期任务时依赖系统时间
	 */

	private static final ScheduledExecutorService executorService;

	// 初始化
	static {
		data = new ConcurrentHashMap<String, Object>(DEFAULT_SIZE);
		task = new ConcurrentHashMap<String, Object>(DEFAULT_SIZE);
		executorService = new ScheduledThreadPoolExecutor(2);
		// 定时器 调度任务，用于根据 时间 定时清除 对应key 缓存
		executorService.scheduleWithFixedDelay(new ScheduleTask(), DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, TimeUnit.SECONDS);
	}

	private static class ScheduleTask extends TimerTask {
		@Override
		public void run() {
			for (Map.Entry<String, Object> entry : task.entrySet()) {
				int second = DateUtil.getDiffSeconds((String) entry.getValue(), DateUtil.getNowTime());
				if (second >= DEFAULT_TIMEOUT) {
					remove(entry.getKey());
				}
			}
		}

	}

	/**
	 * 增加缓存 默认有效时长
	 * 
	 * @param key
	 * @param value
	 */
	public static void put(final String key, Object value) {
		data.put(key, value);
		task.put(key, DateUtil.getNowTime());
	}

	/**
	 * 获取缓存,并刷新缓存时间
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		Object value = data.get(key);
		if(value !=null) {
			refreshTask(key, value);
		}
		return value;
	}
	
	/**
	 * 刷新缓存时效
	 * @param key
	 * @param value
	 */
	public static void refreshTask(String key, Object value) {
		remove(key);
		put(key, value);
	}

	/**
	 * 获取当前缓存中 所有的key
	 * 
	 * @return
	 */
	public static Set<String> cacheKeys() {
		return data.keySet();
	}

	public static Map<String, Object> allCache() {
		return data;
	}

	/**
	 * 判断缓存是否包含key
	 * 
	 * @param key
	 * @return
	 */
	public static boolean containKey(String key) {
		return data.containsKey(key);
	}

	/**
	 * 获取当前缓存大小
	 * 
	 * @return
	 */
	public static int size() {
		return data.size();
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public static void remove(String key) {
		data.remove(key);
		task.remove(key);
	}

	/**
	 * 清空所有缓存
	 */
	public static void clear() {
		if (size() > 0) {
			data.clear();
		}
	}
}