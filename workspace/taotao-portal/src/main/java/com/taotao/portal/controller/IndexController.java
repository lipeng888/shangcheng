package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.manager.service.ContentService;

@Controller
@RequestMapping("index")
public class IndexController {

	@Autowired
	private ContentService contentService;

	@Value("${TAOTAO_AD_ID}")
	private Long categoryId;

	// http://127.0.0.1:8083/index.html
	/**
	 * 首页展示
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String index(Model model) {
		// 使用内容服务，查询大广告数据
		String AD = this.contentService.queryContentByCategoryId(this.categoryId);

		// 把大广告数据放到model中，传递给前台页面
		model.addAttribute("AD", AD);

		return "index";
	}

}
