package com.yajun.green.web.controller.contact;

import com.alibaba.fastjson.JSONObject;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.CHListUtils;
import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.contact.ZuPinContactRepayLocation;
import com.yajun.green.facade.dto.contact.*;
import com.yajun.green.facade.dto.pay.PayItem;
import com.yajun.green.facade.dto.pay.PayOrderDTO;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.PayOrderService;
import com.yajun.green.service.ZuPinContactAndVehicleOverService;
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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/24.
 * 结束租赁
 */
@Controller
public class ZuPinContactOverZuPinController {
    @Autowired
    private ZuPinContactService zuPinContactService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private ZuPinContactAndVehicleOverService zuPinContactAndVehicleOverService;


    /************************************************************结束月租类型车辆***************************************************************************************/

    //获取单量车结束时的页面
    @RequestMapping(value = "/contact/overzupincontactvehicle.html",method = RequestMethod.GET)
    public String overZuPinContactVehicle(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap){
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String tiChePiCi = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "tiChePiCi", ""));
        String vehicleNum = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "vehicleNum", ""));
        String executeUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "executeUuid", ""));
        //不论是前付还是后付还是日结都需要使用execute 不能只是依靠 vehicleNumber

        ZuPinVehicleExecuteDTO zuPinVehicleExecuteDTO = zuPinContactService.obtainSpecZuPinContactVehicleExcuteDTO(zuPinContactUuid,tiChePiCi,vehicleNum);

        LocalDate now = new LocalDate();
        LocalDate ticheDate = new LocalDate(zuPinVehicleExecuteDTO.getTiCheDate());
        LocalDate actualDate = new LocalDate(zuPinVehicleExecuteDTO.getActualzujinrepaymonth());

        modelMap.put("zuPinContactUuid",zuPinContactUuid);
        modelMap.put("tiChePiCi",tiChePiCi);
        modelMap.put("vehicleNum",vehicleNum);
        modelMap.put("finishDate",now);
        modelMap.put("ticheDate",ticheDate);
        modelMap.put("actualDate",actualDate);

        //获取结束合同页面 页面构成 1.feehistory 中所有未创建订单的待付款项 2.待收取租金 待退款押金 3.其他手动填写功能
        //校验合同是否有 已经创建订单却未完成付款项
        int nopaySize = obtainSizeOfZupinContactNoPayBill(zuPinContactUuid);
        if(nopaySize>0){
            return "redirect:carrier/paymentmanagement.html?selectCarrierUuid="+zuPinContactUuid;
        }

        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, false, false,false, false, false);
        modelMap.put("zuPinContact", zuPinContactDTO);

        modelMap.put("zuPinContactRepayType",zuPinContactDTO.getContactModules().get(0).getZuPinContactRepayType().name());

        String today = new LocalDate().toString();
        modelMap.put("today", today);
        String jieshuMaxDay = new LocalDate().toString();

        //设置单车结束最大时间为 当天最小时间为提车日期
        modelMap.put("jieshuMaxDay",jieshuMaxDay);
        modelMap.put("jieshuMinDay",zuPinVehicleExecuteDTO.getTiCheDate());

        List<ZuPinContactRentingFeeHistoryDTO> historys = zuPinContactAndVehicleOverService.obtainZuPinContactSpecVehicleDTOList(zuPinContactUuid,executeUuid,vehicleNum,now);
        modelMap.put("houFuFeeList", historys);
        modelMap.put("executeUuid",executeUuid);
        modelMap.put("vehicleNum",vehicleNum);

        return "contact/zupincontactvehicelfinishmanagement";
    }

    //单量车结束 提交
    @RequestMapping(value = "/contact/overzupincontactvehicle.html",method = RequestMethod.POST)
    public String overZuPinContactVehicle(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("zuPinVehicleExecuteOverDTO")ZuPinVehicleExecuteOverDTO zuPinVehicleExecuteOverDTO, BindingResult errors, ModelMap modelMap) throws Exception {
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String tiChePiCi = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "tiChePiCi", ""));
        String vehicleNum = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "vehicleNum", ""));
        String finishDate = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "finishDate", ""));
        String comment = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "comment", ""));

        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ApplicationLog.infoWithCurrentUser(ZuPinContactOverZuPinController.class, loginInfo.getUserUuid(), "开始结束合同id" + zuPinContactUuid + "中车辆" + vehicleNum + "租赁");

        zuPinContactAndVehicleOverService.saveOverZuPinVehicle(zuPinContactUuid,tiChePiCi,vehicleNum,finishDate,comment);
        return "redirect:zupincontactdetails.html?zuPinContactUuid=" + zuPinContactUuid;
    }

    //单车结束页面  时间改变以后改变页面显示的账单
    @RequestMapping(value = "/contact/zupincontactvehiclefinishformdatechange.html",method = RequestMethod.GET)
    public void zuPinContactVehicleFinishDateChange(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String finishDate = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "finishDate", ""));
        String executeUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "executeUuid", ""));
        String vehicleNum = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "vehicleNum", ""));
        JSONObject total = new JSONObject();
        List<ZuPinContactRentingFeeHistoryDTO> historyDTOS = zuPinContactAndVehicleOverService.obtainZuPinContactSpecVehicleDTOList(zuPinContactUuid,executeUuid,vehicleNum,new LocalDate(finishDate));
        try {
            total.put("unCreateBills",historyDTOS);
            //返回结果
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(total.toJSONString());
            writer.flush();
            writer.close();
        }catch (Exception e){
            ApplicationLog.error(ZuPinContactOverZuPinController.class,e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**************************************************************结束月租类型合同*************************************************************************************/
    //结束合同页面
    @RequestMapping(value = "/contact/zupincontactfinishmanagement.html",method = RequestMethod.GET)
    public String overZupinContactManagement(HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        //获取结束合同页面 页面构成 1.feehistory 中所有未创建订单的待付款项 2.待收取租金 待退款押金 3.其他手动填写功能
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        //校验合同是否有 已经创建订单却未完成付款项
        int nopaySize = obtainSizeOfZupinContactNoPayBill(zuPinContactUuid);
        if(nopaySize>0){
            return "redirect:carrier/paymentmanagement.html?selectCarrierUuid="+zuPinContactUuid;
        }

        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, false, false,false, false, false);
        model.put("zuPinContact", zuPinContactDTO);
        model.put("zuPinContactUuid",zuPinContactUuid);
        model.put("zuPinContactRepayType",zuPinContactDTO.getContactModules().get(0).getZuPinContactRepayType().name());

        String today = new LocalDate().toString();
        model.put("today", today);

        //设置合同结束时间的范围 1.时间不能超过今天 2.向前不能超过10 天 3.不能早于提车时间 取所有excute里面最小的 提车时间

        String jieshuMaxDay = new LocalDate().toString();
        model.put("jieshuMaxDay",jieshuMaxDay);


        LocalDate minDate1 = new LocalDate().plusDays(-10);
        LocalDate minDate2 = zuPinContactAndVehicleOverService.obtainZuPinContactMaxTicheDate(zuPinContactUuid);


        String jieshuMinDay = null;

        //所有execute 都结束的时候 用最大范围
        if(minDate2 == null){
            jieshuMinDay = minDate1.toString();
        }else {
            //取提车时间 和 10 中最大的
            jieshuMinDay = JodaUtils.compareDays(minDate1,minDate2)>=0?minDate2.toString():minDate1.toString();
        }

        model.put("jieshuMinDay",jieshuMinDay);

        List<ZuPinContactRentingFeeHistoryDTO> historys = zuPinContactAndVehicleOverService.obtainZupinContactFinishNoPayZuJinList(zuPinContactUuid,new LocalDate());
        model.put("houFuFeeList", historys);

        //获取所有创建状态未支付账单项 包括 租金押金
        List<ZuPinContactRentingFeeHistoryDTO> allNoPayOrders =  zuPinContactAndVehicleOverService.obtainNoPayHistoryList(zuPinContactUuid);
        List<ZuPinContactRentingFeeHistoryDTO> willPayOrders = new ArrayList<>();

        for (ZuPinContactRentingFeeHistoryDTO order : allNoPayOrders) {
            ZuPinContactRepayLocation location = order.getZuPinContactRepayLocation();
            //判断是否是 押金自动扣款 提车押金扣款 如果是则不显示在合同结束页面上 进入结算
            if(!location.contactOverCheckYaJin()){
                willPayOrders.add(order);
            }
        }

        model.put("willPayOrders", willPayOrders);
        return "contact/zupincontactfinishmanagement";
    }

    //结束合同页面提交
    @RequestMapping(value = "/contact/zupincontactfinishmanagement.html",method = RequestMethod.POST)
    public String overZupinContactForm(HttpServletRequest request, HttpServletResponse response, ZuPinContactOverFormDTO zuPinContactOverFormDTO, ModelMap model)throws Exception{
        //合同结束将所有合同内的所有未结束租赁的车辆还原
        String zuPinContactUuid = zuPinContactOverFormDTO.getZuPinContactUuid();
        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, false, true, true, false, false, false);

        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ApplicationLog.infoWithCurrentUser(ZuPinContactOverZuPinController.class, loginInfo.getUserUuid(), "开始结束合同 合同id:" + zuPinContactUuid);
        //合同期限未至结束合同 合同期限到达自动扣费后结束合同
        if(!"S_ENDBUTNOTOVER".equals(zuPinContactDTO.getContactStatus())){
            List<ZuPinVehicleExecuteDTO> executeDTOS = zuPinContactDTO.getSpeUseFulExecute();
            //将车辆状态回滚 进行金额计算
            zuPinContactAndVehicleOverService.updateZuPinContactFinish(executeDTOS,zuPinContactOverFormDTO);
        }else {
            //合同期限正常到达扣费但是没有归还的车
            List<ZuPinVehicleExecuteDTO> executeDTOS = zuPinContactDTO.getOverButNotRevertExecute();
            //将车辆状态回滚 进行金额计算
            zuPinContactAndVehicleOverService.updateZuPinContactFinish(executeDTOS,zuPinContactOverFormDTO);
        }
        return "redirect:zupincontactdetails.html?zuPinContactUuid=" + zuPinContactUuid;
    }


    //合同结束页面  时间改变以后改变页面显示的账单
    @RequestMapping(value = "/contact/zupincontactfinishformdatechange.html",method = RequestMethod.GET)
    public void zuPinContactFinishDateChange(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String finishDate = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "finishDate", ""));
        JSONObject total = new JSONObject();
        List<ZuPinContactRentingFeeHistoryDTO> historyDTOS = zuPinContactAndVehicleOverService.obtainZupinContactFinishNoPayZuJinList(zuPinContactUuid,new LocalDate(finishDate));
        try {
            total.put("unCreateBills",historyDTOS);
            //返回结果
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(total.toJSONString());
            writer.flush();
            writer.close();
        }catch (Exception e){

        }
    }


    //通过Ajax请求是否含有未付款订单 整个合同结束 合同结束之前需要判断已经没有未支付的账单了
    @RequestMapping("/contact/ajaxvalidatenopaybill.html")
    public void validateZupinContactNoPayBill(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid","");
        int nopaySize = obtainSizeOfZupinContactNoPayBill(zuPinContactUuid);
        JSONObject total = new JSONObject();
        if(nopaySize>0){
            total.put("paysuccess",false);
        }else {
            total.put("paysuccess",true);
        }
        //返回结果
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(total.toJSONString());
        writer.flush();
        writer.close();
    }



    /***********************************************************合同日结相关********************************************************************************/






    //日结类型车辆结束页面
    @RequestMapping(value = "/contact/overzupinrijievehiceldateselect.html",method = RequestMethod.GET)
    public String overZuPinRiJieVehicleDateSelect(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap){
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String tiChePiCi = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "tiChePiCi", ""));
        String vehicleNum = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "vehicleNum", ""));
        String executeUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "executeUuid", ""));
        //不论是前付还是后付还是日结都需要使用execute 不能只是依靠 vehicleNumber

        ZuPinVehicleExecuteDTO zuPinVehicleExecuteDTO = zuPinContactService.obtainSpecZuPinContactVehicleExcuteDTO(zuPinContactUuid,tiChePiCi,vehicleNum);

        LocalDate now = new LocalDate();
        LocalDate ticheDate = new LocalDate(zuPinVehicleExecuteDTO.getTiCheDate());
        LocalDate actualDate = new LocalDate(zuPinVehicleExecuteDTO.getActualzujinrepaymonth());

        modelMap.put("zuPinContactUuid",zuPinContactUuid);
        modelMap.put("tiChePiCi",tiChePiCi);
        modelMap.put("vehicleNum",vehicleNum);
        modelMap.put("finishDate",now);
        modelMap.put("ticheDate",ticheDate);
        modelMap.put("actualDate",actualDate);

        //获取结束合同页面 页面构成 1.feehistory 中所有未创建订单的待付款项 2.待收取租金 待退款押金 3.其他手动填写功能
        //校验合同是否有 已经创建订单却未完成付款项
        int nopaySize = obtainSizeOfZupinContactNoPayBill(zuPinContactUuid);
        if(nopaySize>0){
            return "redirect:carrier/paymentmanagement.html?selectCarrierUuid="+zuPinContactUuid;
        }

        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, false, false,false, false, false);
        modelMap.put("zuPinContact", zuPinContactDTO);

        modelMap.put("zuPinContactRepayType",zuPinContactDTO.getContactModules().get(0).getZuPinContactRepayType().name());

        String today = new LocalDate().toString();
        modelMap.put("today", today);
        String jieshuMaxDay = new LocalDate().toString();

        //设置单车结束最大时间为 当天最小时间为提车日期
        modelMap.put("jieshuMaxDay",jieshuMaxDay);
        modelMap.put("jieshuMinDay",zuPinVehicleExecuteDTO.getTiCheDate());

        List<ZuPinContactRentingFeeHistoryDTO> historys = zuPinContactAndVehicleOverService.obtainZuPinContactRiJieSpecVehicleHistoryDTOList(zuPinContactUuid,executeUuid,vehicleNum,now);
        modelMap.put("houFuFeeList", historys);
        modelMap.put("executeUuid",executeUuid);
        modelMap.put("vehicleNum",vehicleNum);

        return "contact/zupincontactvehiclerijiefinishmanagement";
    }

    //日结类型车辆结束提交
    @RequestMapping(value = "/contact/rijiedateform.html",method = RequestMethod.POST)
    public String overZuPinRijieVehicel(HttpServletRequest request, HttpServletResponse response, ZuPinContactOverFormDTO zuPinContactOverFormDTO, ModelMap model)throws Exception{
        //合同结束将所有合同内的所有未结束租赁的车辆还原
        String zuPinContactUuid = zuPinContactOverFormDTO.getZuPinContactUuid();

        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ApplicationLog.infoWithCurrentUser(ZuPinContactOverZuPinController.class, loginInfo.getUserUuid(), "开始结束合同 合同id:" + zuPinContactUuid);
        //合同期限未至结束合同 合同期限到达自动扣费后结束合同

        zuPinContactAndVehicleOverService.updateZuPinContactVehicleRiJieFinish(zuPinContactOverFormDTO);

        return "redirect:zupincontactdetails.html?zuPinContactUuid=" + zuPinContactUuid;
    }



    //单车时间改变以后改变页面显示的账单
    @RequestMapping(value = "/contact/zupincontactvehiclerijiefinishformdatechange.html",method = RequestMethod.GET)
    public void zuPinContactVehicleRiJieFinishDateChange(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String finishDate = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "finishDate", ""));
        String executeUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "executeUuid", ""));
        String vehicleNum = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "vehicleNum", ""));
        JSONObject total = new JSONObject();
        List<ZuPinContactRentingFeeHistoryDTO> historyDTOS = zuPinContactAndVehicleOverService.obtainZuPinContactRiJieSpecVehicleHistoryDTOList(zuPinContactUuid,executeUuid, vehicleNum,new LocalDate(finishDate));
        try {
            total.put("unCreateBills",historyDTOS);
            //返回结果
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(total.toJSONString());
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }






    //日结类型合同 整个结束
    @RequestMapping(value = "/contact/zupincontactrijiefinishmanagement.html",method = RequestMethod.GET)
    public String overZupinContactRiJieManagement(HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        //获取结束合同页面 页面构成 1.feehistory 中所有未创建订单的待付款项 2.待收取租金 待退款押金 3.其他手动填写功能
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        //校验合同是否有 已经创建订单却未完成付款项
        int nopaySize = obtainSizeOfZupinContactNoPayBill(zuPinContactUuid);
        if(nopaySize>0){
            return "redirect:carrier/paymentmanagement.html?selectCarrierUuid="+zuPinContactUuid;
        }

        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, false, false,false, false, false);
        model.put("zuPinContact", zuPinContactDTO);
        model.put("zuPinContactUuid", zuPinContactUuid);

        model.put("zuPinContactRepayType",zuPinContactDTO.getContactModules().get(0).getZuPinContactRepayType().name());

        String today = new LocalDate().toString();
        model.put("today", today);

        //设置合同结束时间的范围 1.时间不能超过今天 2.向前不能超过10 天 3.不能早于提车时间 取所有excute里面最小的 提车时间

        String jieshuMaxDay = new LocalDate().toString();
        model.put("jieshuMaxDay",jieshuMaxDay);


        LocalDate minDate1 = new LocalDate().plusDays(-10);
        LocalDate minDate2 = zuPinContactAndVehicleOverService.obtainZuPinContactMaxTicheDate(zuPinContactUuid);


        String jieshuMinDay = null;

        //所有execute 都结束的时候 用最大范围
        if(minDate2 == null){
            jieshuMinDay = minDate1.toString();
        }else {
            //取提车时间 和 10 中最大的
            jieshuMinDay = JodaUtils.compareDays(minDate1,minDate2)>=0?minDate2.toString():minDate1.toString();
        }

        model.put("jieshuMinDay",jieshuMinDay);

        List<ZuPinContactRentingFeeHistoryDTO> historys = zuPinContactAndVehicleOverService.obtainZupinContactRiJieFinishNoPayZuJinList(zuPinContactUuid,new LocalDate());
        model.put("houFuFeeList", historys);

        //获取所有创建状态未支付账单项 包括 租金押金
        List<ZuPinContactRentingFeeHistoryDTO> allNoPayOrders =  zuPinContactAndVehicleOverService.obtainNoPayHistoryList(zuPinContactUuid);
        List<ZuPinContactRentingFeeHistoryDTO> willPayOrders = new ArrayList<>();

        for (ZuPinContactRentingFeeHistoryDTO order : allNoPayOrders) {
            ZuPinContactRepayLocation location = order.getZuPinContactRepayLocation();
            //判断是否是 押金自动扣款 提车押金扣款 如果是则不显示在合同结束页面上 进入结算
            if(!location.contactOverCheckYaJin()){
                willPayOrders.add(order);
            }
        }

        model.put("willPayOrders", willPayOrders);
        return "contact/zupincontactfinishmanagement";
    }

    //日结合同整个合同结束 提交
    @RequestMapping(value = "/contact/zupincontactrijiefinishmanagement.html",method = RequestMethod.POST)
    public String overZupinContactRiJieForm(HttpServletRequest request, HttpServletResponse response, ZuPinContactOverFormDTO zuPinContactOverFormDTO, ModelMap model)throws Exception{
        //合同结束将所有合同内的所有未结束租赁的车辆还原
        String zuPinContactUuid = zuPinContactOverFormDTO.getZuPinContactUuid();
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ApplicationLog.infoWithCurrentUser(ZuPinContactOverZuPinController.class, loginInfo.getUserUuid(), "开始结束合同 合同id:" + zuPinContactUuid);
        //合同期限未至结束合同 合同期限到达自动扣费后结束合同
        zuPinContactAndVehicleOverService.updateZuPinContactRiJieFinish(zuPinContactOverFormDTO);
        return "redirect:zupincontactdetails.html?zuPinContactUuid=" + zuPinContactUuid;
    }


    //日结类型合同结束页面 时间改变以后改变页面显示的账单
    @RequestMapping(value = "/contact/zupincontactrijiefinishformdatechange.html",method = RequestMethod.GET)
    public void zuPinContactRiJieFinishDateChange(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String finishDate = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "finishDate", ""));
        JSONObject total = new JSONObject();
        List<ZuPinContactRentingFeeHistoryDTO> historyDTOS = zuPinContactAndVehicleOverService.obtainZupinContactRiJieFinishNoPayZuJinList(zuPinContactUuid,new LocalDate(finishDate));
        try {
            total.put("unCreateBills",historyDTOS);
            //返回结果
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(total.toJSONString());
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public int obtainSizeOfZupinContactNoPayBill(String zuPinContactUuid){
        List<ZuPinContactRentingFeeHistoryDTO> nopayBills = obtainZupinContactNoPayBillList(zuPinContactUuid);
        if(CHListUtils.hasElement(nopayBills)){
            return nopayBills.size();
        }else {
            return 0;
        }
    }



    public List<ZuPinContactRentingFeeHistoryDTO> obtainZupinContactNoPayBillList(String zuPinContactUuid){
        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid,false,false,false,false,false,false);
        PayOrderDTO notFinishOrder = payOrderService.obtainNotFinishPayOrderByChengYunUuid(zuPinContactDTO.getYiFangUuid());
        if(notFinishOrder!=null){
            List<PayItem> payItems = notFinishOrder.getPayItems();
            List<ZuPinContactRentingFeeHistoryDTO> nopayBills = new ArrayList<>();
            for (PayItem payItem : payItems) {
                ZuPinContactRentingFeeHistoryDTO historyDTO = (ZuPinContactRentingFeeHistoryDTO)payItem;
                if(historyDTO.getZuPinContactUuid().equals(zuPinContactUuid)){
                    nopayBills.add(historyDTO);
                }
            }
            return nopayBills;
        }else {
            return null;
        }
    }

}
