package com.taotao.order.service;

import com.taotao.manager.pojo.Order;

public interface OrderService {

	/**
	 * 创建订单
	 * 
	 * @param order
	 * @return
	 */
	String saveOrder(Order order);

	/**
	 * 根据订单id查询订单
	 * 
	 * @param orderId
	 * @return
	 */
	Order queryOrderById(String orderId);

	/**
	 * 清理无效订单
	 */
	void cleanOrder();

}
