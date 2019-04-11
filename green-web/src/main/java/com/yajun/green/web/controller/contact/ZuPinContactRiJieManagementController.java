package com.yajun.green.web.controller.contact;

import com.yajun.green.common.web.view.SelectView;
import com.yajun.green.domain.contact.ZuPinContactBaoYueType;
import com.yajun.green.domain.contact.ZuPinContactRepayType;
import com.yajun.green.domain.contact.ZuPinYaJinType;
import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleModuleDTO;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.ZuPinContactService;
import com.yajun.vms.facade.dto.VehicleModuleDTO;
import com.yajun.vms.service.VmsDubboService;
import org.apache.catalina.security.SecurityUtil;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/12/4.
 *
 *  日结合同 定义 合同车辆 操作
 *
 */
@Controller
public class ZuPinContactRiJieManagementController {

    @Autowired
    private VmsDubboService vmsDubboService;
    @Autowired
    private ZuPinContactService zuPinContactService;

    /********************************日结合同车辆定义*******************************/

    @RequestMapping(value = "/contact/zupincontactrijievehicleform.html",method = RequestMethod.GET)
    public String setZuPinContactVehicleForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String contactVehicleModuleUuid = ServletRequestUtils.getStringParameter(request, "contactVehicleModuleUuid", "");
        String token = SecurityUtils.userToken.get();

        //车辆类型
        List<VehicleModuleDTO> modules = vmsDubboService.obtainTongBuVMSVehicleModels(token);
        model.put("modules", modules);

        //押金类型
        List<SelectView> zuPinYaJinTypes = ZuPinYaJinType.getSelectViews();
        model.put("zuPinYaJinTypes",zuPinYaJinTypes);
        //租金类型
        List<SelectView> zuPinContactRepayTypes = ZuPinContactRepayType.getSelectViews();
        model.put("zuPinContactRepayTypes",zuPinContactRepayTypes);

        //获取合同车型信息 一个合同只有一个车型
        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, false, false,false,false,false);
        ZuPinVehicleModuleDTO moduleDTO = null;
        if (StringUtils.hasText(contactVehicleModuleUuid)) {
            moduleDTO = zuPinContactDTO.getContactModules().get(0);
        } else {
            moduleDTO = new ZuPinVehicleModuleDTO();
        }

        model.put("zuPinContactUuid", zuPinContactUuid);
        model.put("module", moduleDTO);
        return "contact/zupincontactrijieform";
    }

    /********************************日结合同车辆定义*******************************/

    @RequestMapping(value = "/contact/zupincontactrijievehicleform.html",method = RequestMethod.POST)
    public String saveZuPinContactVehicleForm(HttpServletRequest request, @ModelAttribute("module") ZuPinVehicleModuleDTO module, BindingResult errors, ModelMap model) throws Exception {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String token = SecurityUtils.userToken.get();
        //获取合同车辆信息
        validateZuPinContactRiJieVehicleForm(module, errors);

        ZuPinContactDTO zuPinContactDTO = null;
        if (StringUtils.hasText(zuPinContactUuid)) {
            zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, false,false, false, false,true);
        } else {
            zuPinContactDTO = new ZuPinContactDTO();
        }

        if (errors.hasErrors()) {
            //添加远程车辆模型
            List<VehicleModuleDTO> modules = vmsDubboService.obtainTongBuVMSVehicleModels(token);
            model.put("modules", modules);
            model.put("saleManName",zuPinContactDTO.getSaleManName());
            model.put("contactNumber",zuPinContactDTO.getContactNumber());
            model.putAll(errors.getModel());
            //添加押金类型
            List<SelectView> zuPinYaJinTypes = ZuPinYaJinType.getSelectViews();
            model.put("zuPinYaJinTypes",zuPinYaJinTypes);
            //添加租金类型
            List<SelectView> zuPinContactRepayTypes =ZuPinContactRepayType.getSelectViews();
            model.put("zuPinContactRepayTypes",zuPinContactRepayTypes);
            model.put("zuPinContactUuid",zuPinContactUuid);
            return "contact/zupincontactrijieform";
        }

        //获取据图选择的车型数据
        VehicleModuleDTO selectModule = vmsDubboService.obtainTongBuVMSVehicle(module.getModuleUuid());
        module.setSelectVehicleModuleInfo(selectModule, true);

        //设置初始的合同押金总额
        if(selectModule!=null){
            zuPinContactDTO.initOrigYaJinFee(module);
            zuPinContactService.saveOrUpdateZuPinContact(zuPinContactDTO);
        }

        //保存选择的车型
        zuPinContactService.saveOrUpdateZuPinVehicleModule(module, zuPinContactUuid);
        return "redirect:zupincontactform.html?zuPinContactUuid=" + zuPinContactUuid + "&infoSave=true";
    }

    private void validateZuPinContactRiJieVehicleForm(ZuPinVehicleModuleDTO module, BindingResult errors) {
        String rentNumber = module.getRentNumber();
        String rentMonth = module.getRentMonth();

        if (!StringUtils.hasText(rentNumber)) {
            errors.rejectValue("rentNumber", "contact.module.rentnumber.empty");
        } else {
            try {
                Integer.valueOf(rentNumber);
            } catch (Exception e) {
                errors.rejectValue("rentNumber", "contact.module.rentnumber.notvalidate");
            }
        }

        if (!StringUtils.hasText(rentMonth)) {
            errors.rejectValue("rentMonth", "contact.module.rentmonth.empty");
        } else {
            try {
                Integer.valueOf(rentMonth);
            } catch (Exception e) {
                errors.rejectValue("rentMonth", "contact.module.rentmonth.notvalidate");
            }
        }
        //校验实际月租单价不能为空
        if (!StringUtils.hasText(module.getActualRentFee())) {
            errors.rejectValue("actualRentFee", "contact.module.actualRentFee.empty");
        } else {
            try {
                Double.valueOf(module.getActualRentFee());
            } catch (Exception e) {
                errors.rejectValue("singleRentFee", "contact.module.rentfee.notvalidate");
            }
        }
    }

}
