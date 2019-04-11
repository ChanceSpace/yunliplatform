package com.yajun.green.repository.xiaoshou;

import com.yajun.green.domain.xiaoshou.XiaoShouContact;
import com.yajun.green.repository.HibernateEntityObjectDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by LiuMengKe on 2018/1/2.
 */
@Repository("xiaoShouForCarrierDao")
public class XiaoShouForCarrierDaoImpl extends HibernateEntityObjectDao implements XiaoShouForCarrierDao{

    @Override
    public List<XiaoShouContact> findOverviewZuPinContact(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus, int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("select distinct(c) from XiaoShouContact c left join c.vehicleExecutes v where 1=1 and ((c.startDate >= '" + startTime + "' and c.startDate <='" + endTime + "') or (c.endDate >= '" + startTime + "' and c.endDate <='" + endTime + "'))");
        if (StringUtils.hasText(userUuid)) {
            builder.append(" and c.yiFangUuid = '" + userUuid + "'");
        }

        if (StringUtils.hasText(keyWords)) {
            String  upperkeyWords = keyWords.toUpperCase();
            builder.append(" and (v.vehicleNum like '%" + upperkeyWords + "%' or c.yiFangName like '%" + upperkeyWords + "%' or c.contactNumber like '%" + upperkeyWords + "%')");
        }

        if(!"all".equals(contactStatus)) {
            builder.append("and c.contactStatus ='" + contactStatus + "'");
        }

        builder.append(" order by c." + sortBy + " " + sortWay);

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<XiaoShouContact> contacts = query.list();
        return contacts;
    }

    @Override
    public int findOverviewXiaoShouContactSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(distinct c.uuid) from XiaoShouContact c left join c.vehicleExecutes v where 1=1 and ((c.startDate >= '" + startTime + "' and c.startDate <='" + endTime + "') or (c.endDate >= '" + startTime + "' and c.endDate <='" + endTime + "'))");
        if (StringUtils.hasText(userUuid)) {
            builder.append(" and c.yiFangUuid = '" + userUuid + "'");
        }

        if (StringUtils.hasText(keyWords)) {
            builder.append(" and (v.vehicleNum like '%" + keyWords + "%' or c.yiFangName like '%" + keyWords + "%' or c.contactNumber like '%" + keyWords + "%')");
        }

        if(!"all".equals(contactStatus)) {
            builder.append("and c.contactStatus ='" + contactStatus + "'");
        }

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

        List list = query.list();
        return ((Long) list.get(0)).intValue();
    }
}
