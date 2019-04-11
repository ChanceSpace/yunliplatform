package com.yajun.green.security;

import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.manager.RedisCacheManager;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: Jack Wang
 * Date: 17-11-6
 * Time: 上午8:56
 */
public class SecurityUtils {

    public static final ThreadLocal<String> userToken = new ThreadLocal();
    public static final ThreadLocal<LoginInfo> loginUser = new ThreadLocal();

    public static LoginInfo currentLoginUser(String token) {
        RedisCacheManager redis = (RedisCacheManager) ApplicationEventPublisher.getBean("redisCacheManager");
        LoginInfo login = (LoginInfo) redis.fetch(token);
        redis.setTimeOut(token, 1, TimeUnit.HOURS);
        return login;
    }

    public static LoginInfo currentLoginUser() {
        LoginInfo login = loginUser.get();
        return login;
    }

    public static boolean hasRentingAccess() {
        LoginInfo login = loginUser.get();
        List<String> authorities = login.getRoles();

        for (String role : authorities) {
            if (role.equals("ROLE_GREEN_CARRIER_RENT") || role.equals("ROLE_GREEN_ADMIN") || role.equals("ROLE_GREEN_FINANCE")) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSaleAccess() {
        LoginInfo login = loginUser.get();
        List<String> authorities = login.getRoles();

        for (String role : authorities) {
            if (role.equals("ROLE_GREEN_CONTACT_SALE") || role.equals("ROLE_GREEN_CONTACT_FINANCE")) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSaleFinance() {
        LoginInfo login = loginUser.get();
        List<String> authorities = login.getRoles();

        for (String role : authorities) {
            if (role.equals("ROLE_GREEN_CONTACT_FINANCE")){
                return true;
            }
        }
        return false;
    }


    public static  boolean hasAdminRole(){
        LoginInfo login = loginUser.get();
        List<String> authorities = login.getRoles();

        for (String role : authorities) {
            if (role.equals("ROLE_GREEN_ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
