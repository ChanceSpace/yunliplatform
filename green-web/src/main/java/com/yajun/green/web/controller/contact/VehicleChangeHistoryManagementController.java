package com.yajun.green.web.controller.contact;

import com.alibaba.fastjson.JSONObject;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.domain.contact.ZuPinVehicleExecute;
import com.yajun.green.facade.dto.contact.VehicleChangeHistoryDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.ChengYunService;
import com.yajun.green.service.ZuPinContactService;
import com.yajun.green.service.ZuPinContactVehicleGetAndChangeService;
import com.yajun.green.service.ZuPinHistoryService;
import org.joda.time.DateTime;
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
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by chance on 2017/8/21.
 */
@Controller
public class VehicleChangeHistoryManagementController {

    /******************************************车辆变更相关*****************************************/
    @Autowired
    ZuPinContactVehicleGetAndChangeService zuPinContactVehicleGetAndChangeService;

    /******************************************获取车辆变更页面*****************************************/
    @RequestMapping(value = "/contact/vehiclechangehistoryform.html", method = RequestMethod.GET)
    public String obtainVehicleChangeHistory(HttpServletRequest request, HttpServletResponse response, String uuid, ModelMap model) {
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String vehicleNumberBefore = ServletRequestUtils.getStringParameter(request,"vehicleNumberBefore","");
        VehicleChangeHistoryDTO vehicleChangeHistoryDTO = new VehicleChangeHistoryDTO();

        //截取车牌号
        String vehicleNumberBefore1 = org.apache.commons.lang.StringUtils.substringBefore(vehicleNumberBefore, ",");
        vehicleChangeHistoryDTO.setVehicleNumberBefore(vehicleNumberBefore1);

        model.put("vehicleChangeHistoryDTO", vehicleChangeHistoryDTO);
        model.put("zuPinContactUuid", zuPinContactUuid);

        return "contact/vehiclechangehistoryform";
    }

    /******************************************车辆变更操作*****************************************/
    @RequestMapping(value = "/contact/vehiclechangehistoryform.html", method = RequestMethod.POST)
    public String saveVehicleChangeHistory(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("vehicleChangeHistoryDTO") VehicleChangeHistoryDTO vehicleChangeHistoryDTO, BindingResult errors, String uuid, ModelMap model)  throws Exception{
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));

        //截取车牌号
        String vehicleNumberBefore = org.apache.commons.lang.StringUtils.substringBefore(vehicleChangeHistoryDTO.getVehicleNumberBefore(), ",");
        String zuPinExecuteUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinExecuteUuid", ""));
        String comment = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "comment", ""));
        String vehicleNumberNow = ServletRequestUtils.getStringParameter(request, "vehicleNumberNow", "");
        //校验表单
        if (errors.hasErrors()) {
            model.put("zuPinContactUuid", zuPinContactUuid);
            model.put("vehicleNumberBefore", vehicleNumberBefore);
            model.put("zuPinExecuteUuid", zuPinExecuteUuid);
            model.putAll(errors.getModel());
            return "contact/vehiclechangehistoryform";
        }

        //设置提车uuid
        ZuPinVehicleExecute zuPinVehicleExecute = new ZuPinVehicleExecute();
        zuPinVehicleExecute.setUuid(zuPinExecuteUuid);
        vehicleChangeHistoryDTO.setZuPinVehicleExecute(zuPinVehicleExecute);
        //设置操作人为当前登录用户
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        vehicleChangeHistoryDTO.setCaoZuoRen(loginInfo.getXingMing());
        vehicleChangeHistoryDTO.setCaoZuoRenUuid(loginInfo.getUserUuid());
        //设置操作时间
        vehicleChangeHistoryDTO.setChangeDate(new DateTime().toString());
        vehicleChangeHistoryDTO.setVehicleNumberBefore(vehicleNumberBefore);
        zuPinContactVehicleGetAndChangeService.saveVehicleChange(zuPinContactUuid,vehicleChangeHistoryDTO,vehicleNumberBefore,comment);
        return "redirect:zupincontactdetails.html?zuPinContactUuid=" + zuPinContactUuid;
    }

    /******************************************事故表单校验*****************************************/

    //通过Ajax校验车牌号
    @RequestMapping("/contact/ajaxvalidatevehiclechange.html")
    public void validateVehicleChange(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        String vehicleNumberNow = ServletRequestUtils.getStringParameter(request, "vehicleNumberNow", "");
        JSONObject total = new JSONObject();
        //保存错误信息
        StringBuilder errorMessageBuilder = new StringBuilder();
        //校验车牌号是否在其他有效合同中 租出
        if (!StringUtils.hasText(vehicleNumberNow)) {
            total.put("duplicateCar","请填写车牌号");
        }else {
            String changeDate = new LocalDate().toString();
            ZuPinVehicleExecuteDTO zuPinVehicleExecuteDTO = zuPinContactVehicleGetAndChangeService.obtainDuplicateVehicleFromAllContact(vehicleNumberNow,changeDate);
            if (zuPinVehicleExecuteDTO!=null) {
                errorMessageBuilder.append("车牌号" + zuPinVehicleExecuteDTO.getVehicleNum() + "的车辆，在合同" + zuPinVehicleExecuteDTO.getContactNumber() + "中已经租出请重新填写提车信息    ;");
                total.put("duplicateCar", errorMessageBuilder.toString());
            } else {
                total.put("duplicateCar", "");
            }
        }
        //返回结果
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(total.toJSONString());
        writer.flush();
        writer.close();
    }

}
