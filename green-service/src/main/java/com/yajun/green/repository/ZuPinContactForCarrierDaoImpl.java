package com.yajun.green.repository;

import com.yajun.green.domain.contact.ZuPinContact;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/9/18.
 */
@Repository("zuPinContactForCarrierDao")
public class ZuPinContactForCarrierDaoImpl extends HibernateEntityObjectDao implements  ZuPinContactForCarrierDao{

    @Override
    public List<ZuPinContact> obtainZuPinContactForCarrierOverview(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, int startPosition, int pageSize,String contactStatus) {
        StringBuilder builder = new StringBuilder();
        builder.append("select distinct(c) from ZuPinContact c left join c.vehicleExecutes v where 1=1 and ((c.startDate >= '" + startTime + "' and c.startDate <='" + endTime + "') or (c.endDate >= '" + startTime + "' and c.endDate <='" + endTime + "'))");
        if (StringUtils.hasText(keyWords)) {
            builder.append(" and (v.vehicleNum like '%" + keyWords + "%' or c.contactNumber like '%" + keyWords + "%')");
        }

        builder.append("and c.yiFangUuid =? ");

        if(!"all".equals(contactStatus)){
            builder.append("and c.contactStatus ='"+contactStatus+"'");
        }

        builder.append(" order by c." + sortBy + " " + sortWay);

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setParameter(0, userUuid);
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<ZuPinContact> contacts = query.list();
        return contacts;
    }

    @Override
    public int obtainOverviewZuPinContactForCarrierSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay,String contactStatus) {
        StringBuilder builder = new StringBuilder();

        //注意 百分号前面是单引号
        builder.append("select count(distinct c.uuid) from ZuPinContact c left join c.vehicleExecutes v where 1=1 and ((c.startDate >= '" + startTime + "' and c.startDate <='" + endTime + "') or (c.endDate >= '" + startTime + "' and c.endDate <='" + endTime + "'))");
        if (StringUtils.hasText(keyWords)) {
            builder.append(" and (v.vehicleNum like '%" + keyWords + "%' or c.yiFangName like '%" + keyWords + "%' or c.contactNumber like '%" + keyWords + "%')");
        }

        if(!"all".equals(contactStatus)){
            builder.append("and c.contactStatus ='"+contactStatus+"'");
        }

        builder.append("and c.yiFangUuid = '"+userUuid+"'");
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

        List list = query.list();
        return ((Long) list.get(0)).intValue();
    }


}
