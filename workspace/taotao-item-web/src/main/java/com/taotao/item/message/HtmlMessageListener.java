package com.taotao.item.message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

public class HtmlMessageListener implements MessageListener {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public void onMessage(Message message) {
		// 判断消息的类型是否是TextMessage
		if (message instanceof TextMessage) {
			// 如果是TextMessage，强转
			TextMessage textMessage = (TextMessage) message;

			try {
				// 获取消息内容{type：save，itemId：123}
				String json = textMessage.getText();

				// 判断json数据不为空
				if (StringUtils.isNotBlank(json)) {
					// 直接解析json数据
					JsonNode jsonNode = MAPPER.readTree(json);

					// 获取操作符type
					String type = jsonNode.get("type").asText();
					// 获取商品id
					long itemId = jsonNode.get("itemId").asLong();

					// 根据消息内容生成静态页面
					if ("save".equals(type)) {
						// 如果是新增，根据商品id创建静态页面
						this.genHtml(itemId);
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemDescService itemDescService;

	// 模板+数据=静态页面
	private void genHtml(long itemId) throws Exception {
		// 获取FreeMarker的核心对象
		Configuration cfg = this.freeMarkerConfigurer.getConfiguration();

		// 使用核心对象获取模板，参数是模板名称
		Template template = cfg.getTemplate("item.ftl");

		// 声明模型数据
		Map<String, Object> root = new HashMap<>();

		// 使用商品服务，根据商品id查询商品数据
		Item item = this.itemService.queryById(itemId);
		ItemDesc itemDesc = this.itemDescService.queryById(itemId);

		// 把商品基础数据放到模型数据root中
		root.put("item", item);
		// 把商品描述数据放到模型数据root中
		root.put("itemDesc", itemDesc);

		// 声明输出对象
		Writer out = new FileWriter(new File("C:/0407/02taotao/html/item/" + itemId + ".html"));

		// 使用模板输出静态页面，需要两个参数，一个是模型数据，一个是输出对象Writer
		template.process(root, out);

	}

}
