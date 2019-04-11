package com.yajun.green.web.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-4-24
 * Time: 上午9:30
 */
@Controller
public class ApplicationExceptionController {

    @RequestMapping("/error.html")
    protected String error1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "common/error";
    }

    @RequestMapping("/admin/error.html")
    protected String error2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "common/error";
    }

    @RequestMapping("/carrier/error.html")
    protected String error3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "common/error";
    }

    @RequestMapping("/service/error.html")
    protected String error4(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "common/error";
    }

    @RequestMapping("/contact/error.html")
    protected String error5(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "common/error";
    }

    @RequestMapping("/notauthority.html")
    protected String authority1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "common/authority";
    }
}
