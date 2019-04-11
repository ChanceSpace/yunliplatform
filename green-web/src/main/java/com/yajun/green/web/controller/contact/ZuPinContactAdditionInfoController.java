package com.yajun.green.web.controller.contact;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.common.web.view.SelectView;
import com.yajun.green.domain.contact.ZuPinContactChargingType;
import com.yajun.green.facade.dto.contact.*;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.ZuPinContactAdditionalInfoService;
import com.yajun.green.service.ZuPinContactService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by LiuMengKe on 2018/1/13.
 * 合同车辆 备注 充电桩 补充协议
 */
@Controller
public class ZuPinContactAdditionInfoController {

    @Autowired
    private ZuPinContactService zuPinContactService;
    @Autowired
    private ZuPinContactAdditionalInfoService zuPinContactAdditionalInfoService;


    //合同车辆列表备注
    @RequestMapping(value = "/contact/zupincontactexecutecomment.html", method = RequestMethod.GET)
    public String obtainZupinContactExecuteComment(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String executeUUid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "executeuuid", ""));
        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, false, true, false, false, false, false);
        List<ZuPinVehicleExecuteDTO> list = zuPinContactDTO.getExecuteModules();
        String[] comments = null;
        for (ZuPinVehicleExecuteDTO executeDTO : list) {
            if (executeDTO.getUuid().equals(executeUUid)) {
                String executeDTOComment = executeDTO.getComment();
                if (StringUtils.hasText(executeDTOComment)) {
                    comments = executeDTOComment.split("@x@x@");
                }
            }
        }
        model.put("comments", comments);
        return "contact/zupincontactexecutecomment";
    }

    /********************************************合同原件备案***********************************************/

    @RequestMapping(value = "/contact/zupincontactfilebeian.html", method = RequestMethod.GET)
    public String handleExcelImportPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        model.put("zuPinContactUuid", zuPinContactUuid);
        return "contact/zupincontactfilebeian";
    }

    @RequestMapping(value = "/contact/zupincontactfilebeian.html", method = RequestMethod.POST)
    public String handleExcelImportData(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        try {
            DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            MultipartFile importFile = multipartRequest.getFile("contactFile");
            zuPinContactAdditionalInfoService.obtainImportContactFile(zuPinContactUuid, importFile);
        } catch (Exception e) {
            ApplicationLog.error(ZuPinContactManagementController.class, "get contact file error", e);
        }

        return "redirect:zupincontactdetails.html?zuPinContactUuid=" + zuPinContactUuid;
    }

    /********************************************合同补充协议***********************************************/
    //新增补充协议
    @RequestMapping(value = "/contact/zupincontactsupplyform.html", method = RequestMethod.GET)
    public String setZuPinContactSupplyForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");

        ZuPinContactSupplyDTO zuPinContactSupplyDTO = new ZuPinContactSupplyDTO();

        //设置操作人为当前登录用户
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        zuPinContactSupplyDTO.setCaoZuoRen(loginInfo.getXingMing());
        zuPinContactSupplyDTO.setCaoZuoRenUuid(loginInfo.getUserUuid());

        //设置操作时间
        DateTime timestamp = new DateTime();
        String timestamp1 = timestamp.toString();
        zuPinContactSupplyDTO.setTimestamp(timestamp1);

        String supplyUuid = zuPinContactAdditionalInfoService.saveOrUpdateZuPinContactSupply(zuPinContactSupplyDTO, zuPinContactUuid);

        return "redirect:zupincontactsupplycontentform.html?contactSupplyUuid=" + supplyUuid + "&zuPinContactUuid=" + zuPinContactUuid;

    }

    //添加补充协议
    @RequestMapping(value = "/contact/zupincontactsupplyform.html", method = RequestMethod.POST)
    public String saveZuPinContactSupplyForm(HttpServletRequest request, @ModelAttribute("zuPinContactSupplyDTO") ZuPinContactSupplyDTO zuPinContactSupplyDTO, BindingResult errors, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");

        //设置操作人为当前登录用户
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        zuPinContactSupplyDTO.setCaoZuoRen(loginInfo.getXingMing());
        zuPinContactSupplyDTO.setCaoZuoRenUuid(loginInfo.getUserUuid());
        //设置操作时间
        DateTime timestamp = new DateTime();
        String timestamp1 = timestamp.toString();
        zuPinContactSupplyDTO.setTimestamp(timestamp1);

        zuPinContactAdditionalInfoService.saveOrUpdateZuPinContactSupply(zuPinContactSupplyDTO, zuPinContactUuid);
        return "redirect:zupincontactform.html?zuPinContactUuid=" + zuPinContactUuid + "&infoSave=true";
    }

    //删除补充协议
    @RequestMapping(value = "/contact/zupincontactsupplydelete.html", method = RequestMethod.GET)
    public String setZuPinSupplyDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String contactSupplyUuid = ServletRequestUtils.getStringParameter(request, "contactSupplyUuid", "");
        zuPinContactAdditionalInfoService.deleteZuPinContactSupply(contactSupplyUuid);
        return "redirect:zupincontactform.html?zuPinContactUuid=" + zuPinContactUuid;
    }


    /********************************************合同补充协议内容***********************************************/
    //展示补充协议内容框
    @RequestMapping(value = "/contact/zupincontactsupplycontentform.html", method = RequestMethod.GET)
    public String setZuPinContactSupplyContentForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String contactSupplyUuid = ServletRequestUtils.getStringParameter(request, "contactSupplyUuid", "");

        //获取合同车辆信息
        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, false, false, false, true, true, true);

        model.put("saleManName", zuPinContactDTO.getSaleManName());
        model.put("contactNumber", zuPinContactDTO.getContactNumber());
        model.put("zuPinContactUuid", zuPinContactUuid);

        ZuPinContactSupplyContentDTO zuPinContactSupplyContentDTO = new ZuPinContactSupplyContentDTO();
        model.put("zuPinContactSupplyContentDTO", zuPinContactSupplyContentDTO);

        ZuPinContactSupplyDTO zuPinContactSupplyDTO = zuPinContactAdditionalInfoService.obtainZuPinContactSupplyByUuid(contactSupplyUuid);
        model.put("zuPinContactSupplyDTO", zuPinContactSupplyDTO);

        return "contact/zupincontactsupplycontentform";
    }

    //保存补充协议内容
    @RequestMapping(value = "/contact/zupincontactsupplycontentform.html", method = RequestMethod.POST)
    public String saveZuPinContactSupplyContentForm(HttpServletRequest request, @ModelAttribute("zuPinContactSupplyContentDTO") ZuPinContactSupplyContentDTO zuPinContactSupplyContentDTO, BindingResult errors, ZuPinContactSupplyDTO zuPinContactSupplyDTO, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String contactSupplyUuid = ServletRequestUtils.getStringParameter(request, "contactSupplyUuid", "");
        String zuPinContactSupplyUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactSupplyUuid", ""));

        //获取合同车辆信息
        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, false, false, false, false, true, true);

        model.put("saleManName", zuPinContactDTO.getSaleManName());
        model.put("contactNumber", zuPinContactDTO.getContactNumber());
        model.put("zuPinContactUuid", zuPinContactUuid);

        //校验表单
        //生成补充协议内容 并设置id
        zuPinContactAdditionalInfoService.saveOrUpdateZuPinContactSupplyContent(zuPinContactSupplyContentDTO, zuPinContactSupplyUuid);
        ZuPinContactSupplyDTO zuPinContactSupplyDTO1 = zuPinContactAdditionalInfoService.obtainZuPinContactSupplyByUuid(contactSupplyUuid);
        model.put("zuPinContactSupplyDTO", zuPinContactSupplyDTO1);
        zuPinContactSupplyContentDTO.setContent("");

        return "contact/zupincontactsupplycontentform";
    }

    //删除补充协议内容
    @RequestMapping(value = "/contact/zupincontactsupplycontentdelete.html", method = RequestMethod.GET)
    public String setZuPinSupplyContentDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String contactSupplyUuid = ServletRequestUtils.getStringParameter(request, "contactSupplyUuid", "");
        //获取当前的补充协议的id 在删除后跳转只改补充协议
        String contctSupplyContentId = ServletRequestUtils.getStringParameter(request, "contctSupplyContentId", "");

        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ApplicationLog.infoWithCurrentUser(ZuPinContactManagementController.class, loginInfo.getUserUuid(), "删除补充协议" + contctSupplyContentId);
        zuPinContactAdditionalInfoService.deleteZuPinContactSupplyContent(contactSupplyUuid);
        return "redirect:zupincontactsupplycontentform.html?zuPinContactUuid=" + zuPinContactUuid + "&contactSupplyUuid=" + contctSupplyContentId;
    }

    /******************************************合同充电桩信息***************************************/
    @RequestMapping(value = "/contact/zupincontactchargingform.html", method = RequestMethod.GET)
    public String setZuPinContactChargingForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String chargingUuid = ServletRequestUtils.getStringParameter(request, "chargingUuid", "");

        ZuPinContactChargingDTO zuPinContactChargingDTO = null;
        if (StringUtils.hasText(chargingUuid)) {
            zuPinContactChargingDTO = zuPinContactAdditionalInfoService.obtainZuPinContactChargingByUuid(chargingUuid);
        } else {
            zuPinContactChargingDTO = new ZuPinContactChargingDTO();
        }
        model.put("charging", zuPinContactChargingDTO);
        model.put("zuPinContactUuid", zuPinContactUuid);

        //添加充电桩类型
        List<SelectView> zuPinContactChargingTypes = ZuPinContactChargingType.getSelectViews();
        model.put("types", zuPinContactChargingTypes);

        return "contact/zupincontactchargingform";

    }

    //充电
    @RequestMapping(value = "/contact/zupincontactchargingform.html", method = RequestMethod.POST)
    public String saveZuPinContactChargingForm(HttpServletRequest request, @ModelAttribute("charging") ZuPinContactChargingDTO charging, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");

        //设置操作人为当前登录用户
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        charging.setActorManName(loginInfo.getXingMing());
        charging.setActorManUuid(loginInfo.getUserUuid());

        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, false, false, false, false, false, false);

        zuPinContactAdditionalInfoService.saveOrUpdateZuPinContactCharging(charging, zuPinContactDTO.getYiFangUuid(), zuPinContactDTO.getYiFangName());
        return "redirect:zupincontactdetails.html?zuPinContactUuid=" + zuPinContactUuid;

    }

    //删除合同充电桩信息
    @RequestMapping(value = "/contact/zupincontactchargingdelete.html", method = RequestMethod.GET)
    public String setZuPinChargingDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String chargingUuid = ServletRequestUtils.getStringParameter(request, "chargingUuid", "");

        zuPinContactAdditionalInfoService.deleteZuPinContactCharging(chargingUuid);

        return "redirect:zupincontactdetails.html?zuPinContactUuid=" + zuPinContactUuid;
    }

    //处理充电桩
    @RequestMapping(value = "/contact/zupincontactchargingstatus.html", method = RequestMethod.GET)
    public String changeZuPinContactChargingStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String chargingUuid = ServletRequestUtils.getStringParameter(request, "chargingUuid", "");

        ZuPinContactChargingDTO zuPinContactChargingDTO = zuPinContactAdditionalInfoService.obtainZuPinContactChargingByUuid(chargingUuid);

        zuPinContactChargingDTO.setZuPinContactChargingStatus("CHARGING_FINISH");
        zuPinContactChargingDTO.setFinishTime(JodaUtils.todMYHmString(new DateTime()));

        zuPinContactAdditionalInfoService.saveOrUpdateZuPinContactCharging(zuPinContactChargingDTO, "", "");
        return "redirect:zupincontactdetails.html?zuPinContactUuid=" + zuPinContactUuid;
    }


}
