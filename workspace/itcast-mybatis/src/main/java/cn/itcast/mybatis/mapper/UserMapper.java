package cn.itcast.mybatis.mapper;

import java.util.List;

import cn.itcast.mybatis.pojo.User;

public interface UserMapper {

	/**
	 * 根据用户名模糊查询
	 * 
	 * @param name
	 * @return
	 */
	public List<User> queryUsreByName(String name);

	/**
	 * 新增
	 * 
	 * @param user
	 */
	public void saveUser(User user);

	/**
	 * 更新
	 * 
	 * @param user
	 */
	public void updateUserById(User user);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteById(Long id);

}
