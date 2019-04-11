package com.yajun.green.repository;


import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.CHSQLUtils;
import com.yajun.green.domain.contact.*;
import com.yajun.green.facade.dto.admin.CompanyDTO;
import com.yajun.green.security.SecurityUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * Created by LiuMengKe on 2017/8/9.
 */
@Repository("zuPinContactDao")
public class ZuPinContactDaoImpl extends HibernateEntityObjectDao implements ZuPinContactDao {
    @Override
    public List<ZuPinContact> obtainAllNotFinishedZupincontact() {
        String builder = " from ZuPinContact c where 1=1 and  c.contactStatus ='"+"S_PROCESSING"+"' or c.contactStatus='"+"S_ENDBUTNOTOVER' ";
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        List<ZuPinContact > contacts = query.list();
        return contacts;
    }

    @Override
    public List<ZuPinContact> findOverviewZuPinContact(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus, int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("select distinct(c) from ZuPinContact c left join c.vehicleExecutes v where 1=1 and ((c.startDate >= '" + startTime + "' and c.startDate <='" + endTime + "') or (c.endDate >= '" + startTime + "' and c.endDate <='" + endTime + "'))");
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

        List<ZuPinContact> contacts = query.list();
        return contacts;
    }

    @Override
    public int findOverviewZuPinContactSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay,String contactStatus) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(distinct c.uuid) from ZuPinContact c left join c.vehicleExecutes v where 1=1 and ((c.startDate >= '" + startTime + "' and c.startDate <='" + endTime + "') or (c.endDate >= '" + startTime + "' and c.endDate <='" + endTime + "'))");
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
    public boolean findZuPinOrderDuplicate(String zuPinNumberSuffix) {
        List list = getHibernateTemplate().find("select count(y.uuid) from ZuPinContact y where y.contactNumber = '" + zuPinNumberSuffix + "%'");
        return ((Long)list.get(0)).intValue() > 0 ? true : false;
    }

    @Override
    public ZuPinVehicleExecute obtainDuplicateVehicleFromAllContact(String vehicleNum, String tiCheDate) {
        StringBuilder builder = new StringBuilder();
        builder.append("from ZuPinVehicleExecute e where 1=1 and e.vehicleNum=? and e.tiCheDate <= ? and  e.jieshuDate >= ? and e.isOver=false");
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setParameter(0,vehicleNum);
        query.setParameter(1,new LocalDate(tiCheDate));
        query.setParameter(2,new LocalDate(tiCheDate));
        List<ZuPinVehicleExecute> contacts = query.list();
        /******在一个时间内一个车牌只会出现在一个合同中取第一个******/
        if(contacts.size()>0){
            return contacts.get(0);
        }
        return null;
    }

    @Override
    public ZuPinVehicleExecute obtainSpecZuPinContactVehicleExcuteDTO(String zuPinContactUuid, String tiChePiCi, String vehicleNum) {
        StringBuilder builder = new StringBuilder();
        builder.append("from ZuPinVehicleExecute e where 1=1 and e.vehicleNum=? and e.tiChePiCi = ? and  e.zuPinContact.uuid = ? and e.isOver=false");
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setParameter(0,vehicleNum);
        query.setParameter(1,Integer.valueOf(tiChePiCi));
        query.setParameter(2,zuPinContactUuid);
        List<ZuPinVehicleExecute> contacts = query.list();
        /******在一个时间内一个车牌只会出现在一个合同中取第一个******/
        if(contacts.size()>0){
            return contacts.get(0);
        }
        return null;
    }

    @Override
    public ZuPinVehicleExecute obtainAllStatusSpecZuPinContactVehicleExcute(String zuPinContactUuid, String tiChePiCi, String vehicleNum) {
        StringBuilder builder = new StringBuilder();
        builder.append("from ZuPinVehicleExecute e where 1=1 and e.vehicleNum=? and e.tiChePiCi = ? and  e.zuPinContact.uuid = ?");
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setParameter(0,vehicleNum);
        query.setParameter(1,Integer.valueOf(tiChePiCi));
        query.setParameter(2,zuPinContactUuid);
        List<ZuPinVehicleExecute> contacts = query.list();
        /******在一个时间内一个车牌只会出现在一个合同中取第一个******/
        if(contacts.size()>0){
            return contacts.get(0);
        }
        return null;
    }

     /*******************************************扣款历史*******************************************/

     @Override
    public List<ZuPinContactRentingFeeHistory> findContactRentingFeeHistory(List<String> historyUuids) {
        return getHibernateTemplate().find("from ZuPinContactRentingFeeHistory z where z.uuid in (" + CHSQLUtils.convertListToSQLIn(historyUuids) + ")");
    }

    @Override
    public List<ZuPinContactRentingFeeHistory> findSpecUserOnCreateBill(String selectCarrierUuid) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        StringBuilder builder = new StringBuilder();
        builder.append("select distinct(h) from ZuPinContactRentingFeeHistory h join h.zuPinContact c where 1=1 ");
        builder.append(" and h.status = 'O_CREATE'");
        builder.append(" and c.yiFangUuid = ?");
        if (SecurityUtils.hasRentingAccess()) {
            builder.append(" and c.jiaFangUuid = ?");
        }
        Query query = null;

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        query = session.createQuery(builder.toString());
        query.setParameter(0, selectCarrierUuid);
        if (SecurityUtils.hasRentingAccess()) {
            query.setParameter(1, loginInfo.getCompanyUuid());
        }
        return query.list();
    }

    /********************************************充电桩部分**********************************/

    @Override
    public List<ZuPinContactCharging> findZuPinContactCharging(String yiFangUuid) {
        return getHibernateTemplate().find(" from ZuPinContactCharging z where z.companyUuid = ?", new Object[]{yiFangUuid});
    }

    @Override
    public List<Object[]> obtainAllCompanyWithOnCreateBillOnLoginUser() {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        StringBuilder builder = new StringBuilder();
        builder.append("select h.zuPinContact.yiFangUuid,h.zuPinContact.yiFangName from ZuPinContactRentingFeeHistory h where 1=1 and h.zuPinContact.jiaFangUuid = ?  and  h.status = 'O_CREATE'");
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setParameter(0, loginInfo.getCompanyUuid());
        return query.list();
    }
}
