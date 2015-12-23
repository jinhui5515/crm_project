package com.atguigu.crm.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestDataSource
{
	private ApplicationContext act;
	
	{
		act = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void  test()
	{
		Object bean = act.getBean("dataSource");
		System.out.println(bean);
	}
}
