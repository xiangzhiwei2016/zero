
/**
 * @author xiangzhiwei
 * 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 */
package com.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class MyJob implements Job{
	private static final Logger logger = LoggerFactory.getLogger(MyJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Object obj = context.getMergedJobDataMap().get("xzw");
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(obj.toString());
	}
} 