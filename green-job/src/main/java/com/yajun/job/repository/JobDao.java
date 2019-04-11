package com.yajun.job.repository;

import com.yajun.job.domain.YunLiJob;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-11-8
 * Time: 下午5:21
 */
public interface JobDao extends EntityObjectDao {

    boolean findYunLiJobExist(String jobId);

    List<YunLiJob> findNotExecuteYunLiJob(int limit);
}
