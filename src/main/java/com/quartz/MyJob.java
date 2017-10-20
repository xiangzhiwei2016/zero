
/**
 * @author xiangzhiwei
 *
 */
package com.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Object obj = context.getMergedJobDataMap().get("xzw");
		System.out.println(obj);
	}
} 