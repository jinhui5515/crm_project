package com.atguigu.crm.test;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job
{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		System.out.println("do my job at " + new Date() );
	}
	
}
