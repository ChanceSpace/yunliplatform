package com.yajun.green.web;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.web.controller.contact.ZuPinContactManagementController;
import com.yajun.green.web.pagging.ZuPinContactOverviewPaging;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午11:18
 */
public class CHSessionListener implements HttpSessionListener, ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.shutdown();
        } catch (SchedulerException e) {
            ApplicationLog.error(CHSessionListener.class, "Shut down the server and shut down the schedular error", e);
        }
        ApplicationLog.info(CHSessionListener.class, "Shut down the server and shut down the schedular!");
    }

    public void sessionCreated(HttpSessionEvent event) {

    }

    @SuppressWarnings("unchecked")
    public void sessionDestroyed(HttpSessionEvent event) {

    }
}
