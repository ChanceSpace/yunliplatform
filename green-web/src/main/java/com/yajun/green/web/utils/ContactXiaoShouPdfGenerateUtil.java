package com.yajun.green.web.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.utils.CHListUtils;
import com.yajun.green.domain.xiaoshou.XiaoShouContactBaoDianType;
import com.yajun.green.domain.xiaoshou.XiaoShouType;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactSupplyContentDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactSupplyDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouVehicleModuleDTO;
import com.yajun.green.web.utils.xiaoshou.ContactXiaoShouPdfTemplate;
import com.yajun.green.web.utils.xiaoshou.ContactXiaoShouXieYiTemplate;
import com.yajun.user.facade.dto.CompanyDTO;
import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDate;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/12/18.
 * 销售合同pdf
 */
public class ContactXiaoShouPdfGenerateUtil {

    public static  String fontPath;
    public static String watermarkpdfPath;

    public static String getFontPath() {
        return fontPath;
    }

    public static void setFontPath(String fontPath) {
        ContactXiaoShouPdfGenerateUtil.fontPath = fontPath;
    }


    /******传入pdf 全地址 如 results/hello_world.pdf 并生成******/
    public static void generatePDF(String targerPath, ContactParagrah1Object firstParagraphparam, List<ZuPinXiaoShouVehicleModuleDTO> moduleList, String fontPath, String contactNumber, ZuPinXiaoShouContactDTO dto, CompanyDTO company) {
        setFontPath(fontPath);
        exportXiaoShouPdfDocument(targerPath, firstParagraphparam, moduleList, fontPath, contactNumber, dto, company);
    }

    private static void exportXiaoShouPdfDocument(String targerPath, ContactParagrah1Object firstParagraphparam, List<ZuPinXiaoShouVehicleModuleDTO> moduleList, String fontPath, String contactNumber, ZuPinXiaoShouContactDTO dto, CompanyDTO company) {
        // 创建A4纸张大小的pdf
        Document document = new Document(PageSize.A4);
        try {
            File contactFile = new File(targerPath);
            if (!contactFile.exists()) {
                contactFile.mkdir();
            }
            StringBuilder pdfpath = new StringBuilder();
            pdfpath.append(targerPath + contactNumber + ".pdf");
            //使用PDFWriter进行写文件操作
            watermarkpdfPath = pdfpath.toString();
            PdfWriter.getInstance(document, new FileOutputStream(
                    pdfpath.toString()));

            BaseFont bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);

            //在document open 之前 给document设置页码

            //为了匹配各公司缩写 保持合同头部格式
            StringBuilder headerBlank = new StringBuilder();
            for (int i = (61 - 5 - contactNumber.length()); i > 0; i--) {
                headerBlank.append(" ");
            }
            String headerTitle = "合同日期:" + new LocalDate().toString() + headerBlank.toString() + "合同编号:" + contactNumber;
            HeaderFooter headerFooter = new HeaderFooter(new Phrase(headerTitle, fontChinese), false);
            headerFooter.setBorder(Rectangle.BOTTOM);
            document.setHeader(headerFooter);

            //在document open 之前 给document设置页码
            HeaderFooter footer = new HeaderFooter(new Phrase("第", fontChinese), new Phrase("页", fontChinese));
            footer.setBorder(Rectangle.NO_BORDER);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.setFooter(footer);



            document.open();
            /******产生合同标题******/
            generateTitle(document, fontChinese, dto.getJiaFangName() + ContactXiaoShouPdfTemplate.CONTACTTITLE);
            /******产生开头甲方乙方******/
            generateBegin(document, fontChinese, ContactXiaoShouPdfTemplate.JIAYISHANGFANG, firstParagraphparam);
            addBodyBody(document, ContactXiaoShouPdfTemplate.YINYAN);
            addBodyTitle(document, ContactXiaoShouPdfTemplate.RULE_1);
            /******产生租赁车辆表******/
            generateXiaoShouTable(document, fontChinese, moduleList);
            addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_1);

            /******条款2******/
            addBodyTitle(document, ContactXiaoShouPdfTemplate.RULE_2);
            addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_2);

            /******条款3******/
            addBodyTitle(document, ContactXiaoShouPdfTemplate.RULE_3);
            if (isQuanKuan(dto)) {
                generateQuanKuanBody3(document, ContactXiaoShouPdfTemplate.BODY_3, moduleList.get(0), company);
            } else {
                generateAnJieBody3(document, ContactXiaoShouPdfTemplate.BODY_3_ANJIE, moduleList.get(0), company);
            }

            /******条款4******/
            addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_4);
            if (isQuanKuan(dto)) {
                generateQuanKuanBody4(document, ContactXiaoShouPdfTemplate.BODY_4, dto);
            } else {
                generateQuanKuanBody4(document, ContactXiaoShouPdfTemplate.BODY_4_ANJIE, dto);
            }

            /******条款5******/
            addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_5);
            if (isQuanKuan(dto)) {
                addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_5);
            } else {
                addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_5_ANJIE);
            }

            /******条款6******/
            addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_6);

            /******条款7******/
            addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_7);

            /******条款8******/
            addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_8);
            addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_8);
            /******条款9******/
            addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_9);
            addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_9);
            /******条款10******/

            addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_10);
            if (isQuanKuan(dto)) {
                addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_10);
            } else {
                addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_10_ANJIE);
            }

            /*************从第11条开始完全不同**********/
            if (isQuanKuan(dto)) {
                addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_11);
                addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_11);
                addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_12);
                addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_12);
                addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_13);
                addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_13);
                addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_14);
            } else {
                addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_11_ANJIE);
                addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_11_ANJIE);

                addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_12_ANJIE);
                addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_12_ANJIE);

                addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_13_ANJIE);
                addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_13_ANJIE);

                addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_14_ANJIE);
                addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_14_ANJIE);
                addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_15_ANJIE);

                addBodyTitle(document, ContactXiaoShouPdfTemplate.ROLE_16_ANJIE);
                addBodyBody(document, ContactXiaoShouPdfTemplate.BODY_16_ANJIE);
            }

            /******甲乙方签字确认******/
            generateEndTable(document, company);

            //是否有补充协议 每个补充协议新起一页
            if (CHListUtils.hasElement(dto.getZuPinContactSupplies())) {
                List<ZuPinXiaoShouContactSupplyDTO> xiaoShouContactSupplyDTOList = dto.getZuPinContactSupplies();
                for (int k = 0; k < xiaoShouContactSupplyDTOList.size(); k++) {
                    ZuPinXiaoShouContactSupplyDTO xiaoShouContactSupplyDTO = xiaoShouContactSupplyDTOList.get(k);

                    document.setHeader(null);
                    document.newPage();
                    generateTitle(document, fontChinese, dto.getJiaFangName()+ContactXiaoShouXieYiTemplate.XIEYITITLE);
                    generateBegin(document, fontChinese, ContactXiaoShouXieYiTemplate.JIAYISHANGFANG, firstParagraphparam);
                    generateXieYiBegin(document, ContactXiaoShouXieYiTemplate.XIEYIHEADER1 + dto.getJiaFangName() + ContactXiaoShouXieYiTemplate.XIEYIHEADER2, contactNumber, fontChinese);
                    List<ZuPinXiaoShouContactSupplyContentDTO> xiaoShouContactSupplyContentDTOS = xiaoShouContactSupplyDTO.getZuPinXiaoShouContactSupplyContentDTOList();

                    StringBuilder builder = new StringBuilder();
                    int j = 0;
                    for (int i = 0; i < xiaoShouContactSupplyContentDTOS.size(); i++) {
                        if (i == 0) {
                            builder.append("   第" + NumberToCN.getGeneralCHN(i + 1) + "条 " + xiaoShouContactSupplyContentDTOS.get(i).getContent() + "\n");
                        } else {
                            builder.append("    第" + NumberToCN.getGeneralCHN(i + 1) + "条 " + xiaoShouContactSupplyContentDTOS.get(i).getContent() + "\n");
                        }
                        j = i + 1;
                    }

                    builder.append("    第"+NumberToCN.getGeneralCHN(j+1)+"条 "+ContactXiaoShouXieYiTemplate.BUCHONGXIEYI2_1+dto.getJiaFangName()+ContactXiaoShouXieYiTemplate.BUCHONGXIEYI2_2                                    +dto.getJiaFangName()+ContactXiaoShouXieYiTemplate.BUCHONGXIEYI2_3+ "\n");
                    builder.append("    第"+NumberToCN.getGeneralCHN(j+2)+"条 "+ContactXiaoShouXieYiTemplate.BUCHONGXIEYI3 + "\n");
                    addBodyBody(document, builder.toString());

                    document.newPage();
                    generateXieYiEndTable(document, fontChinese, company);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //todo  生成水印未成功 暂时不处理
        /*try {
            String imagePath = fontPath.replaceAll("simfang.ttf", "");
            Image image = Image.getInstance(IOUtils.toByteArray(new FileInputStream(imagePath + "watermark.jpg")));
            image.setAbsolutePosition(-100,-100);
            Watermark watermark = new Watermark(image,-100,-100);
            document.add(watermark);
        }catch (Exception e){

        }*/

        document.close();

        /******生成带水印PDF******/

        try {
            imageWatermark(watermarkpdfPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    static void generateXiaoShouTable(Document document, Font font, java.util.List<ZuPinXiaoShouVehicleModuleDTO> moduleList) throws Exception {
        //创建一个6列的表
        Table table = new Table(8);
        table.setWidth(100);
        table.setCellspacing(4f);
        //设置列之间的相对宽度
        int[] width = {15, 15, 25, 10, 10, 10, 10, 20};//设置每列宽度比例
        table.setWidths(width);
        //生成表头
        generateXiaoShouTableHeader(table, resetFontSize(12), document);
        generateXiaoShouTableBody(table, resetFontSize(12), document, moduleList);
        table.setAlignment(Element.ALIGN_CENTER);
    }

    static void generateXiaoShouTableHeader(Table datatable, Font font, Document document) throws Exception {
        // 添加表头元素
        Paragraph a1 = new Paragraph("产品名称", font);
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell = new Cell(a1);
        cell.setUseAscender(true);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        datatable.addCell(cell);
        datatable.addCell(getParagraphInCenter(new Paragraph("品牌", font)));
        datatable.addCell(getParagraphInCenter(new Paragraph("产品型号", font)));
        datatable.addCell(getParagraphInCenter(new Paragraph("颜色", font)));
        datatable.addCell(getParagraphInCenter(new Paragraph("数量（台）", font)));
        datatable.addCell(getParagraphInCenter(new Paragraph("单价（元）", font)));
        datatable.addCell(getParagraphInCenter(new Paragraph("总金额（元）", font)));
        datatable.addCell(getParagraphInVertical(new Paragraph("备注", font)));

    }

    static void generateXiaoShouTableBody(Table datatable, Font font, Document document, java.util.List<ZuPinXiaoShouVehicleModuleDTO> params) throws Exception {
        // 添加表头元素
        for (ZuPinXiaoShouVehicleModuleDTO param : params) {
            datatable.addCell(getParagraphInCenter(new Paragraph(param.getModuleName(), font)));
            datatable.addCell(getParagraphInCenter(new Paragraph(param.getModuleType(), font)));
            datatable.addCell(getParagraphInCenter(new Paragraph(param.getModuleBrand(), font)));
            datatable.addCell(getParagraphInCenter(new Paragraph("白色", font)));
            datatable.addCell(getParagraphInCenter(new Paragraph(String.valueOf(param.getSaleNumber()), font)));
            datatable.addCell(getParagraphInCenter(new Paragraph(String.valueOf(param.getSalePrice()), font)));
            datatable.addCell(getParagraphInCenter(new Paragraph(String.valueOf(param.getSalePrice().multiply(new BigDecimal(param.getSaleNumber()))), font)));
            datatable.addCell(getParagraphInVertical(new Paragraph("销售单价为扣除中央和成都地方补贴后的价格", font)));
        }
        document.add(datatable);
    }


    //判断是否是全款
    private static boolean isQuanKuan(ZuPinXiaoShouContactDTO dto) {
        if (dto.getXiaoShouType().equals(XiaoShouType.XS_QK)) {
            return true;
        } else {
            return false;
        }
    }

    //全款条款三
    static void generateQuanKuanBody3(Document document, String body, ZuPinXiaoShouVehicleModuleDTO moduleDTO, CompanyDTO company) throws Exception {
        String salenumber = String.valueOf(moduleDTO.getSaleNumber());
        String dingjin = moduleDTO.getDingJin().multiply(new BigDecimal(moduleDTO.getSaleNumber())).toString();
        String dingjinrmb = NumberToCN.number2CNMontrayUnit(moduleDTO.getDingJin().multiply(new BigDecimal(moduleDTO.getSaleNumber())));
        String totalprice = moduleDTO.getSalePrice().subtract(moduleDTO.getDingJin()).multiply(new BigDecimal(moduleDTO.getSaleNumber())).toString();
        String totalpricermb = NumberToCN.number2CNMontrayUnit(moduleDTO.getSalePrice().subtract(moduleDTO.getDingJin()).multiply(new BigDecimal(moduleDTO.getSaleNumber())));
        String result = body.replaceAll("salenumber", "!")
                .replaceAll("dingjin", "!")
                .replaceAll("djrmb", "!")
                .replaceAll("totalprice", "!")
                .replaceAll("totalrmb", "!");
        String[] params = {salenumber, dingjin, dingjinrmb, totalprice, totalpricermb};
        String[] second = result.split("!");
        ApplicationLog.info(ContactXiaoShouPdfGenerateUtil.class, "" + second.length);
        ApplicationLog.info(ContactXiaoShouPdfGenerateUtil.class, "" + params.length);
        /******拼接 参数******/
        if (second.length >= 1) {
            for (int i = 0; i < second.length; i++) {
                document.add(new Chunk(second[i], resetFontSize(12, Font.NORMAL)));
                if (i != second.length - 1) {
                    Chunk beforeunderline = new Chunk("  ", resetFontSize(ContactPdfTemplate.BODYSIZE, Font.NORMAL));
                    Chunk underline = new Chunk(params[i], resetFontSize(ContactPdfTemplate.BODYSIZE, Font.NORMAL));
                    Chunk afterunderline = new Chunk("  ", resetFontSize(ContactPdfTemplate.BODYSIZE, Font.NORMAL));
                    beforeunderline.setUnderline(0.1f, -2f);
                    underline.setUnderline(0.1f, -2f);
                    afterunderline.setUnderline(0.1f, -2f);
                    document.add(beforeunderline);
                    document.add(underline);
                    document.add(afterunderline);
                }
            }
        }
        addyhdetail(document, ContactXiaoShouPdfTemplate.BODY3_YH, company);
    }

    //按揭条款三
    static void generateAnJieBody3(Document document, String body, ZuPinXiaoShouVehicleModuleDTO moduleDTO, CompanyDTO company) throws Exception {

        String salenumber = String.valueOf(moduleDTO.getSaleNumber());
        String dingjin = moduleDTO.getDingJin().multiply(new BigDecimal(moduleDTO.getSaleNumber())).toString();
        String dingjinrmb = NumberToCN.number2CNMontrayUnit(moduleDTO.getDingJin().multiply(new BigDecimal(moduleDTO.getSaleNumber())));

        String shoufu = moduleDTO.getShouFu().multiply(new BigDecimal(moduleDTO.getSaleNumber())).toString();
        String sfrmb = NumberToCN.number2CNMontrayUnit(moduleDTO.getShouFu().multiply(new BigDecimal(moduleDTO.getSaleNumber())));

        String weikuan = moduleDTO.getWeiKuan().multiply(new BigDecimal(moduleDTO.getSaleNumber())).toString();
        String wkrmb = NumberToCN.number2CNMontrayUnit(moduleDTO.getWeiKuan().multiply(new BigDecimal(moduleDTO.getSaleNumber())));

        String daikuanqixian = String.valueOf(moduleDTO.getMaxAnJieYear());

        String result = body.replaceAll("salenumber", "!")
                .replaceAll("dingjin", "!")
                .replaceAll("djrmb", "!")
                .replaceAll("shoufu", "!")
                .replaceAll("sfrmb", "!")
                .replaceAll("weikuan", "!")
                .replaceAll("wkrmb", "!")
                .replaceAll("daikuanqixian", "!");
        String[] params = {salenumber, dingjin, dingjinrmb, shoufu, sfrmb, weikuan, wkrmb, daikuanqixian};
        String[] second = result.split("!");
        ApplicationLog.info(ContactXiaoShouPdfGenerateUtil.class, "" + second.length);
        ApplicationLog.info(ContactXiaoShouPdfGenerateUtil.class, "" + params.length);
        /******拼接 参数******/
        if (second.length >= 1) {
            for (int i = 0; i < second.length; i++) {
                document.add(new Chunk(second[i], resetFontSize(12, Font.NORMAL)));
                if (i != second.length - 1) {
                    Chunk beforeunderline = new Chunk("  ", resetFontSize(ContactPdfTemplate.BODYSIZE, Font.NORMAL));
                    Chunk underline = new Chunk(params[i], resetFontSize(ContactPdfTemplate.BODYSIZE, Font.NORMAL));
                    Chunk afterunderline = new Chunk("  ", resetFontSize(ContactPdfTemplate.BODYSIZE, Font.NORMAL));
                    beforeunderline.setUnderline(0.1f, -2f);
                    underline.setUnderline(0.1f, -2f);
                    afterunderline.setUnderline(0.1f, -2f);
                    document.add(beforeunderline);
                    document.add(underline);
                    document.add(afterunderline);
                }
            }
        }
        addyhdetail(document, ContactXiaoShouPdfTemplate.BODY3_YH, company);
    }


    //条款3 银行信息添加
    private static void addyhdetail(Document document, String body3Yh, CompanyDTO company) throws Exception {
        String[] bodys = body3Yh.split("!");
        String[] params = {company.getName(), company.getBankName(), company.getBankNum()};
        for (int i = 0; i < bodys.length; i++) {
            document.add(new Chunk(bodys[i], resetFontSize(12, Font.NORMAL)));
            if (i <= params.length - 1) {
                document.add(new Chunk(params[i], resetFontSize(12, Font.BOLD)));
            }
        }
    }

    //全款条款4
    static void generateQuanKuanBody4(Document document, String body, ZuPinXiaoShouContactDTO contactDTO) throws Exception {
        Integer jl_number = contactDTO.getJiaoliuChargeNumber();
        Integer zl_number = contactDTO.getZhiliuChargeNumber();

        String result = body.replaceAll("zlnumber", "!")
                .replaceAll("jlnumber", "!")
                .replaceAll("typenumber", "!");
        Integer type_number = 2;

        if (XiaoShouContactBaoDianType.C_BAODIAN.equals(contactDTO.getBaoDianType())) {
            type_number = 1;
        }
        String[] params = {String.valueOf(jl_number), String.valueOf(zl_number), String.valueOf(type_number)};
        String[] second = result.split("!");
        ApplicationLog.info(ContactXiaoShouPdfGenerateUtil.class, "" + second.length);
        ApplicationLog.info(ContactXiaoShouPdfGenerateUtil.class, "" + params.length);
        /******拼接 参数******/
        if (second.length >= 1) {
            for (int i = 0; i < second.length; i++) {
                document.add(new Chunk(second[i], resetFontSize(12, Font.NORMAL)));
                if (i != second.length - 1) {
                    Chunk beforeunderline = new Chunk("  ", resetFontSize(ContactPdfTemplate.BODYSIZE, Font.NORMAL));
                    Chunk underline = new Chunk(params[i], resetFontSize(ContactPdfTemplate.BODYSIZE, Font.NORMAL));
                    Chunk afterunderline = new Chunk("  ", resetFontSize(ContactPdfTemplate.BODYSIZE, Font.NORMAL));
                    beforeunderline.setUnderline(0.1f, -2f);
                    underline.setUnderline(0.1f, -2f);
                    afterunderline.setUnderline(0.1f, -2f);
                    document.add(beforeunderline);
                    document.add(underline);
                    document.add(afterunderline);
                }
            }
        }
    }

    /*********************************工具方法*******************************/

    /******使用指定大小 指定样式的 仿宋字体******/
    public static Font resetFontSize(int newSize, int fontType) throws Exception {
        BaseFont bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontChinese = new Font(bfChinese, newSize, fontType);
        return fontChinese;
    }

    public static void generateTitle(Document document, Font font, String title) throws Exception {
        Paragraph headerParagrah;
        headerParagrah = new Paragraph(title, resetFontSize(ContactPdfTemplate.BIAOTISIZE, Font.BOLD));
        headerParagrah.setAlignment(Element.ALIGN_CENTER);
        document.add(headerParagrah);
    }

    /******使用指定大小的仿宋字体******/
    public static Font resetFontSize(int newSize) throws Exception {
        BaseFont bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontChinese = new Font(bfChinese, newSize, Font.NORMAL);
        return fontChinese;
    }

    static void generateBegin(Document document, Font font, String body, ContactParagrah1Object firstParagraphparam) throws Exception {

        String jiaFangCompanyName = firstParagraphparam.getJiaFangcompanyName();
        String jiaFangFaren = firstParagraphparam.getJiaFangFaRen();
        String jiaFangAddress = firstParagraphparam.getJiaFangAddress();
        String jiaFangPostcode = firstParagraphparam.getJiaFangPostCode();

        String companyName = firstParagraphparam.getCompanyName();
        String faren = firstParagraphparam.getFaren();
        String address = firstParagraphparam.getAddress();
        String postcode = firstParagraphparam.getPostcode();

        String result = body.replaceAll("jiafangcompanyname", "!")
                .replaceAll("jiafangfaren", "!")
                .replaceAll("jiafangaddress", "!")
                .replaceAll("jiafangpostcode", "!")
                .replaceAll("companyname", "!").replaceAll("faren", "!")
                .replaceAll("address", "!")
                .replaceAll("postcode", "!");
        String[] params = {jiaFangCompanyName,jiaFangFaren,jiaFangAddress,jiaFangPostcode,companyName, faren, address, postcode};
        String[] second = result.split("!");
        /******拼接 参数******/
        for (int i = 0; i < second.length; i++) {
            document.add(new Chunk(second[i], resetFontSize(14, Font.BOLD)));
            if (i <= 7) {
                Chunk underline = new Chunk(params[i], resetFontSize(14, Font.BOLD));
                document.add(underline);
            }

        }

    }

    //添加合同条款标题 如 第二条 租金、租赁押金和租期 固定字体 大小以及 字体样式
    protected static void addBodyTitle(Document document, String title) throws Exception {
        Paragraph paragraphnew = new Paragraph(title, resetFontSize(ContactPdfTemplate.RULESIZE, Font.BOLD));
        paragraphnew.setLeading(22f);
        document.add(paragraphnew);
    }

    protected static void addBodyBody(Document document, String title) throws Exception {
        Paragraph paragraphnew = new Paragraph(title, resetFontSize(ContactPdfTemplate.BODYSIZE, Font.NORMAL));
        paragraphnew.setLeading(22f);
        document.add(paragraphnew);
    }


    /******传入段落返回 水平居中垂直居中的 Cell******/
    protected static Cell getParagraphInTop(Paragraph paragraph) throws Exception {
        Cell cell = new Cell(paragraph);
        cell.setUseAscender(true);
        cell.setUseBorderPadding(true);
        cell.setHorizontalAlignment(Cell.LEFT);
        cell.setVerticalAlignment(Cell.TOP);
        return cell;
    }

    /******只要垂直居中******/
    protected static Cell getParagraphInVertical(Paragraph paragraph) throws Exception {
        Cell cell = new Cell(paragraph);
        cell.setUseAscender(true);
        cell.setBorderWidth(1);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        return cell;
    }

    /******传入段落返回 水平居中垂直居中的 Cell******/
    protected static Cell getParagraphInCenter(Paragraph paragraph) throws Exception {

        Cell cell = new Cell(paragraph);
        cell.setUseAscender(true);
        cell.setUseBorderPadding(true);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        return cell;
    }

    static void generateXieYiEndTable(Document document, Font font, CompanyDTO companyDTO) throws Exception {
        //创建一个6列的表
        Table table = new Table(2);
        table.setWidth(100);
        table.setCellspacing(4f);
        table.setDefaultCellBorder(PdfPCell.NO_BORDER);
        table.setBorderColor(Color.white);
        //设置列之间的相对宽度
        int[] width = {50,50};//设置每列宽度比例
        table.setWidths(width);

        Paragraph a1 = new Paragraph("甲方（盖章）：", resetFontSize(13, Font.BOLD));
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell = new Cell(a1);
        cell.setUseAscender(true);
        cell.setHorizontalAlignment(Cell.LEFT);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        table.addCell(cell);
        table.addCell(getParagraphInTop(new Paragraph("乙方（盖章）：",  resetFontSize(13, Font.BOLD))));

        generateEndTableBody(table, resetFontSize(13, Font.BOLD), document, companyDTO);
        table.setAlignment(Element.ALIGN_CENTER);
    }

    static void generateEndTable(Document document, CompanyDTO company) throws Exception {
        //创建一个6列的表
        Table table = new Table(2);
        table.setWidth(100);
        table.setCellspacing(4f);
        table.setDefaultCellBorder(PdfPCell.NO_BORDER);
        table.setBorderColor(Color.white);

        //设置列之间的相对宽度
        int[] width = {50,50};//设置每列宽度比例
        table.setWidths(width);
        generateEndTableBody(table, resetFontSize(14, Font.BOLD), document,company);
        table.setAlignment(Element.ALIGN_CENTER);
    }

    static void generateEndTableBody(Table datatable, Font font, Document document,CompanyDTO companyDTO) throws Exception {
        Paragraph a1 = new Paragraph("甲方："+companyDTO.getName(), font);
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell = new Cell(a1);
        cell.setUseAscender(true);
        cell.setHorizontalAlignment(Cell.LEFT);
        cell.setVerticalAlignment(Cell.TOP);
        datatable.addCell(cell);
        datatable.addCell(getParagraphInTop(new Paragraph("乙方：", font)));

        Paragraph a2 = new Paragraph("签约代表：", font);
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell2 = new Cell(a2);
        cell2.setUseAscender(true);
        cell2.setHorizontalAlignment(Cell.LEFT);
        cell2.setVerticalAlignment(Cell.TOP);
        datatable.addCell(cell2);
        datatable.addCell(getParagraphInTop(new Paragraph("签约代表：", font)));


        Paragraph a3 = new Paragraph("委托代理人：", font);
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell3 = new Cell(a3);
        cell3.setUseAscender(true);
        cell3.setHorizontalAlignment(Cell.LEFT);
        cell3.setVerticalAlignment(Cell.TOP);
        datatable.addCell(cell3);
        datatable.addCell(getParagraphInTop(new Paragraph("委托代理人：", font)));

        Paragraph a4 = new Paragraph("开户银行：" + companyDTO.getBankName(), font);
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell4 = new Cell(a4);
        cell4.setUseAscender(true);
        cell4.setHorizontalAlignment(Cell.LEFT);
        cell4.setVerticalAlignment(Cell.TOP);
        datatable.addCell(cell4);
        datatable.addCell(getParagraphInTop(new Paragraph("开户银行：", font)));


        Paragraph a5 = new Paragraph("银行帐号："+companyDTO.getBankNum(), font);
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell5 = new Cell(a5);
        cell5.setUseAscender(true);
        cell5.setHorizontalAlignment(Cell.LEFT);
        cell5.setVerticalAlignment(Cell.TOP);
        datatable.addCell(cell5);
        datatable.addCell(getParagraphInTop(new Paragraph("银行帐号：", font)));

        Paragraph a6 = new Paragraph("签订时间：     年    月     日", font);
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell6 = new Cell(a6);
        cell6.setUseAscender(true);
        cell6.setHorizontalAlignment(Cell.LEFT);
        cell6.setVerticalAlignment(Cell.TOP);
        datatable.addCell(cell6);
        datatable.addCell(getParagraphInTop(new Paragraph("签订时间：     年    月     日", font)));

        document.add(datatable);
    }

    static void generateXieYiBegin(Document document, String body, String contactNumber, Font font) throws Exception {
        String result = body
                .replaceAll("contactNumber", "!");
        String[] params = {contactNumber};
        String[] second = result.split("!");
        /******拼接 参数******/
        for (int i = 0; i < second.length; i++) {
            document.add(new Chunk(second[i], resetFontSize(ContactPdfTemplate.RULESIZE, Font.BOLD)));
            if (i <= 0) {
                Chunk underline = new Chunk(params[i], resetFontSize(ContactPdfTemplate.RULESIZE, Font.BOLD));
                underline.setUnderline(0.1f, -2f);
                document.add(underline);
            }

        }
    }



    /******水印生成******/
    public static void imageWatermark(String pdfpath) throws IOException, DocumentException {
        /******来源pdf******/
        PdfReader reader = new PdfReader(pdfpath);
        /******目标位置 出去来源pdf名字进行拼接水印版本加_watermark后缀******/
        int splitIndex = pdfpath.lastIndexOf(".");
        String result = pdfpath.substring(0, splitIndex) + "_watermark.pdf";
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(result));
        PdfGState gs1 = new PdfGState();
        /******透明度******/
        gs1.setFillOpacity(0.3f);
        /******图片来源******/
        String imagePath = fontPath.replaceAll("simfang.ttf", "");
        Image image = Image.getInstance(IOUtils.toByteArray(new FileInputStream(imagePath + "salewatermark.jpg")));
        int n = reader.getNumberOfPages();
        for (int i = 1; i <= n; i++) {
            PdfContentByte pdfContentByte = stamp.getOverContent(i);
            pdfContentByte.setGState(gs1);
            image.setAbsolutePosition(-100, -150);
            pdfContentByte.addImage(image);
        }

        stamp.close();
        reader.close();
    }
}
