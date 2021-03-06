package com.atguigu.crm.handler;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.crm.service.UserService;
import com.atuigu.crm.entity.User;

@RequestMapping("/user")
@Controller
public class UserHandler
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	/**
	 * 1. 关于转发和重定向.
	 * 1). 若需要显示数据表中的信息到页面上, 几乎都使用转发.
	 * 2). 若提交表单, 要尽量使用重定向, 因为重定向就没有表单重复提交的问题.
	 * 
	 * 2. 重定向后如何在页面上得到 handler(或 Action)中的消息. 
	 * 1). 不能得到 request 中加入的消息。
	 * 2). 但在 SpringMVC 中可以得到
	 * 
	 * 3. 在 SpringMVC 中, 重定向时如何得到 handler 中的消息 ?
	 * 1). 不能使用 Map, 而需要使用 RedirectAttributes, 且调用其 addFlashAttribute 方法
	 * 2). 单纯使用 redirect:目标页面. 不起作用. 必须重定向后转由 SpringMVC 进行处理. 
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam(value="username",required=false) String username,
			@RequestParam(value="password",required=false) String password , HttpSession session,RedirectAttributes attribute,
							Locale locale)
	{
		//1. 可以手工完成对 username 和 password 的简单验证
		
		
		//2. 调用 Service 完成登录操作
		User user = userService.login(username, password);
		if(user == null )
		{
			attribute.addFlashAttribute("message", messageSource.getMessage("error.user.login", null, locale));
			return "redirect:/index";
			
		}
		
		session.setAttribute("user", user);
		return "home/success";
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String login(){
			return "index";
	}
}
