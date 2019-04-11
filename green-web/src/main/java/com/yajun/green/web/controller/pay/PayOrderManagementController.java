package com.yajun.green.web.controller.pay;

import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.web.view.SelectView;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.domain.pay.PayOrderStatus;
import com.yajun.green.domain.pay.PayOrderWay;
import com.yajun.green.facade.dto.contact.ZuPinContactRentingFeeHistoryDTO;
import com.yajun.green.facade.dto.pay.PayOrderDTO;
import com.yajun.green.facade.dto.pay.PayOrderFileDTO;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.PayOrderService;
import com.yajun.green.service.ZuPinContactPayService;
import com.yajun.green.service.ZuPinContactService;
import com.yajun.green.web.pagging.PayOrderCarrierOverviewPaging;
import com.yajun.user.facade.dto.CompanyDTO;
import com.yajun.user.service.UserDubboService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/10/16.
 */
@Controller
public class PayOrderManagementController {

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private UserDubboService userDubboService;
    @Autowired
    private ZuPinContactService zuPinContactService;
    @Autowired
    private ZuPinContactPayService zuPinContactPayService;

    /*********************************************订单详情页面****************************************************/

    // localhost:8080/my/carrier/payorderdetail.html?payOrderNumber=YL201710180947410167497162
    //详情展示页
    @RequestMapping("/carrier/payorderdetail.html")
    public String payOrderDetail(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        String payOrderNumber = ServletRequestUtils.getStringParameter(request, "payOrderNumber","");
        String userUuid = ServletRequestUtils.getStringParameter(request,"userUuid", "");
        PayOrderDTO payOrderDTO = payOrderService.obtainPayOrder(payOrderNumber, true, true, true, true);

        model.put("order", payOrderDTO);
        model.put("payOrderNumber", payOrderNumber);
        model.put("applicationHost", ApplicationEventPublisher.applicationFileRequestPath);
        model.put("userUuid", userUuid);

        //页面跳转
        Boolean carrierList = ServletRequestUtils.getBooleanParameter(request, "carrierList", false);
        model.put("carrierList",carrierList);
        Boolean userList = ServletRequestUtils.getBooleanParameter(request, "userList", false);
        model.put("userList", userList);
        String userType = ServletRequestUtils.getStringParameter(request, "userType", "");
        model.put("userType", userType);
        return "pay/payorderdetail";
    }
    //附件上传

    @RequestMapping(value = "/carrier/payorderdetailfile.html",method = RequestMethod.POST)
    public String payOrderDetailFileUpload(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("payOrderFile") PayOrderFileDTO payOrderFileDTO, ModelMap model){
        String orderUuid = ServletRequestUtils.getStringParameter(request, "orderUuid", "");
        String payOrderNumber = ServletRequestUtils.getStringParameter(request, "payOrderNumber","");

        //保存附件
        List<MultipartFile> fujianFiles = null;
        try {
            DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            fujianFiles = multipartRequest.getFiles("fuJian");
        } catch (Exception e) {
            ApplicationLog.error(PayOrderManagementController.class, "get fujian from pay order offline error", e);
        }

        payOrderService.savePayOrderDetailFile(orderUuid, payOrderNumber, fujianFiles);
        return "redirect:payorderdetail.html?payOrderNumber=" + payOrderNumber;
    }

    //我的账户
    @RequestMapping(value = "/carrier/payordercarrieroverview.html")
    public String payOrderCarrierOverview(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        String startTime = ServletRequestUtils.getStringParameter(request, "startTime", new LocalDate().minusYears(1).toString());
        String endTime = ServletRequestUtils.getStringParameter(request, "endTime", new LocalDate().plusDays(1).toString());
        String keyWords = ServletRequestUtils.getStringParameter(request, "keyWords","" ).toUpperCase();
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String selType = ServletRequestUtils.getStringParameter(request, "selPayType", "ALL");

        PayOrderCarrierOverviewPaging paging = new PayOrderCarrierOverviewPaging(payOrderService);
        constructCarrierPayOrderInfoPaging(paging, current, keyWords, startTime, endTime,selType);

        List<PayOrderDTO> payOrderDTOS = paging.getItems();
        model.put("orders", payOrderDTOS);
        model.put("paging", paging);

        //收支统计
        List<PayOrderDTO> payOrderDTOS1 = payOrderService.obtainAllPayOrder(paging.getKeyWords(), paging.getUserUuid(), paging.getUserType(), paging.getStartTime(), paging.getEndTime(), "ALL");
        //一级承运商收入
        BigDecimal totalFeeBelongIn = new BigDecimal(0);
        // 一级承运商支出
        BigDecimal totalFeeBelongOut = new BigDecimal(0);
        //二级承运商收入
        BigDecimal totalFeelChengYunIn = new BigDecimal(0);
        //二级承运商支出
        BigDecimal totalFeelChengYunOut = new BigDecimal(0);

        for (PayOrderDTO payOrderDTO : payOrderDTOS1) {
            if (PayOrderStatus.O_FINSIH.name().equals(payOrderDTO.getPayOrderStatus())){
                if (BigDecimal.valueOf(0).compareTo(payOrderDTO.getTotalFeeBelone())==-1) {
                    totalFeeBelongIn = totalFeeBelongIn.add(payOrderDTO.getTotalFeeBelone());
                }
                if (BigDecimal.valueOf(0).compareTo(payOrderDTO.getTotalFeeBelone())==1) {
                    totalFeeBelongOut= totalFeeBelongOut.add(payOrderDTO.getTotalFeeBelone());
                }
                if (BigDecimal.valueOf(0).compareTo(payOrderDTO.getTotalFeeChengYun())==-1){
                    totalFeelChengYunIn = totalFeelChengYunIn.add(payOrderDTO.getTotalFeeChengYun());
                }

                if (BigDecimal.valueOf(0).compareTo(payOrderDTO.getTotalFeeChengYun())==1){
                    totalFeelChengYunOut = totalFeelChengYunOut.add(payOrderDTO.getTotalFeeChengYun());
                }
            }
        }
        model.put("totalFeeBelongIn", totalFeeBelongIn);
        model.put("totalFeeBelongOut", totalFeeBelongOut);
        model.put("totalFeelChengYunIn", totalFeelChengYunIn);
        model.put("totalFeelChengYunOut", totalFeelChengYunOut);

        //账户余额
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        CompanyDTO companyDTO = userDubboService.obtainTongBuCompanyInfoByUuid(loginInfo.getCompanyUuid());
        model.put("company", companyDTO);

        List<SelectView> payTypes = PayOrderWay.getSelectViews();
        model.put("payTypes", payTypes);

        return "pay/payordercarrieroverview";
    }

    private void constructCarrierPayOrderInfoPaging(PayOrderCarrierOverviewPaging paging,int current, String keyWords, String startTime, String endTime, String selType){
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        paging.setCurrentPageNumber(current);
        paging.setKeyWords(keyWords);
        paging.setStartTime(startTime);
        paging.setEndTime(endTime);
        paging.setUserType(loginInfo.getCompanyType());
        paging.setUserUuid(loginInfo.getCompanyUuid());
        paging.setSelPayType(selType);
    }

    //取消订单页面
    @RequestMapping(value = "/carrier/cancelHistory.html",method = RequestMethod.GET)
    public String cancelHistory(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        String historyUuid = ServletRequestUtils.getStringParameter(request, "historyUuid", "");
        String selectCarrierUuid = ServletRequestUtils.getStringParameter(request, "selectCarrierUuid", "");
        model.put("historyUuid",historyUuid);
        model.put("selectCarrierUuid",selectCarrierUuid);
        return "pay/cancelhistory";
    }

    //取消订单页面
    @RequestMapping(value = "/carrier/cancelHistory.html",method = RequestMethod.POST)
    public String cancelHistorySubmit(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        String historyUuid = ServletRequestUtils.getStringParameter(request, "historyUuid", "");
        String selectCarrierUuid = ServletRequestUtils.getStringParameter(request, "selectCarrierUuid", "");
        String comment = ServletRequestUtils.getStringParameter(request, "comment", "");
        ZuPinContactRentingFeeHistoryDTO zuPinContactRentingFeeHistoryDTO = zuPinContactService.obtainZuPinContactRentingFeeHistoryDTOByUuid(historyUuid);

        //得到history
        boolean validate = validateHistoryCancel(zuPinContactRentingFeeHistoryDTO);
        if(zuPinContactRentingFeeHistoryDTO!=null){
            zuPinContactPayService.saveHistoryCancel(historyUuid,comment);
            return "redirect:paymentmanagement.html?selectCarrierUuid="+selectCarrierUuid;
        }
        return  "common/error";
    }

    //校验是否能够被取消
    private boolean validateHistoryCancel(ZuPinContactRentingFeeHistoryDTO zuPinContactRentingFeeHistoryDTO){
        String zuPinContactUuid = zuPinContactRentingFeeHistoryDTO.getZuPinContactUuid();
        return zuPinContactPayService.obtainCancelValidationOfHistory(zuPinContactRentingFeeHistoryDTO,zuPinContactUuid);
    }
}
