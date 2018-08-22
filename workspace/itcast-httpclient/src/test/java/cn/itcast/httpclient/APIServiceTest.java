package cn.itcast.httpclient;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class APIServiceTest {
	private APIService apiService;

	@Before
	public void init() {
		this.apiService = new APIService();
	}

	// 查询
	@Test
	public void testQueryItemById() throws Exception {
		// http://manager.taotao.com/rest/item/interface/40
		// 声明查询接口地址
		String url = "http://manager.taotao.com/rest/item/interface/40";

		HttpResult httpResult = this.apiService.doGet(url);

		System.out.println(httpResult.getCode());
		System.out.println(httpResult.getBody());

	}

	// 新增
	@Test
	public void testSaveItem() throws Exception {
		// http://manager.taotao.com/rest/item/interface/40
		// 声明查询接口地址
		String url = "http://manager.taotao.com/rest/item/interface";

		Map<String, Object> map = new HashMap<String, Object>();
		// title=RESTful 接口测试 新增商品&price=10000&num=12&cid=33&status=1
		map.put("title", "APIServie 接口测试 新增商品&");
		map.put("price", 10000);
		map.put("num", 12);
		map.put("cid", 33);
		map.put("status", 1);

		HttpResult httpResult = this.apiService.doPost(url, map);

		System.out.println(httpResult.getCode());
		System.out.println(httpResult.getBody());

	}

	// 修改
	@Test
	public void testUpdateItem() throws Exception {
		// http://manager.taotao.com/rest/item/interface/40
		// 声明查询接口地址
		String url = "http://manager.taotao.com/rest/item/interface";

		Map<String, Object> map = new HashMap<String, Object>();
		// title=RESTful 接口测试 新增商品&price=10000&num=12&cid=33&status=1
		map.put("title", "APIServie 接口测试 修改商品&");
		map.put("id", 44);

		HttpResult httpResult = this.apiService.doPut(url, map);

		System.out.println(httpResult.getCode());

	}

	// 删除
	@Test
	public void testDeleteItemById() throws Exception {
		// http://manager.taotao.com/rest/item/interface/44
		// 声明查询接口地址
		String url = "http://manager.taotao.com/rest/item/interface/44";

		HttpResult httpResult = this.apiService.doDelete(url, null);

		System.out.println(httpResult.getCode());

	}

}
