package com.atguigu.crm.test;

import java.text.ParseException;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

public class QuartzTest2
{	
	
	public static void main(String[] args) throws SchedulerException, ParseException
	{

//		实现 Job 接口，可使 Java 类变为可调度的任务
//		创建描述 Job 的 JobDetail 对象
		JobDetailImpl jobDetail = new JobDetailImpl();
		jobDetail.setName("myjob");
		jobDetail.setGroup("myGroup");
		jobDetail.setJobClass(MyJob.class);
		
		
		//创建CronTrigger对象
		CronTriggerImpl trigger = new CronTriggerImpl() ;
		trigger.setName("myTrigger");
		trigger.setGroup("triggerGroup");
		
		//设置触发 Job 执行的时间规则
		trigger.setCronExpression("0/3 57 1 2 ? 2015");
		
		
/*//		创建 SimpleTrigger 对象
		SimpleTriggerImpl trigger = new SimpleTriggerImpl();
		trigger.setName("myTrigger");
		trigger.setGroup("triggerGroup");
		
//		设置触发 Job 执行的时间规则
		trigger.setStartTime(new Date());
		trigger.setRepeatInterval(1000);
		trigger.setRepeatCount(10);
		*/
//		通过 SchedulerFactory 获取 Scheduler 对象
		
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//		向 SchedulerFactory 中注册 JobDetail 和 Trigger
		scheduler.scheduleJob(jobDetail, trigger);
//		启动调度任务
		scheduler.start();
	}
	
}














