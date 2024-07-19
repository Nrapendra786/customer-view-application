package com.optile.task.listeners;

import com.optile.task.enums.JobStatusEnum;
import com.optile.task.services.JobStatusService;
import com.optile.task.utils.SpringUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by NrapendraKumar.
 */

public class SchedulerGlobalJobListener implements JobListener {

    private String name = this.getClass().getName();

    public void setGlobalListenerName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
      //  JobStatusService jobStatusService = jobStatusService();
        Map<String,JobStatusEnum> map = JobStatusService.jobStatusMap;
        map.put(jobName, JobStatusEnum.QUEUED);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        Map<String,JobStatusEnum> map = JobStatusService.jobStatusMap;
        map.put(jobName, JobStatusEnum.FAILED);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        Map<String,JobStatusEnum> map = JobStatusService.jobStatusMap;
        map.put(jobName, JobStatusEnum.SUCCESS);
    }

    public JobStatusService jobStatusService(){
        return SpringUtil.ctx.getBean(JobStatusService.class);
    }
}
