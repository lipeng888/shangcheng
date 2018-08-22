package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.service.TestService;

@Controller
@RequestMapping("test")
public class TestController {

	@Autowired
	private TestService testService;

	// http://127.0.0.1/rest/test/date
	/**
	 * 查询数据库时间
	 * 
	 * @return
	 */
	@RequestMapping("date")
	@ResponseBody
	public String queryDate() {

		System.out.println("谁在乱提交，冲了，我重新写");

		String date = this.testService.queryDate();
		return date;
	}

}
