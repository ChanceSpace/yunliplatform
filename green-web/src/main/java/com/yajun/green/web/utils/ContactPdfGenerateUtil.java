package com.yajun.green.web.utils;

import com.lowagie.text.Document;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.yajun.green.common.utils.CHListUtils;
import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactSupplyContentDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactSupplyDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleModuleDTO;
import com.yajun.user.facade.dto.CompanyDTO;
import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class ContactPdfGenerateUtil {

    public static String fontPath;
    public static String watermarkpdfPath;

    public static String getFontPath() {
        return fontPath;
    }

    public static void setFontPath(String fontPath) {
        ContactPdfGenerateUtil.fontPath = fontPath;
    }

    /**
     * ***传入pdf 全地址 如 results/hello_world.pdf 并生成*****
     */
    public static void generatePDF(String targerPath, ContactParagrah1Object firstParagraphparam, ContactPdfParagrah2Object secondParagraphparam, List<ZuPinVehicleModuleDTO> moduleList, String fontPath, String contactNumber, ZuPinContactDTO contact, CompanyDTO company) {
        setFontPath(fontPath);
        exportPdfDocument(targerPath, firstParagraphparam, secondParagraphparam, moduleList, fontPath, contactNumber, contact, company);
    }

    // 导出Pdf文挡
    private static void exportPdfDocument(String targerPath, ContactParagrah1Object firstParagraphparam, ContactPdfParagrah2Object secondParagraphparam, List<ZuPinVehicleModuleDTO> moduleList, String fontPath, String contactNumber, ZuPinContactDTO contact, CompanyDTO company) {
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
            PdfWriter.getInstance(document, new FileOutputStream( pdfpath.toString()));

            // itext 默认包有中对中文支持文件路径有问题需要改写后重新打包
            // 这里使用本目录下的仿宋（和以前合同一致）"com/yajun/contact/web/util//SIMFANG.TTF" src/com/yajun/contact/web/util//SIMFANG.TTF
            BaseFont bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);

            //在document open 之前 给document设置页码
            HeaderFooter headerFooter = new HeaderFooter(new Phrase("合同日期:" + new LocalDate().toString() + "                                         " + "合同编号:" + contactNumber, fontChinese), false);
            headerFooter.setBorder(Rectangle.BOTTOM);
            //headerFooter.setAlignment(Element.ALIGN_LEFT);
            document.setHeader(headerFooter);

            //在document open 之前 给document设置页码
            HeaderFooter footer = new HeaderFooter(new Phrase("第", fontChinese), new Phrase("页", fontChinese));
            footer.setBorder(Rectangle.NO_BORDER);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.setFooter(footer);

            document.open();
            /******产生合同标题******/
            generateTitle(document, fontChinese, contact.getJiaFangName() + ContactPdfTemplate.CONTACTTITLE);
            /******产生开头甲方乙方******/
            generateBegin(document, fontChinese, ContactPdfTemplate.JIAYISHANGFANG, firstParagraphparam);
            addBodyBody(document, ContactPdfTemplate.YINYAN);
            addBodyTitle(document, ContactPdfTemplate.RULE_1);
            /******产生租赁车辆表******/
            generateZuPinTable(document, fontChinese, moduleList);
            /******条款2******/
            addBodyTitle(document, ContactPdfTemplate.RULE_2);
            if ("C_BAODIAN".equals(contact.getContactBaoDianType())) {
                addSecondeBody(document, ContactPdfTemplate.BODY_2_BAODIAN, secondParagraphparam);
            } else if ("C_BUBAODIAN".equals(contact.getContactBaoDianType())) {
                addSecondeBody(document, ContactPdfTemplate.BODY_2_BUBAODIAN, secondParagraphparam);
            }

            /******条款3******/
            addBodyTitle(document, ContactPdfTemplate.RULE_3);
            addBodyBody(document, ContactPdfTemplate.BODY_3);
            /******条款4******/
            addBodyTitle(document, ContactPdfTemplate.ROLE_4);
            if ("C_BAODIAN".equals(contact.getContactBaoDianType())) {
                addBodyBody(document, ContactPdfTemplate.BODY_4_BAODIAN);
            } else if ("C_BUBAODIAN".equals(contact.getContactBaoDianType())) {
                addBodyBody(document, ContactPdfTemplate.BODY_4_BUBAODIAN);
            }
            /******条款5******/
            addBodyTitle(document, ContactPdfTemplate.ROLE_5);
            addBodyBody(document, ContactPdfTemplate.BODY_5);
            /******条款6******/
            addBodyTitle(document, ContactPdfTemplate.ROLE_6);
            addBodyBody(document, ContactPdfTemplate.BODY_6);
            /******条款7******/
            addBodyTitle(document, ContactPdfTemplate.ROLE_7);
            addBodyBody(document, ContactPdfTemplate.BODY_7);
            /******条款8******/
            addBodyTitle(document, ContactPdfTemplate.ROLE_8);
            addBodyBody(document, ContactPdfTemplate.BODY_8);
            /******条款9******/
            addBodyTitle(document, ContactPdfTemplate.ROLE_9);
            addBodyBody(document, ContactPdfTemplate.BODY_9);
            /******条款10******/

            addBodyTitle(document, ContactPdfTemplate.ROLE_10);
            addBodyBody(document, ContactPdfTemplate.BODY_10);
            /******条款11******/
            addBodyTitle(document, ContactPdfTemplate.ROLE_11);
            addBodyBody(document, ContactPdfTemplate.BODY_11);
            /******条款12******/
            addBodyTitle(document, ContactPdfTemplate.ROLE_12);


            if ("C_BAODIAN".equals(contact.getContactBaoDianType()) && "C_RENT".equals(contact.getContactType())) {
                addBodyBody(document, ContactPdfTemplate.BODY_12_BAODIAN_ZU);
            }
            if ("C_BUBAODIAN".equals(contact.getContactBaoDianType()) && "C_RENT".equals(contact.getContactType())) {
                addBodyBody(document, ContactPdfTemplate.BODY_12_BUBAODIAN_ZU);
            }
            if ("C_BAODIAN".equals(contact.getContactBaoDianType()) && "C_RENT_SALE".equals(contact.getContactType())) {
                addBodyBody(document, ContactPdfTemplate.BODY_12_BAODIAN_ZUZHUANSHOU);
            }
            if ("C_BUBAODIAN".equals(contact.getContactBaoDianType()) && "C_RENT_SALE".equals(contact.getContactType())) {
                addBodyBody(document, ContactPdfTemplate.BODY_12_BUBAODIAN_ZUZHUANSHOU);
            }

            /******条款13******/
            addBodyTitle(document, ContactPdfTemplate.ROLE_13);
            addBodyBody(document, ContactPdfTemplate.BODY_13);
            /******条款14******/
            addBodyTitle(document, ContactPdfTemplate.ROLE_14);
            addBodyBody(document, ContactPdfTemplate.BODY_14);

            /******甲乙方签字确认******/
            //租转售不包电和租赁包电 增加换行 单列一页
            if ("C_BAODIAN".equals(contact.getContactBaoDianType())) {
                document.newPage();
            }
            if ("C_BUBAODIAN".equals(contact.getContactBaoDianType()) && "C_RENT_SALE".equals(contact.getContactType())) {
                document.newPage();
            }

            generateEndTable(document, company);

            //是否有补充协议 每个补充协议新起一页
            if (CHListUtils.hasElement(contact.getZuPinContactSupplyDTOS())) {
                List<ZuPinContactSupplyDTO> zuPinContactSupplyDTOS = contact.getZuPinContactSupplyDTOS();
                for (int k = 0; k < zuPinContactSupplyDTOS.size(); k++) {
                    ZuPinContactSupplyDTO zuPinContactSupplyDTO = zuPinContactSupplyDTOS.get(k);
                    HeaderFooter headerFooter2 = new HeaderFooter(new Phrase("合同日期:" + new LocalDate().toString() + "                                      " + "协议编号:" + contactNumber + "-" + (k + 1), fontChinese), false);
                    headerFooter2.setBorder(Rectangle.BOTTOM);
                    document.setHeader(headerFooter2);
                    document.newPage();
                    generateTitle(document, fontChinese, ContactXieYiTemplate.XIEYITITLE);
                    generateBegin(document, fontChinese, ContactXieYiTemplate.JIAYISHANGFANG, firstParagraphparam);
                    generateXieYiBegin(document, ContactXieYiTemplate.XIEYIHEADER1 + contact.getJiaFangName() + ContactXieYiTemplate.XIEYIHEADER2, contactNumber, fontChinese);
                    List<ZuPinContactSupplyContentDTO> zuPinContactSupplyContentDTOS = zuPinContactSupplyDTO.getZuPinContactSupplyContentDTOS();
                    StringBuilder builder = new StringBuilder();
                    int j = 0;
                    for (int i = 0; i < zuPinContactSupplyContentDTOS.size(); i++) {
                        if (i == 0) {
                            builder.append("    " + (i + 1) + "." + zuPinContactSupplyContentDTOS.get(i).getContent() + "\n");
                        } else {
                            builder.append("     " + (i + 1) + "." + zuPinContactSupplyContentDTOS.get(i).getContent() + "\n");
                        }
                        j = i + 1;
                    }
                    builder.append("     " + (j + 1) + "." + ContactXieYiTemplate.XIEYIBODYFU1 + "\n");
                    builder.append("     " + (j + 2) + "." + ContactXieYiTemplate.XIEYIBODYFU2_1 + contact.getJiaFangName() + ContactXieYiTemplate.XIEYIBODYFU2_2 + "\n" + "\n");
                    addBodyBody(document, builder.toString());

                    document.newPage();
                    generateXieYiEndTable(document, fontChinese, company);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();

        /******生成带水印PDF******/

        try {
            imageWatermark(watermarkpdfPath, company);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void generateTitle(Document document, Font font, String title) throws Exception {
        Paragraph headerParagrah;
        headerParagrah = new Paragraph(title, resetFontSize(ContactPdfTemplate.BIAOTISIZE, Font.BOLD));
        headerParagrah.setAlignment(Element.ALIGN_CENTER);
        document.add(headerParagrah);
    }

    /**
     * ***使用指定大小的仿宋字体*****
     */
    public static Font resetFontSize(int newSize) throws Exception {
        BaseFont bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontChinese = new Font(bfChinese, newSize, Font.NORMAL);
        return fontChinese;
    }

    /**
     * ***使用指定大小 指定样式的 仿宋字体*****
     */
    public static Font resetFontSize(int newSize, int fontType) throws Exception {
        BaseFont bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontChinese = new Font(bfChinese, newSize, fontType);
        return fontChinese;
    }


    private static void generateZuPinTable(Document document, Font font, List<ZuPinVehicleModuleDTO> moduleList) throws Exception {
        //创建一个6列的表
        Table table = new Table(6);
        table.setWidth(100);
        table.setCellspacing(4f);
        //设置列之间的相对宽度
        int[] width = {25, 15, 25, 10, 10, 30};//设置每列宽度比例
        table.setWidths(width);
        //生成表头
        generateTableHeader(table, resetFontSize(12), document);
        generateTableBody(table, resetFontSize(12), document, moduleList);
        table.setAlignment(Element.ALIGN_CENTER);

    }

    private static void generateTableHeader(Table datatable, Font font, Document document) throws Exception {

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
        datatable.addCell(getParagraphInCenter(new Paragraph("数量", font)));
        datatable.addCell(getParagraphInVertical(new Paragraph("具体车辆信息（车牌号、车架号等）", font)));

    }

    private static void generateTableBody(Table datatable, Font font, Document document, List<ZuPinVehicleModuleDTO> params) throws Exception {
        // 添加表头元素
        for (ZuPinVehicleModuleDTO param : params) {
            datatable.addCell(getParagraphInCenter(new Paragraph(param.getModuleName(), font)));
            datatable.addCell(getParagraphInCenter(new Paragraph(param.getModuleType(), font)));

            String moduleDianLiang = StringUtils.hasText(param.getModuleDianLiang()) ? "-" + param.getModuleDianLiang() + "度电" : "";
            datatable.addCell(getParagraphInCenter(new Paragraph(param.getModuleBrand() + moduleDianLiang, font)));
            datatable.addCell(getParagraphInCenter(new Paragraph("白色", font)));
            datatable.addCell(getParagraphInCenter(new Paragraph(String.valueOf(param.getRentNumber()), font)));
            datatable.addCell(getParagraphInVertical(new Paragraph("以实际交付车辆为准", font)));
        }
        document.add(datatable);
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

    /**
     * ***传入段落返回 水平居中垂直居中的 Cell*****
     */
    protected static Cell getParagraphInCenter(Paragraph paragraph) throws Exception {

        Cell cell = new Cell(paragraph);
        cell.setUseAscender(true);
        cell.setUseBorderPadding(true);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        return cell;
    }

    /**
     * ***只要垂直居中*****
     */
    protected static Cell getParagraphInVertical(Paragraph paragraph) throws Exception {
        Cell cell = new Cell(paragraph);
        cell.setUseAscender(true);
        cell.setBorderWidth(1);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        return cell;
    }

    /**
     * ***第二段需要填参数的 替换里面的英文为传入参数 *****
     */
    protected static void addSecondeBody(Document document, String body, ContactPdfParagrah2Object param) throws Exception {

        String yajinOne = param.getYajinOne();
        String zujinOne = param.getZujinOne();
        String qixian = param.getQixian();
        String taishu = param.getTaishu();
        String yajinTotal = param.getYajinTotal();
        String yajinTotalFanti = param.getYajinTotalFanti();
        String shouyuezujinHeji = param.getShouyuezujinHeji();
        String shouyuezujinHejiFanti = param.getShouyuezujinHejiFanti();
        String[] params = {yajinOne, zujinOne, qixian, taishu, yajinTotal, yajinTotalFanti, shouyuezujinHeji, shouyuezujinHejiFanti};

        String result = body.replaceAll("yajinOne", "!")
                .replaceAll("zujinOne", "!")
                .replaceAll("qixian", "!")
                .replaceAll("taishu", "!")
                .replaceAll("yajinTotal", "!")
                .replaceAll("YjTotalFanti", "!")
                .replaceAll("shouyuezujinHeji", "!")
                .replaceAll("YZFanti", "!");


        String[] second = result.split("!");
        for (int i = 0; i < second.length; i++) {
            document.add(new Chunk(second[i], resetFontSize(ContactPdfTemplate.RULESIZE, Font.NORMAL)));
            if (i <= 7) {
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

    private static void generateBegin(Document document, Font font, String body, ContactParagrah1Object firstParagraphparam) throws Exception {

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
        String[] params = {jiaFangCompanyName, jiaFangFaren, jiaFangAddress, jiaFangPostcode, companyName, faren, address, postcode};
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

    private static void generateEnd(Document document, Font font, String body) throws Exception {
        Paragraph endParagrah;
        endParagrah = new Paragraph(body, resetFontSize(14, Font.BOLD));
        endParagrah.setLeading(30f);
        document.add(endParagrah);
    }

    private static void generateGaiZhang(Document document, Font font, String body) throws Exception {
        Paragraph endParagrah;
        endParagrah = new Paragraph(body, resetFontSize(14, Font.BOLD));
        document.add(endParagrah);
    }

    private static void generateXieYiBegin(Document document, String body, String contactNumber, Font font) throws Exception {
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

    /**
     * ***水印生成*****
     */
    public static void imageWatermark(String pdfpath, CompanyDTO company) throws IOException, DocumentException {

        /******来源pdf******/
        PdfReader reader = new PdfReader(pdfpath);
        /******目标位置 出去来源pdf名字进行拼接水印版本加_watermark后缀******/
        int splitIndex = pdfpath.lastIndexOf(".");
        String result = pdfpath.substring(0, splitIndex) + "_watermark.pdf";
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(result));
        PdfGState gs1 = new PdfGState();
        /******透明度******/
        gs1.setFillOpacity(0.4f);
        /******图片来源******/
        String imagePath = fontPath.replaceAll("simfang.ttf", "");
        Image image = null;
        if ("201701010101_1234567890123402".equals(company.getUuid())) {
            image = Image.getInstance(IOUtils.toByteArray(new FileInputStream(imagePath + "watermark.jpg")));
        } else {
            image = Image.getInstance(IOUtils.toByteArray(new FileInputStream(imagePath + "watermark_none.jpg")));
        }

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

    static void generateEndTable(Document document, CompanyDTO companyDTO) throws Exception {
        //创建一个6列的表
        Table table = new Table(2);
        table.setWidth(100);
        table.setCellspacing(4f);
        table.setDefaultCellBorder(PdfPCell.NO_BORDER);
        table.setBorderColor(Color.white);
        //设置列之间的相对宽度
        int[] width = {50,50};//设置每列宽度比例
        table.setWidths(width);
        generateEndTableBody(table, resetFontSize(13, Font.BOLD), document, companyDTO);
        table.setAlignment(Element.ALIGN_CENTER);
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
        table.addCell(getParagraphInLeft(new Paragraph("乙方（盖章）：",  resetFontSize(13, Font.BOLD))));

        generateEndTableBody(table, resetFontSize(13, Font.BOLD), document, companyDTO);
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
        datatable.addCell(getParagraphInLeft(new Paragraph("乙方：", font)));

        Paragraph a2 = new Paragraph("签约代表：", font);
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell2 = new Cell(a2);
        cell2.setUseAscender(true);
        cell2.setHorizontalAlignment(Cell.LEFT);
        cell2.setVerticalAlignment(Cell.TOP);
        datatable.addCell(cell2);
        datatable.addCell(getParagraphInLeft(new Paragraph("签约代表：", font)));


        Paragraph a3 = new Paragraph("委托代理人：", font);
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell3 = new Cell(a3);
        cell3.setUseAscender(true);
        cell3.setHorizontalAlignment(Cell.LEFT);
        cell3.setVerticalAlignment(Cell.TOP);
        datatable.addCell(cell3);
        datatable.addCell(getParagraphInLeft(new Paragraph("委托代理人：", font)));

        Paragraph a4 = new Paragraph("开户银行：" + companyDTO.getBankName(), font);
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell4 = new Cell(a4);
        cell4.setUseAscender(true);
        cell4.setHorizontalAlignment(Cell.LEFT);
        cell4.setVerticalAlignment(Cell.TOP);
        datatable.addCell(cell4);
        datatable.addCell(getParagraphInLeft(new Paragraph("开户银行：", font)));


        Paragraph a5 = new Paragraph("银行帐号："+companyDTO.getBankNum(), font);
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell5 = new Cell(a5);
        cell5.setUseAscender(true);
        cell5.setHorizontalAlignment(Cell.LEFT);
        cell5.setVerticalAlignment(Cell.TOP);
        datatable.addCell(cell5);
        datatable.addCell(getParagraphInLeft(new Paragraph("银行帐号：", font)));

        Paragraph a6 = new Paragraph("签订时间：     年    月     日", font);
        a1.setAlignment(Element.ALIGN_CENTER);
        Cell cell6 = new Cell(a6);
        cell6.setUseAscender(true);
        cell6.setHorizontalAlignment(Cell.LEFT);
        cell6.setVerticalAlignment(Cell.TOP);
        datatable.addCell(cell6);
        datatable.addCell(getParagraphInLeft(new Paragraph("签订时间：     年    月     日", font)));

        document.add(datatable);
    }

    /******传入段落返回 水平居中垂直居中的 Cell******/
    protected static Cell getParagraphInLeft(Paragraph paragraph) throws Exception {
        Cell cell = new Cell(paragraph);
        cell.setUseAscender(true);
        cell.setUseBorderPadding(true);
        cell.setHorizontalAlignment(Cell.LEFT);
        cell.setVerticalAlignment(Cell.TOP);
        return cell;
    }


}  