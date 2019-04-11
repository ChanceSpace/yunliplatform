package com.yajun.green.web.controller.contact;

import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.ZupinContactForCarrierService;
import com.yajun.green.web.pagging.ZuPinContactForCarrierOverviewPaging;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/9/18.
 * 专供 承运商页面的 合同 controller
 */
@Controller
public class ZupinContactForCarrierManagementController {

    @Autowired
    private ZupinContactForCarrierService zupinContactForCarrierService;

   //暂时只有显示功能
    @RequestMapping("/carrier/zupincontactcarrieroverview.html")
    public String obtainZupinContactOverviewPage(HttpServletRequest request, HttpServletResponse response, ModelMap model){

        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String keyWords = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "keyWords", ""));
        String startTime = ServletRequestUtils.getStringParameter(request, "startTime", new LocalDate().minusYears(1).toString());
        String endTime = ServletRequestUtils.getStringParameter(request, "endTime", new LocalDate().plusMonths(1).toString());
        String sortBy = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "sortBy", "createTime"));
        String sortWay = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "sortWay", "desc"));
        String contactStatus = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "contactStatus", "all"));

        ZuPinContactForCarrierOverviewPaging paging = new ZuPinContactForCarrierOverviewPaging(zupinContactForCarrierService);
        constructVehicleInfoPaging(paging, current, keyWords, startTime, endTime, sortBy, sortWay, contactStatus);
        List<ZuPinContactDTO> contacts = paging.getItems();

        model.put("contacts", contacts);
        model.put("paging", paging);
        return "contact/zupincontactforcarrieroverview";
    }

    private void constructVehicleInfoPaging(ZuPinContactForCarrierOverviewPaging paging, int current, String keyWords, String startTime, String endTime, String sortBy, String sortWay,String contactStatus) {
        paging.setCurrentPageNumber(current);
        paging.setKeyWords(keyWords);
        paging.setStartTime(startTime);
        paging.setEndTime(endTime);
        paging.setSortBy(sortBy);
        paging.setSortWay(sortWay);
        paging.setContactStatus(contactStatus);

        //用户选择自己对应的承运商
        paging.setUserUuid(SecurityUtils.currentLoginUser().getCompanyUuid());
    }
}
