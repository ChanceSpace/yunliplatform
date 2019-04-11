package com.yajun.job.service;

import com.alibaba.fastjson.JSONObject;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.utils.WebUtils;
import com.yajun.green.common.web.request.HandleResponse;
import com.yajun.job.domain.YunLiJob;
import com.yajun.job.repository.JobDao;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-11-8
 * Time: 下午5:21
 */
@Service("jobService")
public class JobServiceImpl implements JobService {

    @Autowired
    private JobDao jobDao;

    /********************************作业保存相关**********************************/

    @Override
    public void changeYunLiJob(String jobId, String requestHost, String requestMethod, String requestBody) {
        boolean exist = jobDao.findYunLiJobExist(jobId);
        if (exist) {
            ApplicationLog.info(JobServiceImpl.class, "job " + jobId + " already exist in the system");
        } else {

            YunLiJob job = new YunLiJob(jobId, requestHost, requestMethod, requestBody);
            jobDao.saveOrUpdate(job);
            ApplicationLog.info(JobServiceImpl.class, "save job " + jobId + " in the system");
        }
    }

    /********************************任务执行**********************************/

    @Override
    public void updateYunLiJobs() {
        ApplicationLog.info(JobServiceImpl.class, "start job execute--------------------------");

        List<YunLiJob> jobs = jobDao.findNotExecuteYunLiJob(10);
        for (YunLiJob job : jobs) {
            String jobId = job.getJobId();

            try {
                String requestHost = job.getRequestHost();
                String requestMethod = job.getRequestMethod();
                String requestBody = job.getRequestBody();

                PostMethod postMethod = new PostMethod(requestHost);
                RequestEntity entity = new StringRequestEntity(requestBody, "text/xml", "UTF-8");
                postMethod.setRequestEntity(entity);

                String result = WebUtils.httpPostRequest(postMethod);
                ApplicationLog.info(JobServiceImpl.class, "job id " + jobId + " with result " + result);
                JSONObject response = JSONObject.parseObject(result);
                int status = response.getIntValue("status");

                if (1000 == status) {
                    job.finishJob();
                    ApplicationLog.info(JobServiceImpl.class, "finish job execute with job id " + jobId + " with code " + status);
                } else {
                    job.failJob();
                    ApplicationLog.error(JobServiceImpl.class, "error execute job with job id " + jobId + " with result " + result);
                }

            } catch (Exception e) {
                job.failJob();
                ApplicationLog.error(JobServiceImpl.class, "error execute job with job id " + jobId, e);
            }
        }

        ApplicationLog.info(JobServiceImpl.class, "end job execute-----------------------------");
    }
}
