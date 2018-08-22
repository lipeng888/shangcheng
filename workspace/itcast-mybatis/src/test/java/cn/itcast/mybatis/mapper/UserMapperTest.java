package cn.itcast.mybatis.mapper;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.mybatis.pojo.User;

public class UserMapperTest {

	private UserMapper userMapper;

	@Before
	public void setUp() throws Exception {
		// 创建Builder
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		// 加载配置文件
		InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
		// 创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
		// 打开会话,参数是Boolean，如果为true表示自动提交
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		// 使用SqlSession获取UserMapper
		this.userMapper = sqlSession.getMapper(UserMapper.class);
	}

	@Test
	public void testQueryUsreByName() {
		List<User> list = this.userMapper.queryUsreByName("张");

		for (User user : list) {
			System.out.println(user);
		}
	}

	@Test
	public void testSaveUser() {

		User user = new User();

		user.setUserName("sunyuan2");
		user.setName("孙媛");
		user.setAge(18);

		this.userMapper.saveUser(user);

		System.out.println(user);
	}

	@Test
	public void testUpdateUserById() {

		User user = new User();
		user.setId(11l);
		user.setName("孙媛媛");
		user.setAge(30);

		this.userMapper.updateUserById(user);
	}

	@Test
	public void testDeleteById() {
		this.userMapper.deleteById(11l);
	}

}
