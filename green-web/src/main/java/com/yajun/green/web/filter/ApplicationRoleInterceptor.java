package com.yajun.green.web.filter;

import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.security.SecurityUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-9-11
 * Time: 下午2:30
 */
public class ApplicationRoleInterceptor extends HandlerInterceptorAdapter {

    private final static List<String> keyURLs = new ArrayList<>();

    static {
        keyURLs.add("/contact/zupincontactform.html");
        keyURLs.add("/contact/zupincontactdelete.html");

        keyURLs.add("/contact/zupincontactsupplyform.html");
        keyURLs.add("/contact/zupincontactsupplydelete.html");
        keyURLs.add("/contact/zupincontactsupplycontentform.html");
        keyURLs.add("/contact/zupincontactsupplycontentdelete.html");

        keyURLs.add("/contact/zupincontactchargingform.html");
        keyURLs.add("/contact/zupincontactchargingdelete.html");
        keyURLs.add("/contact/zupincontactchargingstatus.html");

        keyURLs.add("/contact/zupincontactvehicleform.html");
        keyURLs.add("/contact/zupincontactvehicledelete.html");
        keyURLs.add("/contact/zupincontactvehicleget.html");

        keyURLs.add("/contact/zupincontactrepay.html");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //RRL访问日志
        String requestURI = request.getRequestURI();
        boolean rentingRole = SecurityUtils.hasRentingAccess();
        String dispatcherURL = requestURI.replace("/" + ApplicationEventPublisher.applicationContext, "");
        if (keyURLs.contains(dispatcherURL)) {
            if (!rentingRole) {
                response.sendRedirect("/" + ApplicationEventPublisher.applicationContext + "/notauthority.html");
            }
        }

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

}
