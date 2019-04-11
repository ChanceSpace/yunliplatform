package com.yajun.green.web.controller.contact;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.facade.dto.contact.ZuPinContacctReetingFeeHistoryGroupDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactOverFormDTO;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.ZuPinContactAdditionBalanceService;
import com.yajun.green.service.ZuPinContactService;
import com.yajun.green.service.ZuPinHistoryService;
import com.yajun.green.facade.show.ZuPinContactRiJieBalanceObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by LiuMengKe on 2017/8/15.
 *      账单页面  车辆附加费用 页面
 */
@Controller
public class ZuPinContactRepayController {

    @Autowired
    private ZuPinContactService zuPinContactService;
    @Autowired
    private ZuPinHistoryService zuPinHistoryService;
    @Autowired
    private ZuPinContactAdditionBalanceService zuPinContactAdditionBalanceService;


    /*******************************************合同账单部分****************************************************/

    //月结账单
    @RequestMapping(value = "/contact/zupincontactrepayoverview.html",method = RequestMethod.GET)
    public String zuPinContactRepayOverview(HttpServletRequest request, HttpServletResponse response, ModelMap map){
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        map.put("zuPinContactUuid", zuPinContactUuid);
        Map<String, ZuPinContacctReetingFeeHistoryGroupDTO> historyMap = zuPinHistoryService.obtainZuPinContactMinXi(zuPinContactUuid);
        map.put("historys", historyMap);
        return "contact/zupincontactrepayoverview2";
    }

    //日结账单
    @RequestMapping(value = "/contact/zupincontactrijierepayoverview.html",method = RequestMethod.GET)
    public String zuPinContactRiJieRepayOverview(HttpServletRequest request, HttpServletResponse response, ModelMap map){
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        map.put("zuPinContactUuid", zuPinContactUuid);

        //Map<String, ZuPinContacctReetingFeeHistoryGroupDTO> historyMap = zuPinHistoryService.obtainZuPinContactMinXiByVecle(zuPinContactUuid);

        ZuPinContactRiJieBalanceObj riJieBalanceObj = zuPinHistoryService.obtainZuPinContactRiJieMinXi(zuPinContactUuid);

        map.put("historys", riJieBalanceObj.getFeeHap());
        map.put("additionhistorys",riJieBalanceObj.getFuJiaList());

        return "contact/zupincontactrijierepayoverview";
    }
    
    
    /*********************************附加费用部分*******************************/
    //单车附加费用部分
    @RequestMapping(value = "/contact/zupincontactvehicleadditionbalance.html",method = RequestMethod.GET)
    public String obtainZuPinContactVehicleAdditionBalance(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String tiChePiCi = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "tiChePiCi", ""));
        String vehicleNum = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "vehicleNum", ""));
        String executeUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "executeUuid", ""));

        modelMap.put("zuPinContactUuid",zuPinContactUuid);
        modelMap.put("tiChePiCi",tiChePiCi);
        modelMap.put("vehicleNum",vehicleNum);

        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, false, false,false, false, false);
        modelMap.put("zuPinContact", zuPinContactDTO);
        modelMap.put("zuPinContactRepayType",zuPinContactDTO.getContactModules().get(0).getZuPinContactRepayType().name());
        modelMap.put("executeUuid",executeUuid);
        modelMap.put("vehicleNum",vehicleNum);
        modelMap.put("url","contact/zupincontactvehicleadditionbalance.html");

        return "contact/vehicleadditionbalance";
    }

    //单车附加费用部分
    @RequestMapping(value = "/contact/zupincontactvehicleadditionbalance.html",method = RequestMethod.POST)
    public String saveZuPinContactVehicleAdditionBalance(HttpServletRequest request, HttpServletResponse response, ZuPinContactOverFormDTO zuPinContactOverFormDTO, ModelMap model)throws Exception{
        //合同结束将所有合同内的所有未结束租赁的车辆还原
        String zuPinContactUuid = zuPinContactOverFormDTO.getZuPinContactUuid();
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ApplicationLog.infoWithCurrentUser(ZuPinContactOverZuPinController.class, loginInfo.getUserUuid(), "开始结束合同 合同id:" + zuPinContactUuid);
        //合同期限未至结束合同 合同期限到达自动扣费后结束合同
        zuPinContactAdditionBalanceService.updateVehicleAdditionBalance(zuPinContactOverFormDTO,zuPinContactUuid);
        return "redirect:zupincontactdetails.html?zuPinContactUuid=" + zuPinContactUuid;
    }

    /*//合同附加款项
        @RequestMapping(value = "/contact/zupincontactadditionbalance.html", method = RequestMethod.GET)
        public String obtainZuPinContactAdditionBalance(HttpServletRequest request, HttpServletResponse response, ModelMap model){
            String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
            model.put("zuPinContactUuid",zuPinContactUuid);
            return "contact/zupincontactadditionbalance";
        }*/


       /* //合同附加款项
        @RequestMapping(value = "/contact/zupincontactadditionbalance.html", method = RequestMethod.POST)
        public String saveZuPinContactAdditionBalance(HttpServletRequest request, HttpServletResponse response,ZuPinContactOverFormDTO zuPinContactOverFormDTO, ModelMap model){
            zuPinContactBalanceCalculateAndEditService.saveZuPinContactAdditionBalance(zuPinContactOverFormDTO);
            return "contact/zupincontactadditionbalance";
        }*/
}
