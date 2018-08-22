package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

public class TaoResult<T> implements Serializable {

	// 数据结果集
	private List<T> rows;

	// 查询的数据总记录数
	private long total;

	public TaoResult() {
		super();
	}

	public TaoResult(List<T> rows, long total) {
		super();
		this.rows = rows;
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
