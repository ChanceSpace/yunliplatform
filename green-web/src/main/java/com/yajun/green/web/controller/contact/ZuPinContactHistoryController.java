package com.yajun.green.web.controller.contact;


import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactRentingFeeHistoryDTO;
import com.yajun.green.service.ZuPinContactService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by LiuMengKe on 2017/8/12.
 */
@Controller
public class ZuPinContactHistoryController {
    /*@Autowired
    private ZuPinContactService zuPinContactService;
    *//*****************************************押金扣费界面*****************************************************//*
    @RequestMapping(value="/contact/zupincontactyajinchongzhi.html",method = RequestMethod.GET)
    public String obtainZuPinYaJinHistoryPage(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String contactNumber = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "contactNumber", ""));

        model.put("contactNumber",contactNumber);
        model.put("zuPinContactUuid",zuPinContactUuid);
        model.put("today", new LocalDate().toString());

        //校验时间是否在合同范围内
        //获取合同车辆信息
        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, false, false, false,false,false,false);

        String startDate = zuPinContactDTO.getStartDate();
        String endDate = zuPinContactDTO.getEndDate();
        model.put("startDate",startDate);
        model.put("endDate",endDate);
        Boolean endExecute = zuPinContactDTO.isEndExecute();
        model.put("endExecute",endExecute);

        return "contact/customerboundchongzhi";
    }

    *//********************************************押金扣费操作*************************************************//*
    @RequestMapping(value="/contact/zupincontactyajinchongzhi.html",method = RequestMethod.POST)
    public String saveZuPinYaJinHistory(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("zuPinContactRentingFeeHistory")ZuPinContactRentingFeeHistoryDTO historyDTO, BindingResult errors, ModelMap model){
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String contactNumber = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "contactNumber", ""));
        boolean chongzhi = ServletRequestUtils.getBooleanParameter(request, "chongzhi", true);

        if(errors.hasErrors()){
            model.put("contactNumber",contactNumber);
            model.put("current",current);
            model.put("zuPinContactUuid",zuPinContactUuid);
            return "contact/customerboundchongzhi";
        }

        zuPinContactService.saveZuPinYaJinKouFei(zuPinContactUuid,historyDTO);


        return "redirect:zupincontactdetails.html?zuPinContactUuid=" + zuPinContactUuid;
    }*/


}
