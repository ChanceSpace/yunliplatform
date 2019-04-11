package com.yajun.job.service;

/**
 * User: Jack Wang
 * Date: 17-11-8
 * Time: 下午5:21
 */
public interface JobService {

    /********************************作业保存相关**********************************/

    void changeYunLiJob(String jobId, String requestHost, String requestMethod, String requestBody);

    /********************************任务执行**********************************/

    /**
     * 每一分钟执行一次，一次执行10个任务
     */
    void updateYunLiJobs();
}
