package cn.itcast.httpclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class APIService {

	private CloseableHttpClient httpClient;

	public APIService() {
		// 创建HttpClient，打开浏览器
		this.httpClient = HttpClients.createDefault();
	}

	// 带参数的GET请求
	/**
	 * 带参数的GET请求
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HttpResult doGet(String url, Map<String, Object> map) throws Exception {

		// 声明URIBuilder
		URIBuilder uriBuilder = new URIBuilder(url);

		// 判断参数map不为空
		if (map != null) {
			// 如果不为空,遍历Map
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				// 设置参数
				uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
			}
		}

		// 创建httpGet，输入请求地址
		HttpGet httpGet = new HttpGet(uriBuilder.build());

		// 执行请求，敲回车
		CloseableHttpResponse response = this.httpClient.execute(httpGet);

		// 解析响应结果,浏览器解析展示数据
		// 封装httpResult返回对象,先声明返回对象
		HttpResult httpResult = null;

		// 状态码
		// response.getStatusLine().getStatusCode()
		// 响应体,必须保证第一个参数Entity不为空，如果为空，会报错
		// EntityUtils.toString(response.getEntity(), "UTF-8");

		// 判断响应体是否不为空
		if (response.getEntity() != null) {
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} else {
			// 如果为空，响应体设置为null
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(), null);
		}

		return httpResult;

	}

	// 不带参数的GET请求
	/**
	 * 不带参数的GET请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpResult doGet(String url) throws Exception {
		HttpResult httpResult = this.doGet(url, null);
		return httpResult;
	}

	// 带参数的POST请求
	/**
	 * 带参数的POST请求
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPost(String url, Map<String, Object> map) throws Exception {
		// 创建httpPost请求
		HttpPost httpPost = new HttpPost(url);

		// 判断参数不为空
		if (map != null) {
			// 声明存放参数的list容器
			List<NameValuePair> param = new ArrayList<NameValuePair>();

			// 如果不为空，进行遍历
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				// 设置请求表单参数
				param.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}

			// 创建表单的实体对象
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(param, "UTF-8");

			// 设置表单对象到httpPost中
			httpPost.setEntity(formEntity);

		}

		// 使用HttpClient执行post请求，返回response
		CloseableHttpResponse response = this.httpClient.execute(httpPost);

		// 解析返回值response，封装返回对象httpResult
		// 声明httpResult返回对象
		HttpResult httpResult = null;

		if (response.getEntity() != null) {
			// 直接设置响应体内容
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} else {
			// 响应体内容为null
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(), null);
		}

		return httpResult;

	}

	// 不带参数的POST请求
	/**
	 * 不带参数的POST请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPost(String url) throws Exception {
		HttpResult httpResult = this.doPost(url, null);
		return httpResult;
	}

	// 带参数的PUT请求
	/**
	 * 带参数的PUT请求
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPut(String url, Map<String, Object> map) throws Exception {
		// 创建httpPost请求
		HttpPut httpPut = new HttpPut(url);

		// 判断参数不为空
		if (map != null) {
			// 声明存放参数的list容器
			List<NameValuePair> param = new ArrayList<NameValuePair>();

			// 如果不为空，进行遍历
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				// 设置请求表单参数
				param.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}

			// 创建表单的实体对象
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(param, "UTF-8");

			// 设置表单对象到httpPost中
			httpPut.setEntity(formEntity);

		}

		// 使用HttpClient执行post请求，返回response
		CloseableHttpResponse response = this.httpClient.execute(httpPut);

		// 解析返回值response，封装返回对象httpResult
		// 声明httpResult返回对象
		HttpResult httpResult = null;

		if (response.getEntity() != null) {
			// 直接设置响应体内容
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} else {
			// 响应体内容为null
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(), null);
		}

		return httpResult;

	}

	// 带参数的DELETE请求
	public HttpResult doDelete(String url, Map<String, Object> map) throws Exception {

		// 声明URIBuilder
		URIBuilder uriBuilder = new URIBuilder(url);

		// 判断参数map不为空
		if (map != null) {
			// 如果不为空,遍历Map
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				// 设置参数
				uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
			}
		}

		// 创建httpGet，输入请求地址
		HttpDelete httpDelete = new HttpDelete(uriBuilder.build());

		// 执行请求，敲回车
		CloseableHttpResponse response = this.httpClient.execute(httpDelete);

		// 解析响应结果,浏览器解析展示数据
		// 封装httpResult返回对象,先声明返回对象
		HttpResult httpResult = null;

		// 状态码
		// response.getStatusLine().getStatusCode()
		// 响应体,必须保证第一个参数Entity不为空，如果为空，会报错
		// EntityUtils.toString(response.getEntity(), "UTF-8");

		// 判断响应体是否不为空
		if (response.getEntity() != null) {
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} else {
			// 如果为空，响应体设置为null
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(), null);
		}

		return httpResult;

	

	}

}
