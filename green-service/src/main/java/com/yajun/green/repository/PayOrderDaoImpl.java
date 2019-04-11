package com.yajun.green.repository;

import com.yajun.green.domain.pay.PayOrder;
import com.yajun.green.domain.pay.PayOrderStatus;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-10-15
 * Time: 下午1:30
 */
@Repository("payOrderDao")
public class PayOrderDaoImpl extends HibernateEntityObjectDao implements PayOrderDao {

    @Override
    public PayOrder findNotFinishPayOrderByChengYunUuid(String chengYunUuid) {
        List<PayOrder> orders = getHibernateTemplate().find("from PayOrder p where p.chengYunUuid = ? and p.orderStatus = ? and p.orderSource = 'O_CONTACT' ", new Object[]{chengYunUuid, PayOrderStatus.O_CREATE});
        if (orders != null && !orders.isEmpty()) {
            return orders.get(0);
        }
        return null;
    }

    @Override
    public PayOrder findNotFinishXiaoShouPayOrderByChengYunUuid(String chengYunUuid) {
        List<PayOrder> orders = getHibernateTemplate().find("from PayOrder p where p.chengYunUuid = ? and p.orderStatus = ? and p.orderSource = 'O_XS' ", new Object[]{chengYunUuid, PayOrderStatus.O_CREATE});
        if (orders != null && !orders.isEmpty()) {
            return orders.get(0);
        }
        return null;
    }

    @Override
    public PayOrder findPayOrderByNumber(String payOrderNumber) {
        List<PayOrder> orders = getHibernateTemplate().find("from PayOrder p where p.orderNumber = ?", new Object[]{payOrderNumber});
        if (orders != null && !orders.isEmpty()) {
            return orders.get(0);
        }
        return null;
    }

    @Override
    public List<PayOrder> findOverviewCarrierPayOrder(String keyWords, String userUuid, String userType, String startTime, String endTime,String selType, int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        if ("U_CARRIER_RENTING".equals(userType)) {
            builder.append(" from PayOrder p where 1=1 and p.finishTime >= '" + startTime.concat(" 00:00:00") + "' and p.finishTime <= '" + endTime.concat(" 23:59:59") + "' and p.beloneUuid = '" + userUuid + "'");
        } else {
            builder.append(" from PayOrder p where 1=1 and p.finishTime >= '" + startTime.concat(" 00:00:00") + "' and p.finishTime <= '" + endTime.concat(" 23:59:59") + "' and p.chengYunUuid = '" + userUuid + "'");
        }
        if (StringUtils.hasText(keyWords)) {
            builder.append(" and ( p.chengYunName like '%" + keyWords + "%' or p.orderNumber like '%" + keyWords + "%' )");
        }
        if(StringUtils.hasText(selType)){
            if (!"ALL".equals(selType)){
                builder.append(" and p.payWay = '" +selType+"'");
            }
        }
        builder.append(" order by p.finishTime desc");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<PayOrder> orders = query.list();
        return orders;
    }

    @Override
    public int findOverviewCarrierPayOrderSize(String keyWords, String userUuid, String userType, String startTime, String endTime,String selType) {
        StringBuilder builder = new StringBuilder();
        if ("U_CARRIER_RENTING".equals(userType)) {
            builder.append(" select count(p.uuid) from PayOrder p where 1=1 and p.finishTime >= '" + startTime.concat(" 00:00:00") + "' and p.finishTime <= '" + endTime.concat(" 23:59:59") + "' and p.beloneUuid = '" + userUuid + "'");
        } else {
            builder.append(" select count(p.uuid) from PayOrder p where 1=1 and p.finishTime >= '" + startTime.concat(" 00:00:00") + "' and p.finishTime <= '" + endTime.concat(" 23:59:59") + "' and p.chengYunUuid = '" + userUuid + "'");
        }
        if (StringUtils.hasText(keyWords)) {
            builder.append(" and (p.chengYunName like '%" + keyWords +  "%' or p.orderNumber like '%" + keyWords + "%')");
        }
        if(StringUtils.hasText(selType)){
            if (!"ALL".equals(selType)){
                builder.append(" and p.payWay = '" +selType+"'");
            }
        }
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

        List list = query.list();
        return ((Long) list.get(0)).intValue();
    }

    @Override
    public List<PayOrder> findAllPayOrder(String keyWords, String userUuid, String userType, String startTime, String endTime, String selType) {
        StringBuilder builder = new StringBuilder();
        if ("U_CARRIER_RENTING".equals(userType)) {
            builder.append(" from PayOrder p where 1=1 and p.finishTime >= '" + startTime.concat(" 00:00:00") + "' and p.finishTime <= '" + endTime.concat(" 23:59:59") + "' and p.beloneUuid = '" + userUuid + "'");
        } else {
            builder.append(" from PayOrder p where 1=1 and p.finishTime >= '" + startTime.concat(" 00:00:00") + "' and p.finishTime <= '" + endTime.concat(" 23:59:59") + "' and p.chengYunUuid = '" + userUuid + "'");
        }
        if (StringUtils.hasText(keyWords)) {
            builder.append(" and ( p.chengYunName like '%" + keyWords + "%' or p.orderNumber like '%" + keyWords + "%' )");
        }
        if(StringUtils.hasText(selType)){
            if (!"ALL".equals(selType)){
                builder.append(" and p.payWay = '" +selType+"'");
            }
        }
        builder.append(" order by p.finishTime desc");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

        List<PayOrder> orders = query.list();
        return orders;
    }
}
