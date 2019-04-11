//package com.yajun.green.repository;
//
//import com.yajun.green.manager.RedisCacheManager;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * User: Jack Wang
// * Date: 17-10-31
// * Time: 下午1:58
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
//public class RedisCacheTest {
//
//    @Autowired
//    private RedisCacheManager redis;
//
//    @Test
//    public void testSaveCache() {
//        redis.cache("TEST_IN", "nice to ");
//        Object in = redis.fetch("TEST_IN");
//        System.out.println(in);
//    }
//}
