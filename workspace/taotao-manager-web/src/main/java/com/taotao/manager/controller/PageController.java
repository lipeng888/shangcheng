package com.taotao.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class PageController {

	// http://127.0.0.1:8080/rest/page/index
	/**
	 * 通用页面跳转方法
	 * 
	 * @param pageName
	 * @return
	 */
	@RequestMapping("{pageName}")
	// @ResponseBody 不能加
	public String toPage(@PathVariable String pageName) {

		return pageName;
	}

}
