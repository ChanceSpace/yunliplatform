package com.yajun.green.web.controller.contact.xiaoshou;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.web.view.SelectView;
import com.yajun.green.domain.xiaoshou.XiaoShouContactChargingType;
import com.yajun.green.facade.dto.contact.*;
import com.yajun.green.facade.dto.contact.xiaoshou.*;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.XiaoShouContactAdditionalInfoService;
import com.yajun.green.service.XiaoShouContactService;
import com.yajun.green.web.controller.contact.ZuPinContactManagementController;
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
public class XiaoShouContactAdditionInfoController {

    @Autowired
    private XiaoShouContactService xiaoShouContactService;
    @Autowired
    private XiaoShouContactAdditionalInfoService xiaoShouContactAdditionalInfoService;


    //合同车辆列表备注
    @RequestMapping(value = "/contact/xiaoshoucontactexecutecomment.html", method = RequestMethod.GET)
    public String obtainZupinContactExecuteComment(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String xiaoShouContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", ""));
        String executeUUid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "executeuuid", ""));
        ZuPinXiaoShouContactDTO xiaoShouContactDTO = xiaoShouContactService.obtainZuPinXiaoShouContactByUuid(xiaoShouContactUuid, false, true, false, false, false, false,false);
        List<ZuPinXiaoShouVehicleExecuteDTO> list = xiaoShouContactDTO.getVehicleExecutes();
        String[] comments = null;
        for (ZuPinXiaoShouVehicleExecuteDTO executeDTO : list) {
            if (executeDTO.getUuid().equals(executeUUid)) {
                String executeDTOComment = executeDTO.getComment();
                if (StringUtils.hasText(executeDTOComment)) {
                    comments = executeDTOComment.split("@x@x@");
                }
            }
        }
        model.put("comments", comments);
        return "xiaoshou/xiaoshoucontactexecutecomment";
    }

    /********************************************合同原件备案***********************************************/

    @RequestMapping(value = "/contact/xiaoshoucontactfilebeian.html", method = RequestMethod.GET)
    public String handleExcelImportPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String xiaoShouContactUuid = ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", "");
        model.put("xiaoShouContactUuid", xiaoShouContactUuid);
        return "xiaoshou/xiaoshoucontactfilebeian";
    }

    @RequestMapping(value = "/contact/xiaoshoucontactfilebeian.html", method = RequestMethod.POST)
    public String handleExcelImportData(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String xiaoShouContactUuid = ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", "");
        try {
            DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            MultipartFile importFile = multipartRequest.getFile("contactFile");
            xiaoShouContactAdditionalInfoService.obtainImportContactFile(xiaoShouContactUuid, importFile);
        } catch (Exception e) {
            ApplicationLog.error(ZuPinContactManagementController.class, "get contact file error", e);
        }

        return "redirect:xiaoshoucontactdetails.html?xiaoShouContactUuid=" + xiaoShouContactUuid;
    }

    /********************************************合同补充协议***********************************************/
    //新增补充协议
    @RequestMapping(value = "/contact/xiaoshoucontactsupplyform.html", method = RequestMethod.GET)
    public String setZuPinContactSupplyForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String xiaoShouContactUuid = ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", "");

        ZuPinXiaoShouContactSupplyDTO xiaoShouContactSupplyDTO = new ZuPinXiaoShouContactSupplyDTO();

        //设置操作人为当前登录用户
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        xiaoShouContactSupplyDTO.setCaoZuoRen(loginInfo.getXingMing());
        xiaoShouContactSupplyDTO.setCaoZuoRenUuid(loginInfo.getUserUuid());

        //设置操作时间
        DateTime timestamp = new DateTime();
        String timestamp1 = timestamp.toString();
        xiaoShouContactSupplyDTO.setTimestamp(timestamp1);

        String supplyUuid = xiaoShouContactAdditionalInfoService.saveOrUpdateZuPinXiaoShouContactSupply(xiaoShouContactSupplyDTO, xiaoShouContactUuid);

        return "redirect:xiaoshoucontactsupplycontentform.html?contactSupplyUuid=" + supplyUuid + "&xiaoShouContactUuid=" + xiaoShouContactUuid;

    }

    //添加补充协议
    @RequestMapping(value = "/contact/xiaoshoucontactsupplyform.html", method = RequestMethod.POST)
    public String saveZuPinContactSupplyForm(HttpServletRequest request, @ModelAttribute("xiaoShouContactSupplyDTO") ZuPinXiaoShouContactSupplyDTO zuPinContactSupplyDTO, BindingResult errors, ModelMap model) {
        String xiaoShouContactUuid = ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", "");

        //设置操作人为当前登录用户
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        zuPinContactSupplyDTO.setCaoZuoRen(loginInfo.getXingMing());
        zuPinContactSupplyDTO.setCaoZuoRenUuid(loginInfo.getUserUuid());
        //设置操作时间
        DateTime timestamp = new DateTime();
        String timestamp1 = timestamp.toString();
        zuPinContactSupplyDTO.setTimestamp(timestamp1);

        xiaoShouContactAdditionalInfoService.saveOrUpdateZuPinXiaoShouContactSupply(zuPinContactSupplyDTO, xiaoShouContactUuid);
        return "redirect:xiaoshoucontactform.html?xiaoShouContactUuid=" + xiaoShouContactUuid + "&infoSave=true";
    }

    //删除补充协议
    @RequestMapping(value = "/contact/xiaoshoucontactsupplydelete.html", method = RequestMethod.GET)
    public String setZuPinSupplyDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String xiaoShouContactUuid = ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", "");
        String contactSupplyUuid = ServletRequestUtils.getStringParameter(request, "contactSupplyUuid", "");
        xiaoShouContactAdditionalInfoService.deleteZuPinXiaoShouContactSupply(contactSupplyUuid);
        return "redirect:xiaoshoucontactform.html?xiaoShouContactUuid=" + xiaoShouContactUuid;
    }


    /********************************************合同补充协议内容***********************************************/
    //展示补充协议内容框
    @RequestMapping(value = "/contact/xiaoshoucontactsupplycontentform.html", method = RequestMethod.GET)
    public String setZuPinContactSupplyContentForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String xiaoShouContactUuid = ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", "");
        String contactSupplyUuid = ServletRequestUtils.getStringParameter(request, "contactSupplyUuid", "");

        //获取合同车辆信息
        ZuPinXiaoShouContactDTO xiaoShouContactDTO= xiaoShouContactService.obtainZuPinXiaoShouContactByUuid(xiaoShouContactUuid, false, false, false, true, true, true,false);

        model.put("saleManName", xiaoShouContactDTO.getSaleManName());
        model.put("contactNumber", xiaoShouContactDTO.getContactNumber());
        model.put("xiaoShouContactUuid", xiaoShouContactUuid);

        ZuPinContactSupplyContentDTO xiaoShouContactSupplyContentDTO = new ZuPinContactSupplyContentDTO();
        model.put("xiaoShouContactSupplyContentDTO", xiaoShouContactSupplyContentDTO);

        ZuPinXiaoShouContactSupplyDTO xiaoShouContactSupplyDTO = xiaoShouContactAdditionalInfoService.obtainZuPinXiaoShouContactSupplyByUuid(contactSupplyUuid);
        model.put("xiaoShouContactSupplyDTO", xiaoShouContactSupplyDTO);

        return "xiaoshou/xiaoshoucontactsupplycontentform";
    }

    //保存补充协议内容
    @RequestMapping(value = "/contact/xiaoshoucontactsupplycontentform.html", method = RequestMethod.POST)
    public String saveZuPinContactSupplyContentForm(HttpServletRequest request, @ModelAttribute("xiaoShouContactSupplyContentDTO") ZuPinXiaoShouContactSupplyContentDTO xiaoShouContactSupplyContentDTO, BindingResult errors, ZuPinXiaoShouContactSupplyDTO zuPinContactSupplyDTO, ModelMap model) {
        String xiaoShouContactUuid = ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", "");
        String contactSupplyUuid = ServletRequestUtils.getStringParameter(request, "contactSupplyUuid", "");
        String xiaoShouContactSupplyUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "xiaoShouContactSupplyUuid", ""));

        //获取合同车辆信息
        ZuPinXiaoShouContactDTO xiaoShouContactDTO= xiaoShouContactService.obtainZuPinXiaoShouContactByUuid(xiaoShouContactUuid, false, false, false, true, true, true,false);

        model.put("saleManName", xiaoShouContactDTO.getSaleManName());
        model.put("contactNumber", xiaoShouContactDTO.getContactNumber());
        model.put("xiaoShouContactUuid", xiaoShouContactUuid);

        //校验表单
        //生成补充协议内容 并设置id
        xiaoShouContactAdditionalInfoService.saveOrUpdateZuPinXiaoShouContactSupplyContent(xiaoShouContactSupplyContentDTO, xiaoShouContactSupplyUuid);
        ZuPinXiaoShouContactSupplyDTO xiaoShouContactSupplyDTO = xiaoShouContactAdditionalInfoService.obtainZuPinXiaoShouContactSupplyByUuid(contactSupplyUuid);
        model.put("xiaoShouContactSupplyDTO", xiaoShouContactSupplyDTO);
        xiaoShouContactSupplyContentDTO.setContent("");

        return "xiaoshou/xiaoshoucontactsupplycontentform";
    }

    //删除补充协议内容
    @RequestMapping(value = "/contact/xiaoshoucontactsupplycontentdelete.html", method = RequestMethod.GET)
    public String setZuPinSupplyContentDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String xiaoShouContactUuid = ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", "");
        String contactSupplyContentUuid = ServletRequestUtils.getStringParameter(request, "contactSupplyContentUuid", "");
        //获取当前的补充协议的id 在删除后跳转只改补充协议
        String contctSupplyId = ServletRequestUtils.getStringParameter(request, "contctSupplyId", "");

        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ApplicationLog.infoWithCurrentUser(ZuPinContactManagementController.class, loginInfo.getUserUuid(), "删除补充协议内容 补充协议id:" + contctSupplyId);
        xiaoShouContactAdditionalInfoService.deleteZuPinXiaoShouContactSupplyContent(contactSupplyContentUuid);
        return "redirect:xiaoshoucontactsupplycontentform.html?xiaoShouContactUuid=" + xiaoShouContactUuid + "&contactSupplyUuid=" + contctSupplyId;
    }

    /******************************************合同充电桩信息***************************************/
    @RequestMapping(value = "/contact/xiaoshoucontactchargingform.html", method = RequestMethod.GET)
    public String setZuPinContactChargingForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String xiaoShouContactUuid = ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", "");
        String chargingUuid = ServletRequestUtils.getStringParameter(request, "chargingUuid", "");

        ZuPinXiaoShouContactChargingDTO xiaoShouContactChargingDTO= null;
        if (StringUtils.hasText(chargingUuid)) {
            xiaoShouContactChargingDTO = xiaoShouContactAdditionalInfoService.obtainZuPinXiaoShouContactChargingByUuid(chargingUuid);
        } else {
            xiaoShouContactChargingDTO = new ZuPinXiaoShouContactChargingDTO();
        }
        model.put("charging", xiaoShouContactChargingDTO);
        model.put("xiaoShouContactUuid", xiaoShouContactUuid);

        //添加充电桩类型
        List<SelectView> zuPinContactChargingTypes = XiaoShouContactChargingType.getSelectViews();
        model.put("types", zuPinContactChargingTypes);

        return "xiaoshou/xiaoshoucontactchargingform";

    }

    //充电
    @RequestMapping(value = "/contact/xiaoshoucontactchargingform.html", method = RequestMethod.POST)
    public String saveZuPinContactChargingForm(HttpServletRequest request, @ModelAttribute("charging") ZuPinXiaoShouContactChargingDTO charging, HttpServletResponse response, ModelMap model) {
        String xiaoShouContactUuid = ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", "");

        //设置操作人为当前登录用户
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        charging.setActorManName(loginInfo.getXingMing());
        charging.setActorManUuid(loginInfo.getUserUuid());

        ZuPinXiaoShouContactDTO xiaoShouContactDTO = xiaoShouContactService.obtainZuPinXiaoShouContactByUuid(xiaoShouContactUuid, false, false, false, false, false, false,true);

        xiaoShouContactAdditionalInfoService.saveOrUpdateZuPinXiaoShouContactCharging(charging,xiaoShouContactDTO);
        return "redirect:xiaoshoucontactdetails.html?xiaoShouContactUuid=" + xiaoShouContactUuid;

    }

    //删除合同充电桩信息
    @RequestMapping(value = "/contact/xiaoshoucontactchargingdelete.html", method = RequestMethod.GET)
    public String setZuPinChargingDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String xiaoShouContactUuid = ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", "");
        String chargingUuid = ServletRequestUtils.getStringParameter(request, "chargingUuid", "");

        xiaoShouContactAdditionalInfoService.deleteZuPinXiaoShouContactCharging(chargingUuid);

        return "redirect:xiaoshoucontactdetails.html?xiaoShouContactUuid=" + xiaoShouContactUuid;
    }

   /* //处理充电桩
    @RequestMapping(value = "/contact/xiaoshoucontactchargingstatus.html", method = RequestMethod.GET)
    public String changeZuPinContactChargingStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String xiaoShouContactUuid = ServletRequestUtils.getStringParameter(request, "xiaoShouContactUuid", "");
        String chargingUuid = ServletRequestUtils.getStringParameter(request, "chargingUuid", "");

        ZuPinXiaoShouContactChargingDTO xiaoShouContactChargingDTO = xiaoShouContactAdditionalInfoService.obtainZuPinXiaoShouContactChargingByUuid(chargingUuid);

        xiaoShouContactChargingDTO.setZuPinContactChargingStatus("CHARGING_FINISH");
        xiaoShouContactChargingDTO.setFinishTime(JodaUtils.todMYHmString(new DateTime()));

        xiaoShouContactAdditionalInfoService.saveOrUpdateZuPinXiaoShouContactCharging(xiaoShouContactChargingDTO,);
        return "redirect:xiaoshoucontactdetails.html?xiaoShouContactUuid=" + xiaoShouContactUuid;
    }*/


}
