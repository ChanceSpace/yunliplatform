package com.yajun.job.repository;

import com.yajun.job.domain.YunLiJob;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-11-8
 * Time: 下午5:21
 */
@Service("jobDao")
public class JobDaoImpl extends HibernateEntityObjectDao implements JobDao {

    @Override
    public boolean findYunLiJobExist(String jobId) {
        List<YunLiJob> jobs = getHibernateTemplate().find("from YunLiJob y where y.jobId = ?", new Object[]{jobId});
        if (jobs.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public List<YunLiJob> findNotExecuteYunLiJob(int limit) {
        StringBuilder builder = new StringBuilder();
        builder.append("from YunLiJob v where v.jobStatus = 'CREATE' and v.tryTimes < 3 order by createTime asc");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

        query.setFirstResult(0);
        query.setMaxResults(limit);

        List<YunLiJob> jobs = query.list();
        return jobs;
    }
}
