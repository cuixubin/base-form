package com.dlshouwen.core.base.utils;

import java.util.Date;

import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dlshouwen.core.base.config.CONFIG;

/**
 * 任务调度工具类
 * @author 大连首闻科技有限公司
 * @version 2013-11-29 14:18:27
 */
public class QuartzUtils {
	
	/**
	 * 获取任务调度对象
	 * <p>如果程序中使用了QuartZ定时任务组件，可通过该方法获取任务对象，
	 * 需要在spring配置文件中定义一个类型通常为org.springframework.scheduling.quartz.SchedulerFactoryBean的bean对象且bean名称必须为schedulerFactory
	 * @return 调度对象
	 * @throws Exception 抛出全部异常
	 */
	public static Scheduler getScheduler() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CONFIG.SERVLET_CONTEXT);
		return context.getBean("schedulerFactory", Scheduler.class);
	}
	
	/**
	 * 更新调度频率
	 * <p>用于更新某触发器的调度频率，频率格式请参考QuartZ官方文档
	 * @param triggerName 触发器名称
	 * @param cronExpression 调度频率，频率格式请参考QuartZ官方文档
	 * @throws Exception 抛出全部异常
	 */
	public static void updateTriggerInterval(String triggerName, String cronExpression) throws Exception {
//		获取调度对象
		Scheduler scheduler = QuartzUtils.getScheduler();
//		获取触发器对象
		CronTriggerBean trigger = (CronTriggerBean)scheduler.getTrigger(triggerName, Scheduler.DEFAULT_GROUP);
//		设置调度频率
		trigger.setCronExpression(cronExpression);
//		重置作业
		scheduler.rescheduleJob(triggerName, Scheduler.DEFAULT_GROUP, trigger);
//		防止设置时间在当前时间点之前时，立刻执行一次的问题
		Date nextFireTime = trigger.getNextFireTime();
		Date currentTime = new Date(System.currentTimeMillis());
		if (nextFireTime.before(currentTime)) {
			nextFireTime.setTime(nextFireTime.getTime()+1);
			trigger.setNextFireTime(nextFireTime);
		}
	}
	
	/**
	 * 删除作业
	 * <p>用于删除某调度作业
	 * @param jobName 作业名称
	 * @throws Exception 抛出全部异常
	 */
	public static void deleteJob(String jobName) throws Exception {
//		获取调度对象
		Scheduler scheduler = QuartzUtils.getScheduler();
//		删除调度作业
		scheduler.deleteJob(jobName, Scheduler.DEFAULT_GROUP);
	}
	
}
