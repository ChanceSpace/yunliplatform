package com.yajun.green.domain.Vehicle;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by chance on 2017/11/28.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
public class VehicleExportTest {

    @Resource
    SessionFactory sessionFactory;
    HibernateTemplate hibernateTemplate;

    @Before
    public void setUp() {
        hibernateTemplate = new HibernateTemplate();
    }

    @After
    public void tearDown() {
        hibernateTemplate = null;
    }

    @Test
    public void testVehicleExport() {
    }

}
