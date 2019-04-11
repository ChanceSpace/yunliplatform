package com.yajun.green.application;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:16
 */
@Service("applicationEventPublisher")
public class ApplicationEventPublisher implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext ctx;

    /*********************************系统变量相关**************************************/

    @Value("${application.live}")
    private boolean applicationLiveTemp;
    public static boolean applicationLive;

    @Value("${application.context}")
    private String applicationContextTemp;
    public static String applicationContext;

    @Value("${application.host}")
    private String applicationHostTemp;
    public static String applicationHost;

    /******************************文件相关*******************************************/

    @Value("${application.upload.file.path}")
    private String applicationFileUploadPathTemp;
    public static String applicationFileUploadPath;

    @Value("${application.file.request.path}")
    private String applicationFileRequestPathTemp;
    public static String applicationFileRequestPath;

    /************************************支付相关*****************************************/

    @Value("${application.payment.live}")
    private boolean applicationPaymentLiveTemp;
    public static boolean applicationPaymentLive;

    @Value("${application.payment.url}")
    private String applicationPaymentURLTemp;
    public static String applicationPaymentURL;

    public static String autoJiaoYiRen = "系统";
    public static String autoJiaoYiRenUuid = "sys_id";

    /************************************车辆接口相关*****************************************/

    @Value("${application.vms.security.host}")
    private String applicationVMSSecurityHostTemp;
    public static String applicationVMSSecurityHost;

    @Value("${application.vms.remote.host}")
    private String applicationVMSRemoteHostTemp;
    public static String applicationVMSRemoteHost;

    /************************************用户中心接口相关*****************************************/

    @Value("${application.user.security.host}")
    private String applicationUserSecurityHostTemp;
    public static String applicationUserSecurityHost;

    @Value("${application.user.remote.host}")
    private String applicationUserRemoteHostTemp;
    public static String applicationUserRemoteHost;

    /**********************************系统整理登录入口**************************************/

    @Value("${application.login.host}")
    private String applicationLoginHostTemp;
    public static String applicationLoginHost;

    @Value("${application.main.menu}")
    private String applicationMainMenuTemp;
    public static String applicationMainMenu;

    /**********************************其他静态属性*****************************************/

    public static final int TI_CHE_BEFORE_DAYS = 30;

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(ctx);

        applicationLive = applicationLiveTemp;
        applicationContext = applicationContextTemp;
        applicationHost = applicationHostTemp;

        applicationFileUploadPath = applicationFileUploadPathTemp;
        applicationFileRequestPath = applicationFileRequestPathTemp;

        applicationPaymentLive = applicationPaymentLiveTemp;
        applicationPaymentURL = applicationPaymentURLTemp;

        applicationVMSSecurityHost = applicationVMSSecurityHostTemp;
        applicationVMSRemoteHost = applicationVMSRemoteHostTemp;

        applicationUserSecurityHost = applicationUserSecurityHostTemp;
        applicationUserRemoteHost = applicationUserRemoteHostTemp;

        applicationLoginHost = applicationLoginHostTemp;
        applicationMainMenu = applicationMainMenuTemp;
    }

    public static void publish(ApplicationEvent event) {
        if (isRunningInTomcat()) {
            ctx.publishEvent(event);
        }
    }

    /***********************************GETTER/SETTER********************************************/

    public static Object getBean(String beanName) {
        return getCtx().getBean(beanName);
    }

    private static boolean isRunningInTomcat() {
        return ctx != null;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setCtx(applicationContext);
    }

    public static ApplicationContext getCtx() {
        return ctx;
    }

    private void setCtx(ApplicationContext ctx) {
        ApplicationEventPublisher.ctx = ctx;
    }
}
