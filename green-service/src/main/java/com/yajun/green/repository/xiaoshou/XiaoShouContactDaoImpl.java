package com.yajun.green.repository.xiaoshou;

import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.CHSQLUtils;
import com.yajun.green.domain.xiaoshou.XiaoShouContact;
import com.yajun.green.domain.xiaoshou.XiaoShouContactRentingFeeHistory;
import com.yajun.green.repository.HibernateEntityObjectDao;
import com.yajun.green.security.SecurityUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/12/21.
 */
@Repository("xiaoShouContactDao")
public class XiaoShouContactDaoImpl extends HibernateEntityObjectDao implements XiaoShouContactDao {

    @Override
    public List<XiaoShouContact> findOverviewXiaoShouContact(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus, int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("select distinct(c) from XiaoShouContact c left join c.vehicleExecutes v where 1=1 and ((c.startDate >= '" + startTime + "' and c.startDate <='" + endTime + "') or (c.endDate >= '" + startTime + "' and c.endDate <='" + endTime + "'))");
        if (StringUtils.hasText(userUuid)) {
            builder.append(" and c.jiaFangUuid = '" + userUuid + "'");
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
    public int findOverviewXiaoShouSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(distinct c.uuid) from XiaoShouContact c left join c.vehicleExecutes v where 1=1 and ((c.startDate >= '" + startTime + "' and c.startDate <='" + endTime + "') or (c.endDate >= '" + startTime + "' and c.endDate <='" + endTime + "'))");
        if (StringUtils.hasText(userUuid)) {
            builder.append(" and c.jiaFangUuid = '" + userUuid + "'");
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

    @Override
    public boolean findXiaoShouOrderDuplicate(String temp) {
        List list = getHibernateTemplate().find("select count(y.uuid) from XiaoShouContact y where y.contactNumber = '" + temp + "%'");
        return ((Long)list.get(0)).intValue() > 0 ? true : false;
    }

    @Override
    public List<Object[]> obtainAllCompanyWithOnCreateBillOnLoginUser() {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        StringBuilder builder = new StringBuilder();
        builder.append("select h.xiaoShouContact.yiFangUuid,h.xiaoShouContact.yiFangName from XiaoShouContactRentingFeeHistory h where 1=1 and h.xiaoShouContact.jiaFangUuid = ?  and  h.payOrderStatus = 'O_CREATE'");
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setParameter(0, loginInfo.getCompanyUuid());
        return query.list();
    }

    @Override
    public List<XiaoShouContactRentingFeeHistory> findSpecUserOnCreateBill(String selectCarrierUuid) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        StringBuilder builder = new StringBuilder();
        builder.append("select distinct(h) from XiaoShouContactRentingFeeHistory h join h.xiaoShouContact c where 1=1 ");
        builder.append(" and h.payOrderStatus = 'O_CREATE'");
        builder.append(" and c.yiFangUuid = ?");
        //todo 使用销售权限
        if (SecurityUtils.hasSaleAccess()) {
            builder.append(" and c.jiaFangUuid = ?");
        }
        Query query = null;

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        query = session.createQuery(builder.toString());
        query.setParameter(0, selectCarrierUuid);
        if (SecurityUtils.hasSaleAccess()) {
            query.setParameter(1, loginInfo.getCompanyUuid());
        }
        return query.list();
    }

    @Override
    public List<XiaoShouContactRentingFeeHistory> findXiaoShouHistory(List<String> historyUuids) {
        return getHibernateTemplate().find("from XiaoShouContactRentingFeeHistory z where z.uuid in (" + CHSQLUtils.convertListToSQLIn(historyUuids) + ")");
    }

    @Override
    public List<XiaoShouContactRentingFeeHistory> obtainXiaoShouHistoryByVehicleNumber(String contactUuid, String vehicleNumber) {


        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        StringBuilder builder = new StringBuilder();
        builder.append("select distinct(h) from XiaoShouContactRentingFeeHistory h join h.xiaoShouContact c where 1=1 ");
        builder.append(" and h.vehicleNumber = ?");
        builder.append(" and c.uuid = ?");
        builder.append(" and c.jiaFangUuid = ?");

        Query query = null;

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        query = session.createQuery(builder.toString());
        query.setParameter(0, vehicleNumber);
        query.setParameter(1, contactUuid);
        if (SecurityUtils.hasSaleFinance()) {
            query.setParameter(2, loginInfo.getCompanyUuid());
        }
        return query.list();
    }
}
