//package com.yajun.green.facade;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Jack Wang
 */
public class ConsumerTest {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:database.xml", "classpath:spring.xml", "classpath:comsumer.xml"});
        context.start();

//        UserServiceFacade us = (UserServiceFacade) context.getBean("userServiceClient");
//
//        System.out.println(us.getUser().getName());
//        System.out.println(us.getUser().getId());
    }

}
