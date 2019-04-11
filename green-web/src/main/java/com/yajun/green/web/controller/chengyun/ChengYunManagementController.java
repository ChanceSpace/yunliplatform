package com.yajun.green.web.controller.chengyun;

import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.web.view.SelectView;
import com.yajun.green.domain.pay.PayOrderStatus;
import com.yajun.green.domain.pay.PayOrderWay;
import com.yajun.green.facade.dto.chengyun.CarrierRentFeeHistoryDTO;
import com.yajun.green.facade.dto.pay.PayOrderDTO;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.ChengYunService;
import com.yajun.green.service.PayOrderService;
import com.yajun.green.web.pagging.*;
import com.yajun.green.web.resquest.HandleResponse;
import com.yajun.user.facade.dto.CompanyDTO;
import com.yajun.user.service.UserDubboService;
import com.yajun.vms.service.VmsDubboService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by chance on 2017/9/12.
 */
@Controller
public class ChengYunManagementController {

    @Autowired
    private ChengYunService chengYunService;
    @Autowired
    private UserDubboService userDubboService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private VmsDubboService vmsDubboService;

    //提车换车自动补全车牌号
    @RequestMapping(value = "/carrier/vehicleautocomplete.html")
    public void vehiceAutoComplete(HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String searchWords = ServletRequestUtils.getStringParameter(request, "searchWords", "");
        String newSearchWords = searchWords.toUpperCase();
        String token = SecurityUtils.userToken.get();
        //查询数据
        LoginInfo loginUser = SecurityUtils.currentLoginUser();
        ApplicationLog.info(ChengYunManagementController.class, loginUser.getUserUuid() + "search customer with words " + searchWords);
        String responseJson = vmsDubboService.obtainTongBuVehicleAutoComplete(token, newSearchWords);

        //返回结果
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(responseJson);
        writer.flush();
        writer.close();
    }

    /******************************************承运商账户管理**********************************************/

    //承运商客户列表
    @RequestMapping(value = "/carrier/carrierbalanceinfomanagement.html")
    public String carrierBalanceInfoManagement(HttpServletRequest request, HandleResponse response, ModelMap model){
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String keywords = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "keywords", ""));
        int qianFeiTotal = ServletRequestUtils.getIntParameter(request, "qianFeiTotal", 0);
        int qianFeiDate = ServletRequestUtils.getIntParameter(request, "qianFeiDate", 0);
        boolean chongZhiMessage = ServletRequestUtils.getBooleanParameter(request, "chongZhiMessage", false);
        boolean tiXianMessage = ServletRequestUtils.getBooleanParameter(request, "tiXianMessage", false);

        CarrierBalanceInfoOverviewPaging paging = new CarrierBalanceInfoOverviewPaging(userDubboService);
        constructCarrierUserInfoPaging(paging, current, keywords, qianFeiTotal, qianFeiDate);
        List<CompanyDTO> companyDTOs = paging.getItems();

        model.put("users", companyDTOs);
        model.put("paging", paging);

        //页面跳转
        boolean carrierList = new Boolean(true);
        model.put("carrierList", carrierList);
        model.put("chongZhiMessage", chongZhiMessage);
        model.put("tiXianMessage", tiXianMessage);

        return "carrier/carrierbalanceinfomanagement";
    }
    private void constructCarrierUserInfoPaging(CarrierBalanceInfoOverviewPaging paging, int current, String keywords, int qianFeiTotal, int qianFeiDate) {
        paging.setCurrentPageNumber(current);
        paging.setKeywords(keywords);
        paging.setQianFeiTotal(qianFeiTotal);
        paging.setQianFeiDate(qianFeiDate);
    }

    //客户列表交易详情
    @RequestMapping(value = "/carrier/carrierbalanceoverview.html")
    public String carrierBalanceHistory(HttpServletRequest request, HandleResponse response, ModelMap model){
        String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);

        String startTime = ServletRequestUtils.getStringParameter(request, "startTime", new LocalDate().minusYears(1).toString());
        String endTime = ServletRequestUtils.getStringParameter(request, "endTime", new LocalDate().plusDays(1).toString());
        String userType = ServletRequestUtils.getStringParameter(request, "userType", "");
        String keyWords = ServletRequestUtils.getStringParameter(request, "keyWords","" );
        String selType = ServletRequestUtils.getStringParameter(request, "selPayType", "ALL");

        PayOrderCarrierOverviewPaging paging = new PayOrderCarrierOverviewPaging(payOrderService);
        constructPayOrderInfoPaging(paging,current,keyWords,userUuid,userType,startTime,endTime,selType);

        //收支统计
        List<PayOrderDTO> payOrders = payOrderService.obtainAllPayOrder(paging.getKeyWords(),paging.getUserUuid(),paging.getUserType(),paging.getStartTime(),paging.getEndTime(),"ALL");
        //一级承运商收入
        BigDecimal totalFeeBelongIn = new BigDecimal(0);
        // 一级承运商支出
        BigDecimal totalFeeBelongOut = new BigDecimal(0);
        //二级承运商收入
        BigDecimal totalFeelChengYunIn = new BigDecimal(0);
        //二级承运商支出
        BigDecimal totalFeelChengYunOut = new BigDecimal(0);

        for (PayOrderDTO payOrder : payOrders) {
            if (PayOrderStatus.O_FINSIH.name().equals(payOrder.getPayOrderStatus())) {
                if (BigDecimal.valueOf(0).compareTo(payOrder.getTotalFeeBelone()) == -1) {
                    totalFeeBelongIn = totalFeeBelongIn.add(payOrder.getTotalFeeBelone());
                }
                if (BigDecimal.valueOf(0).compareTo(payOrder.getTotalFeeBelone()) == 1) {
                    totalFeeBelongOut = totalFeeBelongOut.add(payOrder.getTotalFeeBelone());
                }
                if (BigDecimal.valueOf(0).compareTo(payOrder.getTotalFeeChengYun()) == -1) {
                    totalFeelChengYunIn = totalFeelChengYunIn.add(payOrder.getTotalFeeChengYun());
                }
                if (BigDecimal.valueOf(0).compareTo(payOrder.getTotalFeeChengYun()) == 1) {
                    totalFeelChengYunOut = totalFeelChengYunOut.add(payOrder.getTotalFeeChengYun());
                }
            }
        }
        model.put("totalFeeBelongIn", totalFeeBelongIn);
        model.put("totalFeeBelongOut", totalFeeBelongOut);
        model.put("totalFeelChengYunIn", totalFeelChengYunIn);
        model.put("totalFeelChengYunOut", totalFeelChengYunOut);

        List<PayOrderDTO> payOrderDTOS = paging.getItems();
        model.put("carrierrentfeehistorys", payOrderDTOS);
        model.put("paging", paging);
        model.put("userUuid",userUuid);

        model.put("applicationHost", ApplicationEventPublisher.applicationFileRequestPath);
        model.put("userType",userType);
        model.put("payorder","payorder");

        //页面跳转
        Boolean carrierList = ServletRequestUtils.getBooleanParameter(request, "carrierList", true);
        model.put("carrierList",carrierList);
        Boolean userList = ServletRequestUtils.getBooleanParameter(request, "userList", false);
        model.put("userList", userList);

        ////账户余额
        CompanyDTO companyDTO = userDubboService.obtainTongBuCompanyInfoByUuid(userUuid);
        model.put("company", companyDTO);

        List<SelectView> payTypes = PayOrderWay.getSelectViews();
        model.put("payTypes", payTypes);

        return "carrier/carrierbalanceoverview";
    }

    private void constructPayOrderInfoPaging(PayOrderCarrierOverviewPaging paging, int current, String keyWords, String userUuid,String userType, String startTime, String endTime, String selType){
        paging.setCurrentPageNumber(current);
        paging.setUserUuid(userUuid);
        paging.setStartTime(startTime);
        paging.setEndTime(endTime);
        paging.setUserType(userType);
        paging.setKeyWords(keyWords);
        paging.setSelPayType(selType);
    }
    //线下充值页
    @RequestMapping(value = "/carrier/carrierbalancechongzhi.html",method = RequestMethod.GET)
    public String obtainCarrierBalanceForm(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");

        model.put("today", new LocalDate().toString());
        model.put("userUuid", userUuid);

        return "carrier/carrierbalancechongzhi";
    }
    //线下充值
    @RequestMapping(value = "/carrier/carrierbalancechongzhi.html",method = RequestMethod.POST)
    public String saveCarrierBalanceHistory(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("carrierBalanceHistory") CarrierRentFeeHistoryDTO carrierRentFeeHistoryDTO, ModelMap model){
        String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");
        //保存图片
        List<MultipartFile> carrierBalancePictures = null;
        try {
            DefaultMultipartHttpServletRequest multipartHttpServletRequest = (DefaultMultipartHttpServletRequest) request;
            carrierBalancePictures = multipartHttpServletRequest.getFiles("picture");
        } catch (Exception e) {
            ApplicationLog.error(ChengYunManagementController.class, "get carrierbalancehistory picture error", e);
            model.put("chongZhiMessage", false);
        }
        chengYunService.updateCarrierBalance(userUuid, carrierRentFeeHistoryDTO, carrierBalancePictures,true);
        return "redirect:carrierbalanceinfomanagement.html?userUuid=" + userUuid + "&chongZhiMessage=true";
    }

    //线下提现页
    @RequestMapping(value = "/carrier/carrierbalancetixian.html",method = RequestMethod.GET)
    public String obtainCarrierBalanceTiXianForm(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");
        String currentBalance = ServletRequestUtils.getStringParameter(request, "currentBalance", "");

        model.put("today", new LocalDate().toString());
        model.put("userUuid", userUuid);
        model.put("currentBalance", currentBalance);

        return "carrier/carrierbalancetixian";
    }
    //线下提现
    @RequestMapping(value = "/carrier/carrierbalancetixian.html",method = RequestMethod.POST)
    public String saveCarrierBalanceTiXianHistory(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("carrierBalanceHistory") CarrierRentFeeHistoryDTO carrierRentFeeHistoryDTO, ModelMap model){
        String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");
        //保存图片
        List<MultipartFile> carrierBalancePictures = null;
        try {
            DefaultMultipartHttpServletRequest multipartHttpServletRequest = (DefaultMultipartHttpServletRequest) request;
            carrierBalancePictures = multipartHttpServletRequest.getFiles("picture");
        } catch (Exception e) {
            ApplicationLog.error(ChengYunManagementController.class, "get carrierbalancehistory picture error", e);
            model.put("tiXianMessage", false);
        }
        //判断是否为充值
        chengYunService.updateCarrierBalance(userUuid, carrierRentFeeHistoryDTO, carrierBalancePictures, false);
        return "redirect:carrierbalanceinfomanagement.html?userUuid=" + userUuid + "&tiXianMessage=true" ;
    }

    //单独添加附件 好像暂时没有用到 先不管了
    @RequestMapping(value = "/carrier/carrierbalancehistoryfile.html",method = RequestMethod.GET)
    public String carrierBalanceHistoryFileForm(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");
        String rentFeeHisUuid = ServletRequestUtils.getStringParameter(request, "rentFeeHisUuid", "");

        model.put("userUuid", userUuid);
        model.put("rentFeeHisUuid", rentFeeHisUuid);

        return "carrier/carrierbalancehistoryfile";
    }

    @RequestMapping(value = "/carrier/carrierbalancehistoryfile.html",method = RequestMethod.POST)
    public String carrierBalanceHistoryFileUpload(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("carrierBalanceHistory") CarrierRentFeeHistoryDTO carrierRentFeeHistoryDTO, ModelMap model){
        String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");

        //保存图片
        MultipartFile carrierBalancePictures = null;
        try {
            DefaultMultipartHttpServletRequest multipartHttpServletRequest = (DefaultMultipartHttpServletRequest) request;
            carrierBalancePictures = multipartHttpServletRequest.getFile("picture");
        } catch (Exception e) {
            ApplicationLog.error(ChengYunManagementController.class, "get carrierbalancehistory picture error", e);
        }
        return "redirect:carrierbalanceoverview.html?userUuid=" + userUuid;
    }

}
