package com.atguigu.crm.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class testSpringmvc
{
	
	@RequestMapping(value="testSpringmvc",method=RequestMethod.GET)
	public String testSpringmvc1()
	{
		System.out.println("nihao");
		return "ok";
	}

}
