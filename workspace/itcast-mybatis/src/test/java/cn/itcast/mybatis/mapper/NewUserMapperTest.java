package cn.itcast.mybatis.mapper;

import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.mybatis.pojo.User;

public class NewUserMapperTest {

	private NewUserMapper newUserMapper;

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
		this.newUserMapper = sqlSession.getMapper(NewUserMapper.class);
	}

	@Test
	public void testSelectOne() {
		User param = new User();
		param.setUserName("sunyuan");
		// param.setSex(2);

		User user = this.newUserMapper.selectOne(param);

		System.out.println(user);

	}

	@Test
	public void testSelect() {
		User user = new User();
		user.setSex(2);

		List<User> list = this.newUserMapper.select(user);

		for (User u : list) {
			System.out.println(u);
		}
	}

	@Test
	public void testSelectCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByPrimaryKey() {
		User user = this.newUserMapper.selectByPrimaryKey(2l);
		System.out.println(user);
	}

	// 新增，不忽略空字段
	@Test
	public void testInsert() {
		User user = new User();
		user.setUserName("zhaoxin");
		user.setName("菊花信");
		user.setAge(18);

		this.newUserMapper.insert(user);

		System.out.println(user);

	}

	// 新增，忽略空字段
	@Test
	public void testInsertSelective() {
		User user = new User();
		user.setUserName("gailun");
		user.setName("草丛伦");
		user.setAge(18);

		this.newUserMapper.insertSelective(user);

		System.out.println(user);
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByPrimaryKey() {
		fail("Not yet implemented");
	}

	// 更新，不忽略空字段
	@Test
	public void testUpdateByPrimaryKey() {
		User user = new User();
		user.setId(13l);
		user.setUserName("zhaoxin1");

		this.newUserMapper.updateByPrimaryKey(user);
	}

	// 更新，忽略空字段
	@Test
	public void testUpdateByPrimaryKeySelective() {
		User user = new User();
		user.setId(14l);
		user.setUserName("gailun1");

		this.newUserMapper.updateByPrimaryKeySelective(user);
	}

	// --------------测试多条件-------------
	@Test
	public void testSelectCountByExample() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByExample() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByExample() {
		// 创建example
		Example example = new Example(User.class);
		// 使用example创建设置条件的对象
		Criteria criteria = example.createCriteria();

		// 第一个参数是查询的条件是哪个，pojo的属性名，第二个参数查询的数据
		// 查询条件是id为1,3,5,7
		List<Object> ids = new ArrayList<>();
		ids.add(1);
		ids.add(3);
		ids.add(5);
		ids.add(7);

		// 设置条件
		criteria.andIn("id", ids);

		// 执行查询
		List<User> list = this.newUserMapper.selectByExample(example);

		for (User user : list) {
			System.out.println(user);
		}
	}

	// 根据条件更新，忽略空参数
	@Test
	public void testUpdateByExampleSelective() {
		// 创建example
		Example example = new Example(User.class);
		// 使用example创建设置条件的对象
		Criteria criteria = example.createCriteria();

		// 第一个参数是查询的条件是哪个，pojo的属性名，第二个参数查询的数据
		// 查询条件是id为1,3,5,7
		List<Object> ids = new ArrayList<>();
		ids.add(1);
		ids.add(3);
		ids.add(5);
		ids.add(7);

		// 设置条件
		criteria.andIn("id", ids);

		// 声明修改的数据
		User user = new User();
		user.setPassword("456");

		// 第一个参数是把数据修改成神马样
		// 第二个参数是把什么样的数据进行修改
		this.newUserMapper.updateByExampleSelective(user, example);
	}

	// 根据条件更新，不忽略空参数
	@Test
	public void testUpdateByExample() {
		fail("Not yet implemented");
	}

	// 根据条件更新，不忽略空参数
	@Test
	public void testQueryByPage() {
		Map<String, Object> map = new HashMap<>();

		map.put("start", 0);
		map.put("rows", 2);

		List<User> list = this.newUserMapper.queryUserByPage(map);
		for (User user : list) {
			System.out.println(user);
		}
	}

	// 根据条件更新，不忽略空参数
	@Test
	public void testQueryByPage1() {
		// 设置分页数据，第一个参数是从哪一页开始查，第二个参数是每页显示几条数据
		PageHelper.startPage(2, 5);

		List<User> list = this.newUserMapper.select(null);
		for (User user : list) {
			System.out.println(user);
		}

		PageInfo<User> pageInfo = new PageInfo<>(list);

		System.out.println("总记录数：" + pageInfo.getTotal());
		System.out.println("总页数：" + pageInfo.getPages());
	}

}
