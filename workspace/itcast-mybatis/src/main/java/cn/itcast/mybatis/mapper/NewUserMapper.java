package cn.itcast.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;

import cn.itcast.mybatis.pojo.User;

public interface NewUserMapper extends Mapper<User> {

	/**
	 * 分页查询
	 * 
	 * @param map
	 *            有两条数据，第一条数据的key是start 第二条数据的key是rows
	 * @return
	 */
	public List<User> queryUserByPage(Map<String, Object> map);

}
