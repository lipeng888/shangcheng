package com.taotao.cart.service;

import java.util.List;

import com.taotao.manager.pojo.Cart;

public interface CartService {

	/**
	 * 添加商品到购物车
	 * 
	 * @param userId
	 * @param itemId
	 * @param num
	 */
	void addItemByCart(Long userId, Long itemId, Integer num);

	/**
	 * 根据用户id查询购物车
	 * 
	 * @param userId
	 * @return
	 */
	List<Cart> queryCartByUserId(Long userId);

	/**
	 * 修改购物车中的商品数量
	 * 
	 * @param userId
	 * @param itemId
	 * @param num
	 */
	void updateItemByCart(Long userId, Long itemId, Integer num);

	/**删除购物车中的商品
	 * @param userId
	 * @param itemId
	 */
	void delteItemByCart(Long userId, Long itemId);

}
