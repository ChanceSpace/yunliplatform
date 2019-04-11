package com.yajun.green.web.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.security.SecurityUtils;
import com.yajun.user.facade.dto.CompanyDTO;
import com.yajun.user.service.UserDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-8-28
 * Time: 上午11:07
 */
@Controller
public class UserInfoManagementController {

    @Autowired
    private UserDubboService userDubboService;

    /******************************************查询用户**********************************************/

    @RequestMapping("/contact/getsearchcustomers.html")
    public void getSearchCustomers(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String searchWords = ServletRequestUtils.getStringParameter(request, "searchWords", "");

        //查询数据
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ApplicationLog.info(UserInfoManagementController.class, loginInfo.getUserUuid() + " search customer with words " + searchWords);
        String parentUuid = loginInfo.getCompanyUuid();

        List<CompanyDTO> companies = userDubboService.obtainTongBuAllSearchCompanies(parentUuid, searchWords);
        //返回结果
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONObject.toJSONString(companies));
        writer.flush();
        writer.close();
    }
}
