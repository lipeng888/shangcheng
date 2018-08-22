package cn.itcast.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DoPOST {

	public static void main(String[] args) throws Exception {

		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建http POST请求
		HttpPost httpPost = new HttpPost("http://www.oschina.net/");

		// 设置头信息
		// httpPost.setHeader("User-Agent",
		// "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML,
		// like Gecko) Chrome/60.0.3112.101 Safari/537.36");
		httpPost.setHeader("User-Agent", "");

		// 设置请求参数
		RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000)
				.setConnectionRequestTimeout(10 * 1000).build();
		httpPost.setConfig(config);

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
