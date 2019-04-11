package com.yajun.job.thread;

import com.yajun.job.service.JobService;
/**
 * User: Jack Wang
 * Date: 17-11-8
 * Time: 下午5:38
 */
public class JobLaunchThread implements Runnable {

    private JobService jobService;

    //请求的唯一ID，用于判断JOB避免重复提交
    private String jobId;
    //请求的URL
    private String requestHost;
    //请求的方法，POST或者GET，注意是大写哈
    private String requestMethod;
    //请求的参数
    private String requestBody;

    /**
     * 多个ID用|隔开
     */
    public JobLaunchThread(JobService jobService, String jobId, String requestHost, String requestMethod, String requestBody) {
        this.jobService = jobService;
        this.jobId = jobId;
        this.requestHost = requestHost;
        this.requestMethod = requestMethod;
        this.requestBody = requestBody;
    }

    @Override
    public void run() {
        //当前线程SLEEP 1S，保证所有数据都正常保存到数据库
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        jobService.changeYunLiJob(jobId, requestHost, requestMethod, requestBody);
    }
}
