package cn.itcast.redis.test;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterTest {

	public static void main(String[] args) {
		// 创建集群的连接对象jedisCluster
		Set<HostAndPort> nodes = new HashSet<>();

		nodes.add(new HostAndPort("192.168.37.161", 7001));
		nodes.add(new HostAndPort("192.168.37.161", 7002));
		nodes.add(new HostAndPort("192.168.37.161", 7003));
		nodes.add(new HostAndPort("192.168.37.161", 7004));
		nodes.add(new HostAndPort("192.168.37.161", 7005));
		nodes.add(new HostAndPort("192.168.37.161", 7006));

		JedisCluster jedisCluster = new JedisCluster(nodes);

		// 使用连接对象操作redis集群，使用方式和之前是一样
		String key = "jedisCluster";
		String setResult = jedisCluster.set(key, "hello jedisCluster");
		System.out.println(setResult);

		String getResult = jedisCluster.get(key);
		System.out.println(getResult);

		// 释放资源
		// 关闭jedisCluster连接对象，在项目开发的时候，使用过程中不要关闭jedisCluster
		// 因为JedisCluster已经内置了连接池
		jedisCluster.close();

	}

}
