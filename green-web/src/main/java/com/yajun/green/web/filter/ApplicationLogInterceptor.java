package com.yajun.green.web.filter;

import com.yajun.green.common.log.ApplicationLogThread;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.thread.ApplicationThreadPool;
import com.yajun.green.security.SecurityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 16-9-11
 * Time: 下午2:30
 */
public class ApplicationLogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //RRL访问日志
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        if (loginInfo != null && StringUtils.hasText(loginInfo.getUserUuid())) {
            String requestURI = request.getRequestURI();
            ApplicationLogThread log = new ApplicationLogThread(loginInfo.getUserUuid(), requestURI);
            ApplicationThreadPool.executeThread(log);
        }

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
