package com.yajun.green.web.filter;

import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.manager.RedisCacheManager;
import com.yajun.green.security.SecurityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * User: Jack Wang
 * Date: 17-11-15
 * Time: 下午5:46
 */
public class ApplicationSecurityInterceptor extends HandlerInterceptorAdapter {

    private static final String TOKEN = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = ServletRequestUtils.getStringParameter(request, TOKEN, "");
        if (StringUtils.hasText(token)) {
            request.getSession().setAttribute(TOKEN, token);
            SecurityUtils.userToken.set(token);
        } else {
            token = (String) request.getSession().getAttribute(TOKEN);
            SecurityUtils.userToken.set(token);
        }
        if (!StringUtils.hasText(token)) {
            response.sendRedirect(ApplicationEventPublisher.applicationLoginHost);
            return false;
        }

        RedisCacheManager redis = (RedisCacheManager) ApplicationEventPublisher.getBean("redisCacheManager");
        LoginInfo login = (LoginInfo) redis.fetch(token);
        if (login == null) {
            response.sendRedirect(ApplicationEventPublisher.applicationLoginHost);
            return false;
        }

        redis.setTimeOut(token, 1, TimeUnit.HOURS);
        SecurityUtils.loginUser.set(login);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
