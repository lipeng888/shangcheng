package cn.itcast.httpclient;

public class HttpResult {

	// 响应状态码
	private Integer code;

	// 响应体的内容
	private String body;

	public HttpResult() {
		super();
	}

	public HttpResult(Integer code, String body) {
		super();
		this.code = code;
		this.body = body;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
