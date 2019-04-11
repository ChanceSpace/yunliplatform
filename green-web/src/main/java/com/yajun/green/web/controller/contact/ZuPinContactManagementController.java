package com.yajun.green.web.controller.contact;

import com.alibaba.fastjson.JSONObject;
import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.common.utils.PhoneNumUtils;
import com.yajun.green.common.web.view.SelectView;
import com.yajun.green.domain.contact.*;
import com.yajun.green.facade.dto.contact.ZuPinContactChargingDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleModuleDTO;
import com.yajun.green.manager.RedisCacheManager;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.ZuPinContactAdditionalInfoService;
import com.yajun.green.service.ZuPinContactService;
import com.yajun.green.service.ZuPinContactVehicleGetAndChangeService;
import com.yajun.green.web.pagging.ZuPinContactOverviewPaging;
import com.yajun.green.web.utils.ZuPinContactSearchObject;
import com.yajun.vms.facade.dto.VehicleModuleDTO;
import com.yajun.vms.service.VmsDubboService;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by LiuMengKe on 2017/8/9
 *      合同列表页面 表单 详情 提车
 */
@Controller
public class ZuPinContactManagementController {

    @Autowired
    private ZuPinContactService zuPinContactService;
    @Autowired
    private ZuPinContactVehicleGetAndChangeService zuPinContactVehicleGetAndChangeService;
    @Autowired
    private ZuPinContactAdditionalInfoService zuPinContactAdditionalInfoService;
    @Autowired
    private VmsDubboService vmsDubboService;

    /*********************************************订单查询部分*****************************************/

    @RequestMapping("/contact/zupincontactoverview.html")
    public String getAllZuPinContact(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        String pagKey = "PAGE_"+SecurityUtils.currentLoginUser().getUserUuid();
        RedisCacheManager redis = (RedisCacheManager) ApplicationEventPublisher.getBean("redisCacheManager");

        //接手参数
        boolean init = ServletRequestUtils.getBooleanParameter(request, "init", false);

        //使用序列化过的对象封装查询参数并保存在redis 中 维持一个小时
        ZuPinContactSearchObject searchObject = (ZuPinContactSearchObject)redis.fetch(pagKey);
        ZuPinContactOverviewPaging paging = null;

        if (init || searchObject == null) {
            int current = ServletRequestUtils.getIntParameter(request, "current", 1);
            String keyWords = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "keyWords", "")).toUpperCase();
            String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");
            String startTime = ServletRequestUtils.getStringParameter(request, "startTime", new LocalDate().minusYears(1).toString());
            String endTime = ServletRequestUtils.getStringParameter(request, "endTime", new LocalDate().plusMonths(1).toString());
            String sortBy = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "sortBy", "createTime"));
            String sortWay = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "sortWay", "desc"));
            String contactStatus = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "contactStatus", "all"));

            searchObject = new ZuPinContactSearchObject(current,keyWords,userUuid,startTime,endTime,sortBy,sortWay,contactStatus);

            paging = new ZuPinContactOverviewPaging(zuPinContactService);
            constructVehicleInfoPaging(paging,searchObject );
        } else {
            //重新设置总数，防止添加返回的操作数量不对
            paging = new ZuPinContactOverviewPaging(zuPinContactService);
            constructVehicleInfoPaging(paging,searchObject );
            paging.totalItemSize = -1;
        }

        redis.cache(pagKey,searchObject,1,TimeUnit.HOURS);

        //设置牌勋
        boolean addSort = ServletRequestUtils.getBooleanParameter(request, "addSort", false);
        if (addSort) {
            String sortBy = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "sortBy", "contactNumber"));
            String sortWay = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "sortWay", "desc"));
            paging.setSortBy(sortBy);
            paging.setSortWay(sortWay);
        }

        //查询数据部分
        List<ZuPinContactDTO> contacts = paging.getItems();
        model.put("contacts", contacts);
        model.put("paging", paging);

        return "contact/zupincontactoverview";
    }

    private void constructVehicleInfoPaging(ZuPinContactOverviewPaging paging,ZuPinContactSearchObject searchObject) {
        int current = searchObject.getCurrent();
        String keyWords = searchObject.getKeyWords();
        String startTime = searchObject.getStartTime();
        String endTime = searchObject.getEndTime();
        String sortBy = searchObject.getSortBy();
        String sortWay = searchObject.getSortWay();
        String contactStatus = searchObject.getContactStatus();

        paging.setCurrentPageNumber(current);
        paging.setKeyWords(keyWords);
        paging.setStartTime(startTime);
        paging.setEndTime(endTime);
        paging.setSortBy(sortBy);
        paging.setSortWay(sortWay);
        paging.setContactStatus(contactStatus);

        String carrierUuid = SecurityUtils.currentLoginUser().getCompanyUuid();
        if(SecurityUtils.hasAdminRole()) {
            carrierUuid = null;
        }
        paging.setUserUuid(carrierUuid);
    }

    /*********************************************表单部分*****************************************/

    @RequestMapping(value = "/contact/zupincontactform.html", method = RequestMethod.GET)
    public String setZuPinContactForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        boolean message = ServletRequestUtils.getBooleanParameter(request, "message", false);

        ZuPinContactDTO zuPinContactDTO = null;
        if (StringUtils.hasText(zuPinContactUuid)) {
            zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, false, false, false, false, true);
        } else {
            zuPinContactDTO = new ZuPinContactDTO();
        }

        List<SelectView> contactTypes = ZuPinContactType.getSelectViews();
        request.setAttribute("contactTypes", contactTypes);

        List<SelectView> baodianTypes = ZuPinContactBaoDianType.getSelectViews();
        request.setAttribute("baodianTypes", baodianTypes);

        List<SelectView> baoyueTypes = ZuPinContactBaoYueType.getSelectViews();
        model.put("zuPinBaoYueTypes",baoyueTypes);

        List<SelectView> userTypes = ZuPinContactUserType.getSelectViews();
        request.setAttribute("userTypes", userTypes);

        model.put("zuPinContact", zuPinContactDTO);
        request.setAttribute("message", message);

        return "contact/zupincontactform";
    }

    @RequestMapping(value="/contact/zupincontactform.html", method= RequestMethod.POST)
    public String saveCarInventoryForm(HttpServletRequest request, @ModelAttribute("zuPinContact") ZuPinContactDTO zuPinContact, BindingResult errors, ModelMap model) {
        validateZuPinContactForm(zuPinContact, errors);

        if (errors.hasErrors()) {
            List<SelectView> contactTypes = ZuPinContactType.getSelectViews();
            request.setAttribute("contactTypes", contactTypes);
            List<SelectView> baodianTypes = ZuPinContactBaoDianType.getSelectViews();
            request.setAttribute("baodianTypes", baodianTypes);
            request.setAttribute("message", false);
            List<SelectView> userTypes = ZuPinContactUserType.getSelectViews();
            request.setAttribute("userTypes", userTypes);
            model.putAll(errors.getModel());
            return "contact/zupincontactform";
        }

        zuPinContact.setZujinHasPay(new BigDecimal(0));
        zuPinContact.setYajinHasPay(new BigDecimal(0));
        if (zuPinContact.getOrigYaJinFee() == null) {
            zuPinContact.setOrigYaJinFee(new BigDecimal(0));
        }

        String zuPinContactUuid = zuPinContactService.saveOrUpdateZuPinContact(zuPinContact);
        return "redirect:zupincontactform.html?zuPinContactUuid=" + zuPinContactUuid + "&message=true";
    }

    private void validateZuPinContactForm(ZuPinContactDTO zuPinContact, BindingResult errors) {
        String saleManName = zuPinContact.getSaleManName();
        String jiaFangName = zuPinContact.getJiaFangName();
        String jiaFangFaRen = zuPinContact.getJiaFangFaRen();
        String jiaFangAddress = zuPinContact.getJiaFangAddress();
        String jiaFangPostCode = zuPinContact.getJiaFangPostCode();
        String yiFangUuid = zuPinContact.getYiFangUuid();
        String yiFangName = zuPinContact.getYiFangName();
        String yiFangFaRen = zuPinContact.getYiFangFaRen();
        String yiFangAddress = zuPinContact.getYiFangAddress();
        String yiFangPostCode = zuPinContact.getYiFangPostCode();
        String contactPerson = zuPinContact.getContactPerson();
        String contactPhone = zuPinContact.getContactPhone();
        BigDecimal origYaJinFee = zuPinContact.getOrigYaJinFee();

        if (!StringUtils.hasText(saleManName)) {
            errors.rejectValue("saleManName", "contact.sale.name.empty");
        }
        if (!StringUtils.hasText(jiaFangName)) {
            errors.rejectValue("jiaFangName", "contact.jiafang.name.empty");
        }
        if (!StringUtils.hasText(jiaFangFaRen)) {
            errors.rejectValue("jiaFangFaRen", "contact.jiafang.faren.empty");
        }
        if (!StringUtils.hasText(jiaFangAddress)) {
            errors.rejectValue("jiaFangAddress", "contact.jiafang.address.empty");
        }
        if (!StringUtils.hasText(jiaFangPostCode)) {
            errors.rejectValue("jiaFangPostCode", "contact.jiafang.postcode.empty");
        }

        if (!StringUtils.hasText(yiFangUuid)||!StringUtils.hasText(yiFangName)) {
            errors.rejectValue("yiFangName", "contact.yifang.name.empty");
        }

        if(ZuPinContactUserType.Y_COMPANY.equals(zuPinContact.getYiFangType())){
            if (!StringUtils.hasText(yiFangFaRen)) {
                errors.rejectValue("yiFangFaRen", "contact.yifang.faren.empty");
            }
            if (!StringUtils.hasText(yiFangAddress)) {
                errors.rejectValue("yiFangAddress", "contact.yifang.address.empty");
            }
            if (!StringUtils.hasText(yiFangPostCode)) {
                errors.rejectValue("yiFangPostCode", "contact.yifang.postcode.empty");
            }
        }

        if (!StringUtils.hasText(contactPerson)) {
            errors.rejectValue("contactPerson", "contact.yifang.person.empty");
        }
        if (!StringUtils.hasText(contactPhone)) {
            errors.rejectValue("contactPhone", "contact.yifang.phone.empty");
        } else {
            boolean phoneNumValidate = PhoneNumUtils.isPhoneNum(contactPhone);
            if (!phoneNumValidate) {
                errors.rejectValue("contactPhone", "contact.yifang.phone.notvalidate");
            }
        }

        if (origYaJinFee!=null) {
            try {
                Double.valueOf(origYaJinFee.toString());
            } catch (Exception e) {
                errors.rejectValue("origYaJinFee", "contact.orig.yajinfee.notvalidate");
            }
        }
    }

    /******************************************合同车辆定义***************************************/

    @RequestMapping(value = "/contact/zupincontactvehicleform.html", method = RequestMethod.GET)
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
        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, false, false, false, false, false);
        ZuPinVehicleModuleDTO moduleDTO = null;
        if (StringUtils.hasText(contactVehicleModuleUuid)) {
            moduleDTO = zuPinContactDTO.getContactModules().get(0);
        } else {
            moduleDTO = new ZuPinVehicleModuleDTO();
        }

        model.put("zuPinContactUuid", zuPinContactUuid);
        model.put("module", moduleDTO);
        return "contact/zupincontactvehicleform";
    }

    @RequestMapping(value="/contact/zupincontactvehicleform.html", method= RequestMethod.POST)
    public String saveZuPinContactVehicleForm(HttpServletRequest request, @ModelAttribute("module") ZuPinVehicleModuleDTO module, BindingResult errors, ModelMap model) throws Exception {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String token = SecurityUtils.userToken.get();

        //获取合同车辆信息
        validateZuPinContactVehicleForm(module, errors);

        ZuPinContactDTO zuPinContactDTO = null;
        if (StringUtils.hasText(zuPinContactUuid)) {
            zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, false, false, false, false, true);
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

            return "contact/zupincontactvehicleform";
        }

        //获取据图选择的车型数据
        VehicleModuleDTO selectModule = vmsDubboService.obtainTongBuVMSVehicle(module.getModuleUuid());
        module.setSelectVehicleModuleInfo(selectModule, false);

        //保存合同
        zuPinContactDTO.initOrigYaJinFee(module);
        zuPinContactService.saveOrUpdateZuPinContact(zuPinContactDTO);

        //保存选择的模型
        zuPinContactService.saveOrUpdateZuPinVehicleModule(module, zuPinContactUuid);
        return "redirect:zupincontactform.html?zuPinContactUuid=" + zuPinContactUuid + "&infoSave=true";
    }

    private void validateZuPinContactVehicleForm(ZuPinVehicleModuleDTO module, BindingResult errors) {
        String rentNumber = module.getRentNumber();
        String rentMonth = module.getRentMonth();
        String singleYaJin = module.getSingleYaJin();
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
            errors.rejectValue("actualRentFee","contact.module.actualRentFee.empty");
        }else {
            try {
                Double.valueOf(module.getActualRentFee());
            } catch (Exception e) {
                errors.rejectValue("singleRentFee", "contact.module.rentfee.notvalidate");
            }
        }
        //校验当租金缓付的时候 缓付周期不能大于租用周期 缓付
        if(ZuPinContactRepayType.C_AFTER.equals(module.getZuPinContactRepayType())){
            try {
                if (module.getDelayMonth() == null || module.getDelayMonth() < 1 || module.getDelayMonth() > Integer.valueOf(rentMonth)) {
                    errors.rejectValue("rentMonth", "contact.delayMonth.invalid");
                }
            }catch (Exception e) {
                    errors.rejectValue("rentMonth", "contact.delayMonth.invalid");
            }
        }

        /**********************************分期时的校验 1.首付2.租期3.单月押金4.合计**********************/
        //校验当分期付款时押金首付不能为空
        if (ZuPinYaJinType.Y_FENQI.equals(module.getZuPinYaJinType())&&module.getShouFu() == null) {
            errors.rejectValue("shouFu","contact.module.shouFu.empty");
        }

        if (ZuPinYaJinType.Y_FENQI.equals(module.getZuPinYaJinType())&&module.getYueGong() == null) {
                errors.rejectValue("yueGong","contact.module.yueGong.empty");
        }
        //校验分期数不能大于租用数
        if (ZuPinYaJinType.Y_FENQI.equals(module.getZuPinYaJinType())) {
            if (StringUtils.hasText(module.getFenQiShu()) && StringUtils.hasText(module.getRentMonth())){
                    int i = Integer.parseInt(module.getFenQiShu());
                    int j = Integer.parseInt(module.getRentMonth());
                    if (i+1>j) {
                        errors.rejectValue("fenQiShu","contact.module.fenQiShu.notvalidate");
                }
            }
        }


        //押金部分校验 为空 非数字
        if (!StringUtils.hasText(singleYaJin)) {
            errors.rejectValue("singleYaJin", "contact.module.yajin.empty");
        } else {
            try {
                Double.valueOf(singleYaJin);
            } catch (Exception e) {
                errors.rejectValue("singleYaJin", "contact.module.yajin.notvalidate");
            }
        }

        //押金分期付款校验：首付+分期数*月供 =押金总额= 押金单价*车辆数
        if (ZuPinYaJinType.Y_FENQI.equals(module.getZuPinYaJinType())) {
            if (StringUtils.hasText(module.getRentNumber()) && StringUtils.hasText(module.getSingleYaJin()) && module.getShouFu() !=null && module.getYueGong()!=null) {
                //押金单价*车辆数
                BigDecimal singleYaJin1  = new BigDecimal(singleYaJin);
                //首付+分期数*月供
                BigDecimal shouFu = module.getShouFu();
                BigDecimal fenQiShu = new BigDecimal(module.getFenQiShu());
                BigDecimal yueGong = module.getYueGong();
                BigDecimal yaJinZongShu = shouFu.add(fenQiShu.multiply(yueGong));
                if (singleYaJin1.compareTo(yaJinZongShu)!=0){
                    errors.rejectValue("yueGong","contact.module.yueGong.notvalidate");
                }
            }
        }
    }

    /*********************************************合同车型删除**********************************/

    @RequestMapping(value = "/contact/zupincontactvehicledelete.html", method = RequestMethod.GET)
    public String setZuPinVehicleModuleDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String contactVehicleUuid = ServletRequestUtils.getStringParameter(request, "contactVehicleUuid", "");
        zuPinContactService.deleteZuPinContactVehicleModule(contactVehicleUuid);
        return "redirect:zupincontactform.html?zuPinContactUuid=" + zuPinContactUuid;
    }

    /*********************************************合同审核和修改状态**********************************/

    @RequestMapping("/contact/zupincontactcheck.html")
    public String setZuPinContactCheck(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        //根据check来判断它从订单管理还是审核管理中来
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");

        String changeStatus = ServletRequestUtils.getStringParameter(request, "changeStatus");
        zuPinContactService.changeZuPinContactCheck(zuPinContactUuid, changeStatus);

        return "redirect:zupincontactoverview.html";
    }

    @RequestMapping(value = "/contact/zupincontactdelete.html", method = RequestMethod.GET)
    public String setZuPinContactDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));

        zuPinContactService.deleteZuPinContact(zuPinContactUuid);

        return "redirect:zupincontactoverview.html";
    }

    /*********************************************细节部分*****************************************/

    @RequestMapping(value = "/contact/zupincontactdetails.html", method = RequestMethod.GET)
    public String setZuPinContactDetails(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        boolean check = ServletRequestUtils.getBooleanParameter(request, "check", false);
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));

        ZuPinContactDTO zuPinContactDTO = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, true,true, true, true,true);

        model.put("zuPinContact", zuPinContactDTO);
        model.put("timeWeekAfter",new LocalDate().plusWeeks(1).toString());

        /*************根据乙方uuid获取其押金余额 并将余额总数保存起来 当前押金余额在zupinContactDTO中不再需要重新获取了***************/

        request.setAttribute("applicationHost", ApplicationEventPublisher.applicationFileRequestPath);
        request.setAttribute("check", check);

        //查询充电桩信息
        List<ZuPinContactChargingDTO> zuPinContactChargings  = zuPinContactAdditionalInfoService.obtainZuPinContactCharging(zuPinContactDTO.getYiFangUuid());
        model.put("zuPinContactChargings", zuPinContactChargings);
        return "contact/zupincontactdetails";
    }

    /*********************************************合同提车部分*****************************************/

    @RequestMapping(value = "/contact/zupincontactvehicleget.html", method = RequestMethod.GET)
    public String setZuPinContactVehicleGet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");

        ZuPinVehicleExecuteDTO executeDTO = new ZuPinVehicleExecuteDTO();

        model.put("execute", executeDTO);
        model.put("zuPinContactUuid", zuPinContactUuid);

        //添加远程车辆模型
        ZuPinContactDTO contact = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, true, true,false, false,true);
        model.put("contact", contact);
        //将提车批次传入
        List<ZuPinVehicleExecuteDTO> executeDTOList = contact.getExecuteModules();
        if(executeDTOList.size()==0){
            model.put("tiChePiCi",1);
        }else {
            Integer tichePici = 0;
            //取当前提车批次中最大的一个加一作为下一批次
            for (ZuPinVehicleExecuteDTO zuPinVehicleExecuteDTO : executeDTOList) {
                Integer pici = zuPinVehicleExecuteDTO.getTiChePiCi();
                tichePici = pici>tichePici?pici:tichePici;
            }
            model.put("tiChePiCi",tichePici+1);
        }

        String today = new LocalDate().toString();
        model.put("today", today);
        String fifteendays = new LocalDate().minusDays(ApplicationEventPublisher.TI_CHE_BEFORE_DAYS).toString();
        model.put("fifteendays", fifteendays);
        return "contact/zupincontactvehicleget";
    }

    @RequestMapping(value="/contact/zupincontactvehicleget.html", method = RequestMethod.POST)
    public String saveZuPinContactVehicleGet(HttpServletRequest request, @ModelAttribute("execute") ZuPinVehicleExecuteDTO execute, BindingResult errors, ModelMap model) throws Exception {
        int tiChePiCi = ServletRequestUtils.getIntParameter(request, "tiChePiCi", 1);
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String selectModuleBrand = ServletRequestUtils.getStringParameter(request, "selectModuleBrand", "");
        String[] vehicleNumbers = null;
        vehicleNumbers = ServletRequestUtils.getRequiredStringParameters(request, "vehicleNumber");
        String[] comment = ServletRequestUtils.getRequiredStringParameters(request, "comment");

        if(vehicleNumbers != null && vehicleNumbers.length > 0) {
            //提车操作 包含 租金押金的修改 和修改历史的记录
            zuPinContactVehicleGetAndChangeService.saveVehicleGet(zuPinContactUuid,String.valueOf(tiChePiCi),execute,selectModuleBrand,vehicleNumbers,comment);
        }

        return "redirect:zupincontactdetails.html?zuPinContactUuid=" + zuPinContactUuid;
    }

    //通过Ajax请求验证车牌号
    @RequestMapping("/contact/ajaxvalidatevehicleget.html")
    public void validateVehicleNumber(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String vehicleNumber = ServletRequestUtils.getStringParameter(request, "vehicleNumbers", "");
        String[] vehicleNumbers = vehicleNumber.split(",");

        String tiCheDate = ServletRequestUtils.getStringParameter(request, "tiCheDate", "");
        String zuPinContactUuid = ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", "");
        String token = SecurityUtils.userToken.get();
        JSONObject total = new JSONObject();
        /*************存储 车牌号所在选框的序号 和其所在合同************************/
        /*Map map = new HashMap<Integer,String>();*/
        StringBuilder errorMessageBuilder = new StringBuilder();
        /******有车牌号的时候循环车牌号 查询这个这牌这个提车时间 在所有合同中是否 有期限内相同牌号的******/
        if (vehicleNumbers.length > 0) {
            //先循环判断一遍看是否在本次所有填写的车牌号内有重复的情况
            Map<String, String> vehicleMpa = new HashMap<>();
            for (int i = 0; i < vehicleNumbers.length; i++) {
                if (StringUtils.hasText(vehicleNumbers[i])) {
                    if (StringUtils.hasText(vehicleMpa.get(vehicleNumbers[i]))) {
                        total.put("duplicateCar", "填写了相同的车牌号码 重复车牌为：" + vehicleNumbers[i]);
                    } else {
                        vehicleMpa.put(vehicleNumbers[i], vehicleNumbers[i]);
                    }
                }
            }
            for (int i = 0; i < vehicleNumbers.length; i++) {
                /******第一个车没有车牌号要有错误提示 车牌号为空******/
                if (i == 0 && !StringUtils.hasText(vehicleNumbers[i])) {
                    total.put("duplicateCar", "请填写车牌号");
                }
                //为防止 autocomplete结束以后用户再次编辑造成异常车牌号
                if (StringUtils.hasText(vehicleNumbers[i])) {
                    //1.平台上没有空闲的当前这个一级承运商的这个车
                    String licenseNumber1 = vmsDubboService.obtainTongBuLoginUserBeiYongVehicleByLicenseNumber(token,vehicleNumbers[i]);
                    //2.自己合同中有这个车 只是还没有同步到平台上
                    boolean duplicate = zuPinContactVehicleGetAndChangeService.obtainDuplicateNotFinishVehicleOfContact(vehicleNumbers[i],zuPinContactUuid);
                    if(duplicate){
                        errorMessageBuilder.append("车牌号" + vehicleNumbers[i] + "的车辆本合同在租 ，请等待同步后重新提取其他车辆;");
                    }
                    if (duplicate == false && !StringUtils.hasText(licenseNumber1)) {
                        errorMessageBuilder.append("车牌号" + vehicleNumbers[i] + "的车辆未找到，或不是空置状态;");
                    }

                }
            }
            if (StringUtils.hasText(errorMessageBuilder.toString())) {
                total.put("duplicateCar", errorMessageBuilder.toString());
            }
            if (total.size() == 0) {
                total.put("duplicateCar", "");
            }
        } else {
            /******没有提车 车牌号的 进行提示******/
            total.put("duplicateCar", "");
        }
        //返回结果
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(total.toJSONString());
        writer.flush();
        writer.close();
    }
}

