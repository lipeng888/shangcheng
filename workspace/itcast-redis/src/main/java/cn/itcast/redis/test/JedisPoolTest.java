package cn.itcast.redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolTest {

	public static void main(String[] args) {
		// 创建JedisPool连接池
		JedisPool jedisPool = new JedisPool("192.168.37.161", 6379);

		// 从连接池获取jedis连接对象
		Jedis jedis = jedisPool.getResource();

		// 使用连接对象操作redis
		String key = "jedisPool";
		String setResult = jedis.set(key, "hello jedisPool");
		System.out.println(setResult);

		String getResult = jedis.get(key);
		System.out.println(getResult);

		// 释放资源
		// 关闭jedis，在项目开发中，必须要写。这里的关闭jedis是把连接还给连接池
		jedis.close();
		// 关闭jedisPool，在整个web应用停止的时候在关闭
		jedisPool.close();
	}

}
