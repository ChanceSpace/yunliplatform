package com.yajun.green.repository;


import com.yajun.green.domain.contact.ZuPinContactRentingFeeHistory;
import com.yajun.green.domain.contact.ZuPinVehicleExecute;
import com.yajun.green.domain.pay.PayOrderStatus;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 * Created by LiuMengKe on 2017/8/9.
 */
@Repository("zuPinHistoryDao")
public class ZuPinHistoryDaoImpl extends HibernateEntityObjectDao implements ZuPinHistoryDao {



    @Override
    public List<ZuPinContactRentingFeeHistory> obtainOverviewZuPinContactRentingFeeHistoryList(String zuPinContactUuid, String keyWords, int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("from ZuPinContactRentingFeeHistory  c where 1=1 ");
       /* if (StringUtils.hasText(keyWords)) {
            builder.append(" and (c.yiFangName like '%" + keyWords + "%' or c.contactNumber like '%" + keyWords + "%')");
        }*/
        if (!"ALL".equals(zuPinContactUuid)) {
            builder.append(" and c.zuPinContact.uuid = '" + zuPinContactUuid + "'");
        }
        builder.append(" order by c.actualFeeDate desc");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

//        query.setMaxResults(pageSize);
//        query.setFirstResult(startPosition);

        List<ZuPinContactRentingFeeHistory> contacts = query.list();
        return contacts;
    }

    @Override
    public int obtainOverviewZuPinContactRentingFeeHistorySize(String keyWords, String zuPinContactUuid) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(c.uuid) from ZuPinContactRentingFeeHistory c where 1=1");

        //注意 百分号前面是单引号
       /* if (StringUtils.hasText(keyWords)) {
            builder.append(" and (c.yiFangName like '%" + keyWords + "%' or c.contactNumber like '%" + keyWords + "%')");
        }*/
        if (!"ALL".equals(zuPinContactUuid)) {
            builder.append(" and c.zuPinContact.uuid = '" + zuPinContactUuid + "'");
        }

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

        List list = query.list();
        return ((Long) list.get(0)).intValue();
    }

    /**
     * @Author Liu MengKe
     * @Description:
     */
    @Override
    public List<ZuPinVehicleExecute> obtainReachNextRentFeeDateVehicle(LocalDate willreachDate) {
        StringBuilder builder = new StringBuilder();
        builder.append("from ZuPinVehicleExecute e where 1=1 and e.zuPinContactBaoYueType !='B_RIJIE' and ((e.nextFeeDate <= ? and e.isOver=false ) or (e.nextYajinDate <= ? and e.zuPinContactRepayType ='C_AFTER' and e.isOver=false))");
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setParameter(0,willreachDate);
        query.setParameter(1,willreachDate);

        List<ZuPinVehicleExecute> contacts = query.list();
        /******在一个时间内一个车牌只会出现在一个合同中取第一个******/
        if(contacts.size()>0){
            return contacts;
        }
        return null;
    }

    @Override
    public List<ZuPinVehicleExecute> obtainReachNextRentFeeDateAndMoneyEmptyVehicle(LocalDate newDate) {
        StringBuilder builder = new StringBuilder();
        builder.append("from ZuPinVehicleExecute e where 1=1 and e.nextMessageDate = ? and e.isOver=false");
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setParameter(0,newDate);
        List<ZuPinVehicleExecute> contacts = query.list();
        /******在一个时间内一个车牌只会出现在一个合同中取第一个******/
        if(contacts.size()>0){
            return contacts;
        }
        return null;
    }


    @Override
    public List<ZuPinContactRentingFeeHistory> obtainAllHistoryInSpecStatus(PayOrderStatus status) {
        StringBuilder builder = new StringBuilder();
        builder.append("from ZuPinContactRentingFeeHistory  c where 1=1 ");
        if(status!=null){
            builder.append(" and c.status ='"+status+"'");
        }
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

        List<ZuPinContactRentingFeeHistory> contacts = query.list();
        return contacts;
    }
}
