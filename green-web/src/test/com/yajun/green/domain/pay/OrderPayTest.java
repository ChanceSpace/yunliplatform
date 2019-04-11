package com.yajun.green.domain.pay;

import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
public class OrderPayTest extends TestCase {

    @Resource
    SessionFactory sessionFactory;
    HibernateTemplate hibernateTemplate;

    @Before
    public void setUp() {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @After
    public void tearDown() {
        hibernateTemplate = null;
    }

    @Test
    public void testSavePayOrder() {
        PayOrder order = new PayOrder();
        order.setOrderSource(PayOrderSource.O_CONTACT);
        order.setPayWay(PayOrderWay.O_OFFLINE);
        order.setOrderStatus(PayOrderStatus.O_CREATE);
        order.setTotalFee(BigDecimal.valueOf(2000));

        order.setOrderNumber("23454545454");
        order.setJiaoYiNumber("frfgrgrgr");
        order.setChengYunName("你好呀");
//        order.setChengYunNumber("24354656767676");

        order.setCreateTime(new DateTime());
        order.setFinishTime(new DateTime());

        order.setActorName("张三");
        order.setActorUuid("238048504075745067");
        order.setOrderNote("你好呀");

        hibernateTemplate.save(order);
    }
}
