package cn.itcast.redis.test;

import redis.clients.jedis.Jedis;

public class JedisTest {

	public static void main(String[] args) {
		// 创建jedis连接对象
		Jedis jedis = new Jedis("192.168.37.161", 6379);

		// 使用jedis操作redis，jedis的方法和redis的命令几乎一样
		String key = "jedis";
		String setResult = jedis.set(key, "hello jedis");
		System.out.println(setResult);

		String getResult = jedis.get(key);
		System.out.println(getResult);

		// 释放资源，关闭jedis
		jedis.close();
	}

}
