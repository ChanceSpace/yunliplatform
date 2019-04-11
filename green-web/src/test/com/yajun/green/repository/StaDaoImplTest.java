package com.yajun.green.repository;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by LiuMengKe on 2018/1/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
public class StaDaoImplTest extends TestCase  {

    @Resource
    private StaDao staDao;

    @Before
    public void setUp() throws Exception{
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testObtainZuPinContactSta() throws Exception {
        staDao.findZuPinContactSta(true, null, 2017);
    }

}
