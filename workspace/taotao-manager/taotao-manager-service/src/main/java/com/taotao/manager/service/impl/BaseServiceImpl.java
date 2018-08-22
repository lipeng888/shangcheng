package com.taotao.manager.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.taotao.manager.pojo.BasePojo;
import com.taotao.manager.service.BaseService;

//@Service 不能加注解，不能创建实例
public class BaseServiceImpl<T extends BasePojo> implements BaseService<T> {
	// 这里使用的是spring4的新特性：泛型注入
	@Autowired
	private Mapper<T> mapper;

	private Class<T> clazz;

	public BaseServiceImpl() {
		// 获取父类的type
		Type type = this.getClass().getGenericSuperclass();

		// 强转获取的父类的type
		ParameterizedType ptype = (ParameterizedType) type;

		// 获取父类的泛型class
		this.clazz = (Class<T>) ptype.getActualTypeArguments()[0];
	}

	@Override
	public T queryById(Long id) {
		T t = this.mapper.selectByPrimaryKey(id);
		return t;
	}

	@Override
	public List<T> queryAll() {
		List<T> list = this.mapper.select(null);
		return list;
	}

	@Override
	public int queryCountByWhere(T t) {
		int count = this.mapper.selectCount(t);
		return count;
	}

	@Override
	public List<T> queryListByWhere(T t) {
		List<T> list = this.mapper.select(t);
		return list;
	}

	@Override
	public List<T> queryByPage(Integer page, Integer rows) {
		// 设置分页数据
		PageHelper.startPage(page, rows);

		// 执行查询
		List<T> list = this.mapper.select(null);

		return list;
	}

	@Override
	public T queryOne(T t) {
		T result = this.mapper.selectOne(t);
		return result;
	}

	@Override
	public void save(T t) {
		// 判断是否设置了新增时间
		if (t.getCreated() == null) {
			// 没有设置新增时间，这里进行设置
			t.setCreated(new Date());
			t.setUpdated(t.getCreated());
		} else if (t.getUpdated() == null) {
			// 只设置了新增时间，没有设置更新时间,设置更新时间
			t.setUpdated(t.getCreated());
		}

		this.mapper.insert(t);
	}

	@Override
	public void saveSelective(T t) {
		// 判断是否设置了新增时间
		if (t.getCreated() == null) {
			// 没有设置新增时间，这里进行设置
			t.setCreated(new Date());
			t.setUpdated(t.getCreated());
		} else if (t.getUpdated() == null) {
			// 只设置了新增时间，没有设置更新时间,设置更新时间
			t.setUpdated(t.getCreated());
		}

		this.mapper.insertSelective(t);
	}

	@Override
	public void updateById(T t) {
		t.setUpdated(new Date());
		this.mapper.updateByPrimaryKey(t);
	}

	@Override
	public void updateByIdSelective(T t) {
		t.setUpdated(new Date());
		this.mapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public void deleteById(Long id) {
		this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByIds(List<Object> ids) {
		// 创建example
		Example example = new Example(this.clazz);

		// 创建条件对象
		Criteria criteria = example.createCriteria();
		// 设置条件
		criteria.andIn("id", ids);

		this.mapper.deleteByExample(example);
	}

}
