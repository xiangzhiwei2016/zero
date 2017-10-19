package com.quartz;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Named
@Singleton
public class JobUtils {
	private static final Logger logger = Logger.getLogger(JobUtils.class);
	
	@Inject
	public SchedulerFactoryBean schedulerFactoryBean;
	
	@PostConstruct
	public void addJob() throws SchedulerException{
		// 
		logger.info("定时任务初始化。。。。。。。。。。。。。。。。。。。");
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		logger.info(scheduler+".............添加");
		// 触发器
		TriggerKey triggerKey = TriggerKey.triggerKey("test", "test");
		CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);
		if(null == trigger){
			// 不存在，创建个
			Class clazz = MyJob.class;
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity("test", "test").build();

			jobDetail.getJobDataMap().put("xzw", "hello,word");
			
			// cron表达（每3秒执行一次）
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/3 * * * * ?");
			trigger = TriggerBuilder.newTrigger().withIdentity("test", "test").withSchedule(scheduleBuilder).build();
			
			// 定时任务
			scheduler.scheduleJob(jobDetail, trigger);
		}else{
			// 存在，则更新对应的定时设置
			String cron = "0/8 * * * * ?";
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
		
	}
	
	public static void main(String[] args) {
		try {
			new JobUtils().addJob();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
