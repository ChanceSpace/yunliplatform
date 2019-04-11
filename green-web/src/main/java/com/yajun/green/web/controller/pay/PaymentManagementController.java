package com.yajun.green.web.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.common.exception.ApplicationException;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.domain.pay.PayOrderSource;
import com.yajun.green.domain.pay.PayOrderWay;
import com.yajun.green.facade.dto.contact.PayOrderManagementShowDTO;
import com.yajun.green.facade.dto.pay.PayOrderDTO;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.PayOrderService;
import com.yajun.green.service.PaymentService;
import com.yajun.green.service.ZuPinContactPayService;
import com.yajun.green.service.ZuPinContactService;
import com.yajun.user.facade.dto.CompanyDTO;
import com.yajun.user.service.UserDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chance on 2017/10/19.
 */
@Controller
public class PaymentManagementController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private UserDubboService userDubboService;
    @Autowired
    private ZuPinContactPayService zuPinContactPayService;

    /*******************************************未支付订单和款项查询模块******************************************/

    @RequestMapping("/carrier/paymentmanagement.html")
    public String payOrderManagement(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        String selectCarrierUuid = ServletRequestUtils.getStringParameter(request, "selectCarrierUuid", "");

        //获取当前用户对应的所有用户uuid
        if (SecurityUtils.hasRentingAccess()) {
            List<CompanyDTO> companies = zuPinContactPayService.obtainAllCompanyWithOnCreateBillOnLoginUser();
            model.put("users", companies);
        } else {
            selectCarrierUuid = SecurityUtils.currentLoginUser().getCompanyUuid();
        }
        model.put("selectCarrierUuid", selectCarrierUuid);

        PayOrderDTO notFinishOrder = payOrderService.obtainNotFinishPayOrderByChengYunUuid(selectCarrierUuid);
        model.put("notFinishOrder", notFinishOrder);

        List<PayOrderManagementShowDTO> willPayOrders = zuPinContactPayService.obtainAllOnCreateBill(selectCarrierUuid);
        //去除已经有的在订单中的
        if (notFinishOrder != null) {
            for (PayOrderManagementShowDTO history : willPayOrders) {
                history.removeAlreadyTakenPayment(notFinishOrder.getPayItems());
            }
        }
        model.put("willPayOrders", willPayOrders);

        return "pay/paymentmanagement";
    }

    /******************************************付款流程***************************************************/

    //1， 选择支付的方式，如果是线下支付，则显示余额支付的页面，进入1.1流程，如果选择线上支付，进入流程2.1
    //1.1 填写线下支付的信息，保存，余额支付
    //2.1 根据选择的支付方式，条用相应的支付页面，生成二维码，扫码支付完成后，进入我么的支付的等待页面。进入流程2.2
    //2.2 进入支付等待页面后，每隔3秒去刷新一次支付结果（周期为60秒），如果支付结果成功或者失败，返回结果页面，进去流程2.3
    //2.3 根据支付结果显示相应的支付页面

    //http://localhost:8080/my/carrier/payorderfirststep.html?totalFee=30000.00&chengYunUuid=201701010101_1234567890123404&itemUuids=20171017_0858_3109813946996242,20171017_0858_6006739345578845,20171017_0858_7303854724935709,20171017_0858_7332500417819751
    @RequestMapping("/carrier/paymentfirststep.html")
    public String payOrderFirstStep(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        //如果payOrderNumber的值存在，就代表订单继续支付
        String payOrderNumber = ServletRequestUtils.getStringParameter(request, "payOrderNumber", "");

        double totalFee = 0;
        String chengYunUuid = "";

        if (StringUtils.hasText(payOrderNumber)) {
            PayOrderDTO payOrder = payOrderService.obtainPayOrder(payOrderNumber, true, false, false,false);
            totalFee = payOrder.getTotalFee().doubleValue();
            chengYunUuid = payOrder.getChengYunUuid();
            model.put("payOrderNumber", payOrderNumber);
        } else {
            totalFee = ServletRequestUtils.getDoubleParameter(request, "totalFee", 0);
            String itemUuids = ServletRequestUtils.getStringParameter(request, "itemUuids");
            chengYunUuid = ServletRequestUtils.getStringParameter(request, "chengYunUuid");
            model.put("itemUuids", itemUuids);
        }

        CompanyDTO chengYun = userDubboService.obtainTongBuCompanyInfoByUuid(chengYunUuid);
        model.put("chengYun", chengYun);

        model.put("totalFee", totalFee);
        model.put("actorUuid", SecurityUtils.currentLoginUser().getUserUuid());
        return "pay/paymentfirststep";
    }

    /******************************************线下支付***************************************************/

    @RequestMapping("/carrier/paymentoffline.html")
    public String payOrderOffline(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        //如果payOrderNumber的值存在，就代表订单继续支付
        String payOrderNumber = ServletRequestUtils.getStringParameter(request, "payOrderNumber", "");

        double totalFee = ServletRequestUtils.getDoubleParameter(request, "totalFee", 0);
        String itemUuids = ServletRequestUtils.getStringParameter(request, "itemUuids");
        String orderNote = ServletRequestUtils.getStringParameter(request, "orderNote", "");
        String chengYunUuid = ServletRequestUtils.getStringParameter(request, "chengYunUuid", "");
        String actorUuid = ServletRequestUtils.getStringParameter(request, "actorUuid", "");
        String orderWay = ServletRequestUtils.getStringParameter(request, "orderWay", "");
        String jiaoYiNumber = ServletRequestUtils.getStringParameter(request, "jiaoYiNumber", "");

        //余额支付判断余额是否充足
        CompanyDTO chengYun = userDubboService.obtainTongBuCompanyInfoByUuid(chengYunUuid);
        if (PayOrderWay.valueOf(orderWay).equals(PayOrderWay.O_OFFLINE) && chengYun.getCurrentBalance().doubleValue() < totalFee) {
            return "redirect:paymentmoneynotenought.html";
        }

        List<MultipartFile> fujianFiles = null;
        try {
            DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            fujianFiles = multipartRequest.getFiles("fuJians");
        } catch (Exception e) {
            ApplicationLog.error(PayOrderManagementController.class, "get fujian from pay order offline error", e);
        }

        String[] temp = StringUtils.delimitedListToStringArray(itemUuids, ",");
        List<String> items = Arrays.asList(temp);
        payOrderNumber = paymentService.updateBeginContactPayOrderOffline(payOrderNumber, jiaoYiNumber, chengYunUuid, actorUuid, PayOrderSource.O_CONTACT, PayOrderWay.valueOf(orderWay), orderNote, BigDecimal.valueOf(totalFee), items, fujianFiles);

        return "redirect:paymentfinish.html?payOrderNumber=" + payOrderNumber + "&totalFee=" + totalFee;
    }

    /******************************************线上支付***************************************************/

    @RequestMapping("/carrier/paymentonlinefirst.html")
    public String payOrderOnlineFirst(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        //如果payOrderNumber的值存在，就代表订单继续支付
        String payOrderNumber = ServletRequestUtils.getStringParameter(request, "payOrderNumber", "");

        double totalFee = ServletRequestUtils.getDoubleParameter(request, "totalFee", 0);
        String itemUuids = ServletRequestUtils.getStringParameter(request, "itemUuids");
        String chengYunUuid = ServletRequestUtils.getStringParameter(request, "chengYunUuid", "");
        String actorUuid = ServletRequestUtils.getStringParameter(request, "actorUuid", "");
        String orderWay = ServletRequestUtils.getStringParameter(request, "orderWay", "");

        String[] temp = StringUtils.delimitedListToStringArray(itemUuids, ",");
        List<String> items = Arrays.asList(temp);
        PayOrderDTO payOrder = paymentService.updateBeginContactPayOrderOnline(payOrderNumber, chengYunUuid, actorUuid, PayOrderSource.O_CONTACT, PayOrderWay.valueOf(orderWay), BigDecimal.valueOf(totalFee), items);
        payOrderNumber = payOrder.getOrderNumber();

        //真实环境直接跳转到支付平台，支付完成后跳转等待检查页面
        //如果是测试环境，直接跳转到等待测试页面，点击返回后完成支付
        if (ApplicationEventPublisher.applicationPaymentLive) {
            String payURL = OnlinePaymentHandler.handleOnlinePaymentURL(payOrder, true);
            if (StringUtils.hasText(payURL)) {
                return "redirect:" + payURL;
            }
            return "redirect:paymentonlinefinishwaiting.html";
        }

        return "redirect:paymentwaiting.html?payOrderNumber=" + payOrderNumber;
    }

    @RequestMapping("/carrier/paymentwaiting.html")
    public String payOrderWaiting(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String payOrderNumber = ServletRequestUtils.getStringParameter(request, "payOrderNumber", "");

        if (!StringUtils.hasText(payOrderNumber)) {
            throw new ApplicationException("pay order waiting not find the payOrderNumber or jiaoYiNumber info");
        }

        model.put("payOrderNumber", payOrderNumber);
        model.put("applicationPaymentLive", ApplicationEventPublisher.applicationPaymentLive);

        return "pay/paymentwaiting";
    }

    @RequestMapping("/carrier/paymentonlinesecond.html")
    public void payOrderOnlineChecking(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String payOrderNumber = ServletRequestUtils.getStringParameter(request, "payOrderNumber", "");

        if (!StringUtils.hasText(payOrderNumber)) {
            throw new ApplicationException("pay order waiting not find the payOrderNumber info");
        }

        JSONObject total = new JSONObject();
        OnlinePaymentResult result = OnlinePaymentHandler.handleOnlinePaymentChecking(payOrderNumber);
        if (result.getResult() == 0) {
            BigDecimal totalFee = paymentService.updateFinishContactPayOrderOnline(payOrderNumber, result.getJiaoYiNumber());
            total.put("FLAG", "YES");
            total.put("totalFee", totalFee);
            total.put("jiaoYiNumber", result.getJiaoYiNumber());

        } else if(result.getResult() == 1) {
            total.put("FLAG", "WAITING");

        } else if(result.getResult() == 2) {
            total.put("FLAG", "NO");
        }

        //返回结果
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(total.toJSONString());
        writer.flush();
        writer.close();
    }

    /*********************************************在线测试假测试****************************************************/

    @RequestMapping("/carrier/paymentonlinesecondfake.html")
    public String payOrderOnlineSecondFake(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String payOrderNumber = ServletRequestUtils.getStringParameter(request, "payOrderNumber", "");
        String jiaoYiNumber = ServletRequestUtils.getStringParameter(request, "jiaoYiNumber", "");

        if (!StringUtils.hasText(payOrderNumber) || !StringUtils.hasText(jiaoYiNumber)) {
            throw new ApplicationException("pay order waiting not find the payOrderNumber or jiaoYiNumber info");
        }
        if (ApplicationEventPublisher.applicationPaymentLive) {
            throw new ApplicationException("not live environment use fake payment exception");
        }

        BigDecimal totalFee = paymentService.updateFinishContactPayOrderOnline(payOrderNumber, jiaoYiNumber);
        return "redirect:paymentfinish.html?payOrderNumber=" + payOrderNumber + "&totalFee=" + totalFee;
    }

    /*********************************************支付结果页面****************************************************/

    @RequestMapping("/carrier/paymentmoneynotenought.html")
    public String payOrderMoneyNotEnough(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        return "pay/paymentmoneynotenough";
    }

    @RequestMapping("/carrier/paymentonlinefinishwaiting.html")
    public String payOrderFinishWaiting(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        return "pay/paymentonlinefinishwaiting";
    }

    @RequestMapping("/carrier/paymenterror.html")
    public String payOrderError(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        return "pay/paymentonlineerror";
    }

    @RequestMapping("/carrier/paymentfinish.html")
    public String payOrderFinish(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String payOrderNumber = ServletRequestUtils.getStringParameter(request, "payOrderNumber");
        double totalFee = ServletRequestUtils.getDoubleParameter(request, "totalFee", 0);

        model.put("payOrderNumber", payOrderNumber);
        model.put("totalFee", totalFee);
        return "pay/paymentfinish";
    }

    /*********************************************取消订单****************************************************/

    @RequestMapping("/carrier/paymentcancel.html")
    public String payOrderCancel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String payOrderNumber = ServletRequestUtils.getStringParameter(request, "payOrderNumber");
        String selectCarrierUuid = ServletRequestUtils.getStringParameter(request, "selectCarrierUuid", "");

        //如果已经支付成功，不能取消，直接到付款成功页面
        OnlinePaymentResult result = OnlinePaymentHandler.handleOnlinePaymentChecking(payOrderNumber);
        if (result.getResult() == 0) {
            BigDecimal totalFee = paymentService.updateFinishContactPayOrderOnline(payOrderNumber, result.getJiaoYiNumber());
            return "redirect:paymentfinish.html?payOrderNumber=" + payOrderNumber + "&totalFee=" + totalFee;
        }

        boolean canDelete = paymentService.cancelPayOrder(payOrderNumber);
        if (canDelete) {
            return "redirect:paymentmanagement.html?selectCarrierUuid=" + selectCarrierUuid;
        }
        model.put("payOrderNumber", payOrderNumber);
        return "pay/paymentalreadyfinish";
    }

    /*********************************************手动更新订单状态************************************************/

    @RequestMapping("/carrier/paymentupdate.html")
    public String payOrderUpdate(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String payOrderNumber = ServletRequestUtils.getStringParameter(request, "payOrderNumber");
        String selectCarrierUuid = ServletRequestUtils.getStringParameter(request, "selectCarrierUuid", "");

        //如果已经支付成功，不能跳转
        OnlinePaymentResult result = OnlinePaymentHandler.handleOnlinePaymentChecking(payOrderNumber);
        if (result.getResult() == 0) {
            BigDecimal totalFee = paymentService.updateFinishContactPayOrderOnline(payOrderNumber, result.getJiaoYiNumber());
            return "redirect:paymentfinish.html?payOrderNumber=" + payOrderNumber + "&totalFee=" + totalFee;
        }

        return "redirect:paymentmanagement.html?selectCarrierUuid=" + selectCarrierUuid;
    }

}
