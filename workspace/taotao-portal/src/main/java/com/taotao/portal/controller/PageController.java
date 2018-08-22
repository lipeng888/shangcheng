package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("page")
public class PageController {

	// http://www.taotao.com/page/register.html
	// http://www.taotao.com/page/login.html
	/**
	 * 通用页面跳转
	 * 
	 * @param pageName
	 * @return
	 */
	@RequestMapping(value = "{pageName}")
	// @ResponseBody 不能加这个注解，加了这个注解就不走视图解析器
	public String page(Model model, @PathVariable String pageName, String url) {

		// 把用户原来的请求url返到Model中，传递给前台页面
		model.addAttribute("url", url);

		return pageName;
	}

}
