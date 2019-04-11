package com.yajun.green.web.controller.common;


import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.security.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Jack Wang
 */
@Controller
public class CommonController {

    /**********************************************主界面部分******************************************************/

    @RequestMapping("/common/dashboard.html")
    protected String dashboard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        request.setAttribute("MAIN_NUME", ApplicationEventPublisher.applicationMainMenu);
        return "common/dashboard";
    }

    @RequestMapping("/common/main.html")
    protected String main(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        return "common/main";
    }
}
