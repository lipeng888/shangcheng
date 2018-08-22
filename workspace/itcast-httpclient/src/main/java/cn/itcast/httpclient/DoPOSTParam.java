package cn.itcast.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class DoPOSTParam {

	public static void main(String[] args) throws Exception {

		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建http POST请求
		HttpPost httpPost = new HttpPost("http://manager.taotao.com/rest/item/interface");

		// 设置2个post参数，title=RESTful 接口测试 新增商品&price=10000&num=12&cid=33&status=1
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
		parameters.add(new BasicNameValuePair("title", "HttpClient 接口调用测试  新增商品"));
		parameters.add(new BasicNameValuePair("price", "100001"));
		parameters.add(new BasicNameValuePair("num", "13"));
		parameters.add(new BasicNameValuePair("cid", "22"));
		parameters.add(new BasicNameValuePair("status", "1"));
		// 构造一个form表单式的实体
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
		// 将请求实体设置到httpPost对象中
		httpPost.setEntity(formEntity);

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpclient.execute(httpPost);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(response.getEntity(), "UTF-8");
				System.out.println(content);
			}
		} finally {
			if (response != null) {
				response.close();
			}
			httpclient.close();
		}

	}

}
