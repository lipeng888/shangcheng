package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.manager.pojo.User;
import com.taotao.portal.util.CookieUtils;
import com.taotao.sso.service.UserService;

public class OrderHandlerInterceptor implements HandlerInterceptor {

	@Value("${TT_TICKET}")
	private String TT_TICKET;

	@Autowired
	private UserService userService;

	// 在进入Controller方法之前执行，返回false，被拦截，不会进入Controller方法，返回true，放行，下一步进入Controller方法
	// 登录拦截，权限拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从cookie中获取ticket
		String ticket = CookieUtils.getCookieValue(request, this.TT_TICKET, true);

		// 判断ticket是否为空
		if (StringUtils.isBlank(ticket)) {
			// 获取用户原来的请求url
			String url = request.getRequestURL().toString();

			// 如果为空表示用户未登录
			// 跳转到登陆页面
			response.sendRedirect("http://www.taotao.com/page/login.html?url=" + url);

			// 返回false，拦截
			return false;
		}

		// 使用单点登录服务，根据ticket查询用户登录信息
		User user = this.userService.queryUserByTicket(ticket);

		// 判断user是否为空
		if (user == null) {
			// 获取用户原来的请求url
			String url = request.getRequestURL().toString();

			// 如果为空表示用户未登录
			// 跳转到登陆页面
			response.sendRedirect("http://www.taotao.com/page/login.html?url=" + url);

			// 返回false，拦截
			return false;
		}

		// 执行到这里，表示用户已登录
		// 把查询到的用户的信息放到request中，其他需要使用的就直接从request中获取即可
		request.setAttribute("user", user);

		// 返回true，放行
		return true;
	}

	// 进入Controller方法之后，并且执行完方法的逻辑，在返回ModelAndView之前执行
	// 所有页面要显示的公告数据，可以使用这个拦截器的方法统一的添加数据到ModelAndView中，所有被拦截的方法就有了公告数据
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	// 进入Controller方法之后，并且执行完方法逻辑，返回ModelAndView之后执行，最后执行
	// 日志记录，异常处理
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
