package com.yajun.job.scheduler;

import com.yajun.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Jack Wang
 * Date: 17-11-8
 * Time: 下午9:40
 */
@Service("jobExecuteScheduler")
public class JobExecuteScheduler {

    @Autowired
    private JobService jobService;

    public void update() {
        jobService.updateYunLiJobs();
    }
}
