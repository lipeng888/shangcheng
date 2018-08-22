package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.taotao.manager.mapper.OrderItemMapper;
import com.taotao.manager.mapper.OrderMapper;
import com.taotao.manager.mapper.OrderShippingMapper;
import com.taotao.manager.pojo.Order;
import com.taotao.manager.pojo.OrderItem;
import com.taotao.manager.pojo.OrderShipping;
import com.taotao.order.redis.RedisUtils;
import com.taotao.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${TAOTAO_ORDER_INCR}")
	private String TAOTAO_ORDER_INCR;

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderItemMapper orderItemMapper;

	@Autowired
	private OrderShippingMapper orderShippingMapper;

	@Override
	public String saveOrder(Order order) {
		// 创建订单id
		// 需求：唯一的，可读性高，不能太长
		// 实现方式：用户id+redis唯一数
		String orderId = "" + order.getUserId() + this.redisUtils.incr(this.TAOTAO_ORDER_INCR);

		// 保存订单基础数据
		order.setOrderId(orderId);
		order.setStatus(1);
		order.setCreateTime(new Date());
		this.orderMapper.insert(order);

		// 保存订单商品数据
		// 获取订单商品
		List<OrderItem> orderItems = order.getOrderItems();
		// 遍历订单商品，进行保存
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			this.orderItemMapper.insert(orderItem);
		}

		// 保存订单物流数据
		// 获取订单物流
		OrderShipping orderShipping = order.getOrderShipping();
		// 保存订单物流数据
		orderShipping.setOrderId(orderId);
		this.orderShippingMapper.insert(orderShipping);

		// 返回订单id
		return orderId;
	}

	@Override
	public Order queryOrderById(String orderId) {
		// 根据订单id查询订单基础数据
		Order order = this.orderMapper.selectByPrimaryKey(orderId);

		// 根据订单id查询订单商品数据
		// 设置查询条件
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(orderId);
		// 执行查询
		List<OrderItem> orderItems = this.orderItemMapper.select(orderItem);

		// 根据订单id查询订单物流数据
		OrderShipping orderShipping = this.orderShippingMapper.selectByPrimaryKey(orderId);

		// 把查询到的订单商品数据设置到订单中
		order.setOrderItems(orderItems);
		// 把查询到的订单物流数据设置到订单中
		order.setOrderShipping(orderShipping);

		return order;
	}

	@Override
	public void cleanOrder() {
		// 声明修改条件的example，什么样的数据要修改
		Example example = new Example(Order.class);
		Criteria criteria = example.createCriteria();
		// 无效订单是什么条件
		// 订单的支付类型是在线支付payment_type=1
		criteria.andEqualTo("paymentType", 1);
		// 订单状态是未支付status=1
		criteria.andEqualTo("status", 1);
		// 订单的创建时间是两天或两天以前create_time
		criteria.andLessThanOrEqualTo("createTime", new DateTime().minusDays(2).toDate());

		// 声明订单修改成什么样
		// 创建订单对象
		Order order = new Order();
		// 修改订单的状态为交易关闭status=6
		order.setStatus(6);
		// 修改交易关闭的时间为当前时间close_time
		order.setCloseTime(new Date());

		// 根据条件修改数据，也就是根据条件处理无效订单
		// 第一个参数就是怎么处理无效订单，把数据修改成神马样
		// 第二个参数是哪些订单需要清理,什么样的数据需要修改
		this.orderMapper.updateByExampleSelective(order, example);
	}

}
