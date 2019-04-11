package com.yajun.green.web.controller.contact;

import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.domain.contact.ZuPinContactUserType;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.ZuPinContactService;
import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleModuleDTO;
import com.yajun.green.web.utils.*;
import com.yajun.user.facade.dto.CompanyDTO;
import com.yajun.user.service.UserDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/14.
 */
@Controller
public class ZuPinContactPDFController {

    @Autowired
    private ZuPinContactService zuPinContactService;
    @Autowired
    private UserDubboService userDubboService;

    @RequestMapping(value = "/contact/zupincontactpdfdownload.html", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response, String zuPinContactUuid) throws Exception {
        ZuPinContactDTO contact = zuPinContactService.obtainZuPinContactByUuid(zuPinContactUuid, true, false, false, false, false, true);
        List<ZuPinVehicleModuleDTO> moduleDTOList = contact.getContactModules();
        String modelUuid = null;
        if (moduleDTOList.size() > 0) {
            modelUuid = moduleDTOList.get(0).getUuid();
        }
        String targetPath = generatePDFPath(ApplicationEventPublisher.applicationFileUploadPath, contact.getContactNumber());
        ContactParagrah1Object firstParagraphparam = generateContactParagrah1Object(contact);
        ContactPdfParagrah2Object secondParagraphparam = generateContactPdfParagrah2Object(modelUuid, contact.getContactModules());

        /******字体库路径******/
        String fontPath = request.getSession().getServletContext().getRealPath("/ttf/simfang.ttf");

        String companyUuid = SecurityUtils.currentLoginUser().getCompanyUuid();
        CompanyDTO company = userDubboService.obtainTongBuCompanyInfoByUuid(companyUuid);
        ContactPdfGenerateUtil.generatePDF(targetPath, firstParagraphparam, secondParagraphparam, contact.getContactModules(), fontPath, contact.getContactNumber(), contact, company);

        //直接显示而不是打印
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + contact.getContactNumber() + ".pdf");
        FileCopyUtils.copy(new FileInputStream(targetPath + contact.getContactNumber() + "_watermark.pdf"), response.getOutputStream());

        return null;
    }

    /**
     * 生成Pdf 路径和文件名*
     */
    private String generatePDFPath(String targetBasePath, String contactNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append(targetBasePath).append(contactNumber + File.separator);
        return builder.toString();
    }

    /**
     * 生成合同第一段参数对象*
     */
    private ContactParagrah1Object generateContactParagrah1Object(ZuPinContactDTO dto) {
        //公司法人就是公司法人 个人法人写个人名称
        if (ZuPinContactUserType.Y_COMPANY.equals(dto.getYiFangType())) {
            return new ContactParagrah1Object(dto.getJiaFangName(), dto.getJiaFangFaRen(), dto.getJiaFangAddress(), dto.getJiaFangPostCode(), dto.getYiFangName(), dto.getYiFangFaRen(), dto.getYiFangAddress(), dto.getYiFangPostCode());
        } else {
            return new ContactParagrah1Object(dto.getJiaFangName(), dto.getJiaFangFaRen(), dto.getJiaFangAddress(), dto.getJiaFangPostCode(), dto.getYiFangName(), dto.getYiFangFaRen(), dto.getYiFangAddress(), dto.getYiFangPostCode());
        }
    }

    /**
     * 生成合同第二段参数对象*
     */
    private ContactPdfParagrah2Object generateContactPdfParagrah2Object(String modelUuid, List<ZuPinVehicleModuleDTO> moduleDTOList) {
        //标记是否找到对应车型
        boolean charge = false;
        ZuPinVehicleModuleDTO module = null;
        for (ZuPinVehicleModuleDTO zuPinVehicleModuleDTO : moduleDTOList) {
            if (zuPinVehicleModuleDTO.getUuid().equals(modelUuid)) {
                charge = true;
                module = zuPinVehicleModuleDTO;
            }
        }
        if (!charge) {
            return null;
        }
        /******一台车押金******/
        String yajinOne = module.getSingleYaJin();
        /*******一台车租金 每月*****/
        String zujinOne = module.getActualRentFee();
        /******租期多少个月******/
        String qixian = module.getRentMonth();
        /*******数量*****/
        String taishu = module.getRentNumber();
        /*******总押金*****/
        BigDecimal yajinTotalNumber = BigDecimal.valueOf(Double.valueOf(yajinOne)).multiply(BigDecimal.valueOf(Double.valueOf(taishu)));
        String yajinTotal = yajinTotalNumber.toString();
        /******总押金 汉字******/
        String yajinTotalFanti = NumberToCN.number2CNMontrayUnit(yajinTotalNumber);
        /******首月租金合计******/
        BigDecimal shouyuezujinNumber = BigDecimal.valueOf(Double.valueOf(zujinOne)).multiply(BigDecimal.valueOf(Double.valueOf(taishu)));
        String shouyuezujinHeji = shouyuezujinNumber.toString();
        /******首月租金合计 汉字******/
        String shouyuezujinHejiFanti = NumberToCN.number2CNMontrayUnit(shouyuezujinNumber);
        return new ContactPdfParagrah2Object(yajinOne, zujinOne, qixian, taishu, yajinTotal, yajinTotalFanti, shouyuezujinHeji, shouyuezujinHejiFanti);
    }


}
