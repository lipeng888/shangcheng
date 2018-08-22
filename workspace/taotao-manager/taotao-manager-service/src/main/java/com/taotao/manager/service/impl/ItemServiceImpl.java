package com.taotao.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.mapper.ItemDescMapper;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

	// @Autowired
	// private ItemDescMapper itemDescMapper;

	@Autowired
	private ItemDescService itemDescService;

	@Override
	public void saveItem(Item item, String desc) {
		// 保存商品基础数据
		item.setStatus(1);
		super.save(item);

		// 保存商品描述
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);

		this.itemDescService.save(itemDesc);

		// 新增商品成功，发消息给mq
		// 消息内容是：操作符:type:save 。商品数据：itemId:123
		this.sendMQ("save", item.getId());
	}

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Destination destination;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 发送消息给MQ
	private void sendMQ(final String type, final Long itemId) {
		this.jmsTemplate.send(this.destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				// 创建TextMessage消息体
				TextMessage textMessage = new ActiveMQTextMessage();

				// 创建消息内容，使用json格式的数据
				// 创建封装消息的map
				Map<String, Object> map = new HashMap<>();

				// 设置消息
				// 操作符
				map.put("type", type);
				// 商品Id
				map.put("itemId", itemId);

				try {
					// 把消息转为json格式的数据
					String json = MAPPER.writeValueAsString(map);

					// 设置消息
					textMessage.setText(json);
					System.out.println("消息发送成功,消息的内容是：" + json);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return textMessage;
			}
		});
	}

	@Override
	public TaoResult<Item> queryItemByPage(Integer page, Integer rows) {
		// 设置分页参数
		PageHelper.startPage(page, rows);

		// 设置查询条件
		Item item = new Item();
		// 设置正常的商品
		item.setStatus(1);

		// 执行查询
		List<Item> list = super.queryListByWhere(item);

		// 封装返回对象
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		TaoResult<Item> taoResult = new TaoResult<>(list, pageInfo.getTotal());

		return taoResult;
	}

}
