package com.yajun.job.domain;

import com.yajun.green.common.domain.EntityBase;
import org.joda.time.DateTime;

/**
 * User: Jack Wang
 * Date: 17-11-8
 * Time: 下午3:05
 */
public class YunLiJob extends EntityBase {

    //通过自己系统和业务ID生成JOBID
    private String jobId;

    //任务状态和时间相关
    private DateTime createTime;
    private DateTime finishTime;
    //CREATE, FINISH
    private String jobStatus;

    //任务执行的细节
    private String requestHost;
    private String requestMethod;
    private String requestBody;

    //一个任务最多try三次
    private int tryTimes;

    public YunLiJob() {
    }

    public YunLiJob(String jobId, String requestHost, String requestMethod, String requestBody) {
        this.jobId = jobId;
        this.requestHost = requestHost;
        this.requestMethod = requestMethod;
        this.requestBody = requestBody;

        this.createTime = new DateTime();
        this.finishTime = new DateTime();
        this.jobStatus = "CREATE";
        this.tryTimes = 0;
    }

    public void finishJob() {
        this.finishTime = new DateTime();
        this.jobStatus = "FINISH";
        this.tryTimes = 0;
    }

    public void failJob() {
        this.tryTimes = this.tryTimes + 1;
    }


    /*************************GETTER/SETER**************************/

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public DateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(DateTime finishTime) {
        this.finishTime = finishTime;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestHost() {
        return requestHost;
    }

    public void setRequestHost(String requestHost) {
        this.requestHost = requestHost;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public int getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(int tryTimes) {
        this.tryTimes = tryTimes;
    }
}
