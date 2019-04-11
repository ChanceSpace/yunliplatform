<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="yj" uri="http://www.rajaev.com" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>绿色共享运力平台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/autocomplete/jquery.autocomplete.css" />

    <style>
        .info-box {
            height: 50px;
            padding: 20px;
            border: 1px solid #e2e2e2;
            border-radius: 3px;
        }
        .info-box h2{font-size:18px;margin-bottom:6px;}
        .info-box.active {
            background: #ff5a00;
            color: #fff;
        }
        .layui-elem-field{padding:15px;}
        .layui-elem-field legend {
            font-size: 18px;
        }

        .detail-title {
            margin: 20px 0 10px;
            font-size: 16px;
        }

        .layui-icon {
            margin-right: 6px;
        }
    </style>
</head>
<body>

<div  style="margin:20px;">
    <fieldset class="fieldset_box">

        <yj:security grantRoles="ROLE_GREEN_CARRIER">
            <yj:security grantRoles="ROLE_GREEN_CARRIER">
                <div class="scum_nav">当前位置：<a href="${pageContext.request.contextPath}/carrier/zupincontactcarrieroverview.html" class="font_33a7fd">合同列表</a><img src="${pageContext.request.contextPath}/build/images/arrow_right.png" width="10">合同详情</div>
            </yj:security>
        </yj:security>
        <yj:security grantRoles="ROLE_GREEN_ADMIN,ROLE_GREEN_CARRIER_RENT,ROLE_GREEN_FINANCE">
            <div class="scum_nav">当前位置：<a href="${pageContext.request.contextPath}/contact/zupincontactoverview.html" class="font_33a7fd">合同列表</a><img src="${pageContext.request.contextPath}/build/images/arrow_right.png" width="10">合同详情</div>
        </yj:security>
        <br/>

        <%--导入上面那个状态的线--%>
        <%@include file="zupincontactdetailadditionpackage/zupincontactstatusline.jsp"%>

        <%-----------------------------合同条款部分-----------------------------%>
        <fieldset class="layui-elem-field">
            <legend><i class="layui-icon">&#xe705;</i>合同条款</legend>
            <div class="layui-row layui-col-space5">
                <div class="layui-col-md4">
                    <b>出租方</b>：${zuPinContact.jiaFangName}
                    <br/>
                    <b>法定代表</b>：${zuPinContact.jiaFangFaRen}
                    <br/>
                    <b>地址</b>：${zuPinContact.jiaFangAddress}
                    <br/>
                    <b>邮编</b>：${zuPinContact.jiaFangPostCode}
                </div>

                <div class="layui-col-md4">
                    <b>承租方</b>：${zuPinContact.yiFangName}
                    <br/>
                    <b>法定代表</b>：${zuPinContact.yiFangFaRen}
                    <br/>
                    <b>承租方联系人</b>：${zuPinContact.contactPerson}
                    <br/>
                    <b>承租方联系电话</b>：${zuPinContact.contactPhone}
                    <br/>
                    <b>地址</b>：${zuPinContact.yiFangAddress}
                    <br/>
                    <b>邮编</b>：${zuPinContact.yiFangPostCode}
                </div>

                <div class="layui-col-md4">
                    <b>合同开始日期</b>：<c:if test="${zuPinContact.beginExecute}">${zuPinContact.startDate}</c:if>
                    <br/>
                    <b>合同结束日期</b>：<c:if test="${zuPinContact.endExecute}">${zuPinContact.endDate}</c:if>
                    <br/>
                    <b>押金金额</b>：${zuPinContact.origYaJinFee}元
                    <br/>
                    <b>合同类型</b>：${zuPinContact.contactBaoDianName}
                    <br/>
                    <b>合同结算方式</b>：${zuPinContact.zuPinContactBaoYueType.description}
                </div>
            </div>
        </fieldset>

        <%--合同补充协议部分--%>
        <c:if test="${!empty zuPinContact.zuPinContactSupplyDTOS}">
            <fieldset class="layui-elem-field">
                <legend><i class="layui-icon">&#xe649;</i>合同补充协议</legend>
                <table class="layui-table" lay-skin="line">
                    <thead>
                    <tr>
                        <th width="12%">编号</th>
                        <th width="88%">补充协议内容</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="zuPinContactSupply" items="${zuPinContact.zuPinContactSupplyDTOS}" varStatus="counter">
                        <tr class="gradeX">
                            <td style="vertical-align: top;">
                                    ${zuPinContact.contactNumber}-${counter.count}
                            </td>
                            <td>
                                <c:forEach var="zuPinContactSupplyContent" items="${zuPinContactSupply.zuPinContactSupplyContentDTOS}" varStatus="counter">
                                    <p>${counter.count}.${zuPinContactSupplyContent.content}</p>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </fieldset>
        </c:if>

        <%--合同选择车型部分--%>
        <fieldset class="layui-elem-field">
            <legend><i class="layui-icon">&#xe698;</i>合同租赁车辆信息</legend>
            <table class="layui-table">
                <thead>
                <tr>
                    <th width="5%">序号</th>
                    <th width="28%">车型信息</th>
                    <th width="7%">数量</th>
                    <th width="30%">租金信息</th>
                    <th width="30%">押金信息</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="selectModule" items="${zuPinContact.contactModules}" varStatus="counter">
                    <tr class="gradeX">
                        <td style="vertical-align: top">${counter.count}</td>
                        <td style="vertical-align: top">${selectModule.moduleName} [${selectModule.moduleType}-${selectModule.moduleBrand}<c:if test="${selectModule.moduleDianLiang != null && selectModule.moduleDianLiang != ''}">(${selectModule.moduleDianLiang}度电)</c:if>-${selectModule.moduleColor}]</td>
                        <c:if test="${zuPinContact.zuPinContactBaoYueType == 'B_BAOYUE'}">
                            <td style="vertical-align: top">
                                    ${selectModule.rentNumber}辆
                            </td>
                            <td style="vertical-align: top">
                                <c:if test="${selectModule.zuPinContactRepayType.description == '先付租金'}">
                                    ${selectModule.zuPinContactRepayType.description}
                                    &nbsp;&nbsp;
                                    周期：${selectModule.rentMonth}月 &nbsp;&nbsp;月租/台：${selectModule.actualRentFee}
                                </c:if>
                                <c:if test="${selectModule.zuPinContactRepayType.description == '缓付租金'}">
                                    ${selectModule.zuPinContactRepayType.description}
                                    &nbsp;&nbsp;
                                    后付：${selectModule.delayMonth}月 &nbsp;&nbsp;周期：${selectModule.rentMonth}月 &nbsp;&nbsp;月租/台：${selectModule.actualRentFee}
                                </c:if>
                            </td>

                            <td style="vertical-align: top">
                                <c:if test="${selectModule.zuPinYaJinType.description == '分期付款'}">
                                    ${selectModule.zuPinYaJinType.description}
                                    &nbsp;&nbsp;
                                    首付/台：${selectModule.shouFu} &nbsp;&nbsp;周期：${selectModule.fenQiShu}月 &nbsp;&nbsp;月租/台：${selectModule.yueGong}
                                </c:if>
                                <c:if test="${selectModule.zuPinYaJinType.description == '全款支付'}">
                                    ${selectModule.zuPinYaJinType.description}
                                    &nbsp;&nbsp;
                                    押金/台: ${selectModule.singleYaJin}
                                </c:if>
                            </td>
                        </c:if>
                        <c:if test="${zuPinContact.zuPinContactBaoYueType == 'B_RIJIE'}">
                            <td style="vertical-align: top">
                                租用：${selectModule.rentNumber}辆
                            </td>
                            <td style="vertical-align: top">
                                &nbsp;&nbsp;日租/台：${selectModule.actualRentFee}
                            </td>
                            <td style="vertical-align: top">
                                押金/台: ${selectModule.singleYaJin}
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </fieldset>


        <%------------------------------------操作部分---------------------------------------------%>
        <fieldset class="layui-elem-field">
            <legend><i class="layui-icon">&#xe635;</i>合同操作</legend>

            <div class="layui-row layui-col-space5">
                <div class="layui-col-md4">
                    &nbsp;&nbsp;<b>已收租金合计:</b><font color="green"><b>${zuPinContact.zujinHasPay}</b></font>
                    &nbsp;&nbsp;<b>已收押金合计:</b><font color="green"><b>${zuPinContact.yajinHasPay}</b></font>
                </div>
            </div>
            <br/>
            <a href="javascript:void(0)"  onclick="openContactBill('${zuPinContact.zuPinContactBaoYueType}','${zuPinContact.uuid}');" class="layui-btn layui-btn-normal">扣款明细</a>
            <yj:security grantRoles="ROLE_GREEN_CARRIER_RENT,ROLE_GREEN_FINANCE,ROLE_GREEN_ADMIN">

                <yj:security grantRoles="ROLE_GREEN_FINANCE">
                    <c:if test="${zuPinContact.zuPinContactBaoYueType == 'B_BAOYUE'}">
                        <c:if test="${zuPinContact.contactCanBeOver}">
                            <a href="javascript:void(0)" onclick="openContactOverPopup('${zuPinContact.uuid}', 'edit');" class="layui-btn layui-btn-danger">合同结束</a>
                        </c:if>
                    </c:if>
                    <c:if test="${zuPinContact.zuPinContactBaoYueType == 'B_RIJIE'}">
                        <c:if test="${zuPinContact.contactCanBeOver}">
                            <a href="javascript:void(0)" onclick="openRiJieContactOverPopup('${zuPinContact.uuid}', 'edit');" class="layui-btn layui-btn-danger">合同结束</a>
                        </c:if>
                    </c:if>
                </yj:security>

                <c:if test="${zuPinContact.contactStatus != 'S_CREATED'}">
                    <a href="${pageContext.request.contextPath}/contact/zupincontactpdfdownload.html?zuPinContactUuid=${zuPinContact.uuid}" target="_blank" class="layui-btn">下载合同</a>
                </c:if>

            </yj:security>
        </fieldset>

        <%-----------------------------车辆提车---------------------------------%>
        <fieldset class="layui-elem-field">
            <legend><i class="layui-icon">&#xe657;</i>合同提车车辆信息</legend>
            <yj:security
                    grantRoles="ROLE_GREEN_CARRIER_RENT,ROLE_GREEN_ADMIN">
                <c:if test="${zuPinContact.tiCheCanBeOver}">
                    <a href="javascript:void(0)"  onclick="openTiChePopup('${zuPinContact.uuid}','${current}','${keyWords}','${userUuid}');" class="layui-btn">合同提车</a>
                </c:if>
            </yj:security>
            <table class="layui-table">
                <thead>
                <tr>
                    <th width="6%" class="th_com">批次</th>
                    <th width="10%" class="th_com">车型信息</th>
                    <th width="23%" class="th_com">租金信息</th>
                    <th width="25%" class="th_com">押金信息</th>
                    <th width="8%" class="th_com">车牌号</th>
                    <th width="6%" class="th_time">提车日期</th>
                    <th width="6%" class="th_time">本次租金到期日</th>
                    <th width="6%" class="th_time">本批次租赁到期日</th>
                    <th width="6%" class="th_status">状态</th>
                    <yj:security
                            grantRoles="ROLE_GREEN_CARRIER_RENT,ROLE_GREEN_ADMIN">
                        <td class="th_com">操作</td>
                    </yj:security>
                </tr>
                </thead>

                <tbody>
                <c:set var="currentPiCi" value="1"/>
                <c:forEach var="useVehicle" items="${zuPinContact.executeModules}" varStatus="counter">
                    <c:if test="${currentPiCi != useVehicle.tiChePiCi}">
                        <c:set var="currentPiCi" value="${useVehicle.tiChePiCi}"/>
                        <tr>
                            <td align="left" colspan="2">
                            </td>
                            <td colspan="8"></td>
                        </tr>
                    </c:if>

                    <tr class="gradeX">
                        <td ><font color="red"> 第${useVehicle.tiChePiCi}批次</font></td>
                        <td>${useVehicle.moduleBrand}<c:if test="${useVehicle.moduleDianLiang != null && useVehicle.moduleDianLiang != ''}">(${useVehicle.moduleDianLiang}度电)</c:if></td>
                        <td>
                            <c:if test="${zuPinContact.zuPinContactBaoYueType == 'B_BAOYUE'}">
                                <c:if test="${useVehicle.zuPinContactRepayType.description == '先付租金'}">
                                    ${useVehicle.zuPinContactRepayType.description}
                                    &nbsp;&nbsp;
                                    周期：${useVehicle.rentMonth}月 &nbsp;&nbsp;月租：${useVehicle.actualRentFee}
                                </c:if>
                                <c:if test="${useVehicle.zuPinContactRepayType.description == '缓付租金'}">
                                    ${useVehicle.zuPinContactRepayType.description}
                                    &nbsp;&nbsp;
                                    后付：${useVehicle.delayMonth}月 &nbsp;&nbsp周期：${useVehicle.rentMonth}月 &nbsp;&nbsp;月租：${useVehicle.actualRentFee}
                                </c:if>
                            </c:if>
                            <c:if test="${zuPinContact.zuPinContactBaoYueType == 'B_RIJIE'}">
                                    日租/台：${useVehicle.actualRentFee}
                            </c:if>
                        </td>

                        <td>
                            <c:if test="${useVehicle.zuPinYaJinType.description == '分期付款'}">
                                ${useVehicle.zuPinYaJinType.description}
                                &nbsp;&nbsp;
                                首付：${useVehicle.shouFu} &nbsp;&nbsp;周期：${useVehicle.fenQiShu}月 &nbsp;&nbsp;月租：${useVehicle.yueGong}
                            </c:if>
                            <c:if test="${useVehicle.zuPinYaJinType.description == '全款支付'}">
                                ${useVehicle.zuPinYaJinType.description}
                                &nbsp;&nbsp;
                                押金: ${useVehicle.singleYaJin}
                            </c:if>
                        </td>

                        <td>${useVehicle.vehicleNum}<c:if test="${useVehicle.comment!=null&&useVehicle.comment!=''}"><br/><span class="bg_E60012" onclick="showComment('${useVehicle.uuid}')">查看备注</span></td></c:if>
                        <td >${useVehicle.tiCheDate}</td>

                        <c:if test="${zuPinContact.zuPinContactBaoYueType == 'B_BAOYUE'}">
                            <c:if test="${useVehicle.nextFeeDateInShow > timeWeekAfter}">
                                <td ><font color="green">${useVehicle.nextFeeDateInShow}</font></td>
                            </c:if>
                            <c:if test="${useVehicle.nextFeeDateInShow <= timeWeekAfter}">
                                <td ><b><font color="red">${useVehicle.nextFeeDateInShow}</font></b></td>
                            </c:if>
                            <td >${useVehicle.jieshuDateInShow}</td>
                        </c:if>
                        <c:if test="${zuPinContact.zuPinContactBaoYueType == 'B_RIJIE'}">
                            <td></td>
                            <td >
                                <c:if test="${useVehicle.zupinover == true}">
                                    ${useVehicle.jieshuDate}
                                </c:if>
                             </td>
                        </c:if>
                        <c:if test="${!useVehicle.zupinover}">
                            <td><span class="bg_009E94">租赁中</span></td>
                        </c:if>
                        <c:if test="${useVehicle.zupinover}">
                            <td><span class="bg_33a7fd">结束租赁</span></td>
                        </c:if>

                        <yj:security
                                grantRoles="ROLE_GREEN_CARRIER_RENT,ROLE_GREEN_ADMIN">
                            <td style="text-align: center;">
                                <c:if test="${!useVehicle.zupinover}">
                                    <a href="javascript:void(0)" onclick="openVehicleChangePopup('${zuPinContact.uuid}','${useVehicle.vehicleNum}','${useVehicle.uuid}','${zuPinContact.saleManName}','${zuPinContact.contactNumber}');"  class="layui-btn layui-btn-mini layui-btn-normal" style="margin-bottom: 5px"><i class="layui-icon">&#xe64e;</i>申请换车</a>
                                    <a href="javascript:void(0)" onclick="showJieShuZuPinModal('${zuPinContact.zuPinContactBaoYueType}','${zuPinContact.uuid}','${useVehicle.uuid}','${useVehicle.tiChePiCi}','${useVehicle.vehicleNum}')"  class="layui-btn layui-btn-mini layui-btn-danger" style="margin-bottom: 5px;margin-left: 0"><i class="layui-icon">&#xe643;</i>结束租赁</a>

                                </c:if>

                                <yj:security
                                        grantRoles="ROLE_GREEN_FINANCE">
                                    <c:if test="${!useVehicle.zupinover}">
                                        <a href="javascript:void(0)" onclick="showVehicleAdditionModal('${zuPinContact.zuPinContactBaoYueType}','${zuPinContact.uuid}','${useVehicle.uuid}','${useVehicle.tiChePiCi}','${useVehicle.vehicleNum}')"  class="layui-btn layui-btn-mini" style="margin-bottom: 5px;margin-left: 0"><i class="layui-icon">&#xe65e;</i>其他费用</a>
                                    </c:if>
                                </yj:security>
                            </td>
                        </yj:security>
                    </tr>

                    <c:if test="${counter.last}">
                        <tr>
                            <td align="left" colspan="2">
                            </td>
                            <td colspan="8"></td>
                        </tr>
                    </c:if>
                </c:forEach>

                </tbody>
            </table>

        </fieldset>

        <%--充电桩 附件 和 合同操作日志--%>
        <%@include file="zupincontactdetailadditionpackage/zupincontactaddition.jsp"%>
</div>
</section>


<!-- jQuery 2.2.0 -->
<script src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/popup/modal.popup.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/plugins/autocomplete/jquery.autocomplete.js"></script>
<script src="${pageContext.request.contextPath}/plugins/autocomplete/browser.js"></script>
<script src="${pageContext.request.contextPath}/build/js/pictureuploadvalidate.js"></script>

<script type="text/javascript">

    var form;
    layui.config({
        base: '${pageContext.request.contextPath}/plugins/layui/modules/'
    });
    layui.use(['laypage', 'layer','laydate','form','icheck'], function() {
        form = layui.form
    });
    layui.use(['form'], function() {
        var form = layui.form,
            layer = layui.layer;
    });


    jQuery(function() {
        settings = {
            align : 'center',									//Valid values, left, right, center
            top : 50, 											//Use an integer (in pixels)
            width : 800,
            height :600,                                        //Use an integer (in pixels)
            padding : 10,										//Use an integer (in pixels)
            backgroundColor : 'white', 						    //Use any hex code
            source : '', 				    					//Refer to any page on your server, external pages are not valid e.g. http://www.google.co.uk
            borderColor : '#333333', 							//Use any hex code
            borderWeight : 4,									//Use an integer (in pixels)
            borderRadius : 5, 									//Use an integer (in pixels)
            fadeOutTime : 300, 									//Use any integer, 0 : no fade
            disableColor : '#666666', 							//Use any hex code
            disableOpacity : 40, 								//Valid range 0-100
            loadingImage : '${pageContext.request.contextPath}/plugins/popup/loading.gif'
        };
    });

    function openModalPopup(obj) {
        modalPopup(obj.align, obj.top, obj.width, obj.padding, obj.disableColor, obj.disableOpacity, obj.backgroundColor, obj.borderColor, obj.borderWeight, obj.borderRadius, obj.fadeOutTime, obj.source, obj.loadingImage);
    }



    function openTopicDialog(id, method) {
        window.location.href = '${pageContext.request.contextPath}/contact/customerbalancehistory.html?uuid='+'${zuPinContact.yiFangUuid}'+'&zuPinContactUuid='+'${zuPinContact.uuid}'+'&pageRedirect=redirect'+'&saleManName='+'${zuPinContact.saleManName}'+'&contactNumber='+'${zuPinContact.contactNumber}';
    }

    function checkContact(checkPassed) {
        var note = jQuery("#checkNote").val();
        if(note == null || note == '') {
            jQuery("#contactCheckNoteModal").modal();
        } else {
            jQuery("#checkPassed").val(checkPassed);
            jQuery("#contact_check_form").submit();
        }
    }

    //结束单一车辆租聘部分
    var jieshu_zuPinContactUuid = null;
    var jieshu_tiChePiCi = null;
    var jieshu_vehicleNum = null;
    var jieshu_current_jieshu = null;


    function showJieShuZuPinModal(zuPinContactBaoYueType,zuPinContactUuid,executeUuid,tiChePiCi, vehicleNum) {
        jieshu_zuPinContactUuid = null;
        jieshu_tiChePiCi = null;
        jieshu_vehicleNum = null;
        jieshu_current_jieshu = null;
        jieshu_zuPinContactUuid = zuPinContactUuid;
        jieshu_tiChePiCi = tiChePiCi;
        jieshu_vehicleNum = vehicleNum;
        if(zuPinContactBaoYueType=="B_BAOYUE"){
            window.location.href = "${pageContext.request.contextPath}/contact/overzupincontactvehicle.html?zuPinContactUuid=" + jieshu_zuPinContactUuid +"&tiChePiCi="+jieshu_tiChePiCi+"&vehicleNum="+jieshu_vehicleNum+"&executeUuid="+executeUuid;
        }
        if(zuPinContactBaoYueType=="B_RIJIE"){
            window.location.href = "${pageContext.request.contextPath}/contact/overzupinrijievehiceldateselect.html?zuPinContactUuid=" + jieshu_zuPinContactUuid +"&tiChePiCi="+jieshu_tiChePiCi+"&vehicleNum="+jieshu_vehicleNum+"&executeUuid="+executeUuid;
        }
    }

    function showVehicleAdditionModal(zuPinContactBaoYueType,zuPinContactUuid,executeUuid,tiChePiCi, vehicleNum) {
        jieshu_zuPinContactUuid = null;
        jieshu_tiChePiCi = null;
        jieshu_vehicleNum = null;
        jieshu_current_jieshu = null;
        jieshu_zuPinContactUuid = zuPinContactUuid;
        jieshu_tiChePiCi = tiChePiCi;
        jieshu_vehicleNum = vehicleNum;
        window.location.href = "${pageContext.request.contextPath}/contact/zupincontactvehicleadditionbalance.html?zuPinContactUuid=" + jieshu_zuPinContactUuid +"&tiChePiCi="+jieshu_tiChePiCi+"&vehicleNum="+jieshu_vehicleNum+"&executeUuid="+executeUuid;
    }



    //dateSelect 内选择单车结束时间后在此校验 提交表单
    function vehicleJieShu() {
        layer.confirm('结束租赁后，该车将不再将纳入费用结算和短信提示范围请确认？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);

            var vehiclefinishDate = $("#vehiclefinishDate").val();
            var vehicleticheDate = $("#vehicleticheDate").val();
            var vehicleactualDate = $("#vehicleactualDate").val();
            var nowDate = $("#nowDate").val();

            if(vehiclefinishDate>nowDate){
                layer.msg('车辆结束时间不能超过今天');
                return;
            }
            if(vehiclefinishDate<vehicleticheDate){
                layer.msg('车辆结束时间小于提车时间');
                return;
            }
            /*if(vehiclefinishDate<vehicleactualDate){
             layer.msg('车辆结束时间小于上次缴费时间');
             return;
             }*/

            jQuery("#vehicleoverform").submit();

        });
    }

    //dateSelect 内选择单车结束时间后在此校验 提交表单
    function vehicleRiJieJieShu() {
        layer.confirm('结束租赁后，该车将不再将纳入费用结算和短信提示范围请确认？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);

            var vehiclefinishDate = $("#vehiclefinishDate").val();
            var vehicleticheDate = $("#vehicleticheDate").val();
            var vehicleactualDate = $("#vehicleactualDate").val();
            var nowDate = $("#nowDate").val();

            if(vehiclefinishDate>nowDate){
                layer.msg('车辆结束时间不能超过今天');
                return;
            }
            if(vehiclefinishDate<vehicleticheDate){
                layer.msg('车辆结束时间小于提车时间');
                return;
            }
            /*if(vehiclefinishDate<vehicleactualDate){
             layer.msg('车辆结束时间小于上次缴费时间');
             return;
             }*/

            jQuery("#vehiclerijieoverform").submit();

        });
    }


    //整个合同结束 合同结束之前需要判断已经没有未支付的账单了
    function openContactOverPopup(zuPinContactUuid, method) {
        jQuery.ajax({
            type: "GET",
            url: "${pageContext.request.contextPath}/contact/ajaxvalidatenopaybill.html",
            data: { zuPinContactUuid: '${zuPinContact.uuid}'},
            dataType: "json",
            success:  function (result) {
                var paysuccess = result.paysuccess;
                if(paysuccess){
                    window.location.href = '${pageContext.request.contextPath}/contact/zupincontactfinishmanagement.html?zuPinContactUuid=' + zuPinContactUuid;
                }else {
                    window.location.href = '${pageContext.request.contextPath}/carrier/paymentmanagement.html?selectCarrierUuid=' + '${zuPinContact.yiFangUuid}';
                }
            }
        });
    }

    function openRiJieContactOverPopup(zuPinContactUuid, method) {
        jQuery.ajax({
            type: "GET",
            url: "${pageContext.request.contextPath}/contact/ajaxvalidatenopaybill.html",
            data: { zuPinContactUuid: '${zuPinContact.uuid}'},
            dataType: "json",
            success:  function (result) {
                var paysuccess = result.paysuccess;
                if(paysuccess){
                    window.location.href = '${pageContext.request.contextPath}/contact/zupincontactrijiefinishmanagement.html?zuPinContactUuid=' + zuPinContactUuid;
                }else {
                    window.location.href = '${pageContext.request.contextPath}/carrier/paymentmanagement.html?selectCarrierUuid=' + '${zuPinContact.yiFangUuid}';
                }
            }
        });
    }


    //合同结束页面其他金额添加
    function addContentToFinishForm() {
        $(".detailCon").show();
        var lastIndex = $(".tbody").find("tr:last-child").find("td:first-child").text();
        var thisIndex = null;
        if(lastIndex != undefined && lastIndex!=null&lastIndex!=""){
            thisIndex = parseInt(lastIndex)+1;
        }else {
            thisIndex = 1;
        }
        var html = '<tr><td>'+thisIndex+'</td>'+
            '<td><i class="font_E60012" style="position: absolute;right: 9px">*</i> <input placeholder="请输入金额" required class="layui-input overformcount" name="overformcount"/> </td>'+
            '<td>' +
            '<select id="finishCharge" name="finishCharge" class="overformcharge" >' +
            '<option value="ZHI_CHU">支付</option>'+
            '<option value="SHOU_QU">收取</option>'+
            '</select>' +
            '</td>'+
            '<td><input placeholder="请输入备注" name="comment" class="layui-input"/></td>'+
            '<td><a href="javascript:;" data-id="1" data-opt="del" class="deteleBtn layui-btn layui-btn-danger layui-btn-mini" onclick="addContentToFinishFormDelete(this)"><i class="layui-icon">&#xe640;</i>删除</a></td>'+
            "</tr>"
        $(".tbody").append(html);
    }
    function addContentToFinishFormDelete(obj){
        $(obj).parents("tr").remove();
    }

    Date.prototype.format =function(format)
    {
        var o = {
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(),    //day
            "h+" : this.getHours(),   //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
            "S" : this.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
            (this.getFullYear()+"").substr(4- RegExp.$1.length));
        for(var k in o)if(new RegExp("("+ k +")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length==1? o[k] :
                    ("00"+ o[k]).substr((""+ o[k]).length));
        return format;
    }


    //合同结束 根据日期的改变计算后付模式下 应该缴纳的欠款
    function contactOverFormFeeChange(){
        var date = new Date().format('yyyy-MM-dd');

        if( $("#finishDate").val()>date){
            layer.msg('合同结束时间不能超过今天');
            return;
        }
        jQuery.ajax({
            type: "GET",
            url: "${pageContext.request.contextPath}/contact/zupincontactfinishformdatechange.html",
            data: { zuPinContactUuid: '${zuPinContact.uuid}',finishDate: $("#finishDate").val()},
            dataType: "json",
            success:  function (result) {
                var houFuFee = result.houFuFee;
                jQuery('#contactOverHouFuFee').val(houFuFee);
            }
        });
    }


    function saveZuPinContactFinish() {
        var date = new Date().format('yyyy-MM-dd');
        console.log(date)
        if( $("#finishDate").val()>date){
            layer.msg('合同结束时间不能超过今天');
            return;
        }
        var finishNote = jQuery("#finishNote").val();
        var contarray = new Array();
        var chargearray = new Array();

        $(".overformcount").each(function(index,domEle){
            contarray.push($(this).val());
        })
        $(".overformcharge").each(function(index,domEle){
            chargearray.push($(this).val());
        })

        if(contarray.length!=0&&chargearray.length!=0){
            if(contarray.length!=chargearray.length){
                jQuery("#qitaFeeError").css("display", "block");
            }
            for (var j=0;j<chargearray.length;j++){
                if(contarray[j]==null||contarray[j]==""){
                    $("#qitaFeeError").text("其他金额含有空值重新输入");
                    jQuery("#qitaFeeError").css("display", "block");
                    return;
                }

            }
        }


        layer.confirm('结束租赁后，该合同所有车辆将不再纳入计费范围？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            $("#zupincontactoverform").submit();
        });

    }

    //发送短信部分
    function showSendMessageModal(zuPinContact,tiChePiCi,current,keyWords,userUuid) {
        message_zuPinContact = null;
        message_tiChePiCi = null;
        message_current = null;
        message_keyWords = null;
        message_userUuid = null;

        message_zuPinContact = zuPinContact;
        message_tiChePiCi =tiChePiCi;
        message_current = current;
        message_keyWords = keyWords;
        message_userUuid = userUuid;
        jQuery("#sendMessageBody").text("用户将收到一条短信催款提示，请确认是否发送");
        jQuery("#sendMessageButton").attr('onclick','sendMessageSubmit()');
        jQuery("#sendMessageModal").modal();
    }

    function sendMessageSubmit(){
        window.location.href = "${pageContext.request.contextPath}/contact/sendmessagetocustomer.html?zuPinContactUuid="+message_zuPinContact+"&tiChePiCi="+message_tiChePiCi;
    }



    function saveZuPinContactFile() {
        var contactFile = jQuery("#contactFile");
        if (jQuery.trim(contactFile.val()) == '') {
            jQuery("#contactFile_help").css("display", "block");
            return;
        } else {
            //验证图片大小
            var canbeLoad = validatepicturesize(contactFile,1024*1024);
            if(canbeLoad == true){
                jQuery("#contact_file_form").submit();
            }else {
                layer.msg("图片文件超过了1M，请压缩后再上传");
            }
        }
    }

    /***************添加租金************************************/

    function saveMoneyConfirm() {
        var feeTotal = jQuery("#feeTotal").val();
        if (feeTotal ==null || feeTotal==''||isNaN(feeTotal)) {
            jQuery("#feeTotal_help").css("display","block");
            return;
        } else {
            jQuery("#feeTotal_help").css("display","none");
        }
        //今天
        var today = jQuery("input[name='today']").val();
        //合同开始日期
        var startDate = jQuery("input[name='startDate']").val();
        //合同结束日期
        var endDate = jQuery("input[name='endDate']").val();
        //充值时间
        var happendDate = jQuery("#happendDate").val();
        //合同是否提车完毕
        var endExecute = new Boolean()
        var endExecute = jQuery("input[name='endExecute']").val();
        if ( today < happendDate ) {
            jQuery("#happendDate_help").css("display","block");
            return;
        } else {
            jQuery("#happendDate_help").css("display","none");
        }


        layer.confirm('请确认是否对该合同进行租金充值？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            jQuery("#chongzhiForm").submit();
        });
    }

    /***************押金扣费************************************/
    function openKouFeiPopup(zuPinContactUuid,saleManName,contactNumber) {
        settings.source = '${pageContext.request.contextPath}/contact/zupincontactyajinchongzhi.html?zuPinContactUuid='+'${zuPinContact.uuid}'+'&saleManName='+'${zuPinContact.saleManName}'+'&contactNumber='+'${zuPinContact.contactNumber}';
        openModalPopup(settings);
    }
    function saveMoneyConfirm1(chongzhiflag) {
        var feeTotal = jQuery("#feeTotal").val();
        if (feeTotal ==null || feeTotal==''||isNaN(feeTotal)) {
            jQuery("#feeTotal_help").css("display","block");
            return;
        } else {
            jQuery("#feeTotal_help").css("display","none");
        }
        //今天
        var today = jQuery("input[name='today']").val();
        //合同开始日期
        var startDate = jQuery("input[name='startDate']").val();
        //合同结束日期
        var endDate = jQuery("input[name='endDate']").val();
        //充值时间
        var happendDate = jQuery("#happendDate").val();
        //合同是否提车完毕
        var endExecute = new Boolean();
        var endExecute = jQuery("input[name='endExecute']").val();

        if ( today < happendDate ) {
            jQuery("#happendDate_help").css("display","block");
            return;
        } else {
            jQuery("#happendDate_help").css("display","none");
        }

        var message = "";
        if(chongzhiflag){
            message = '请确认是否对该合同进行押金充值？'
        }else {
            message = '扣除押金后，在合同结束时将不会返还请确认'
        }
        layer.confirm(message, {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            jQuery("#chongzhiForm1").submit();
        });
    }
    /**************************细节***************************/
    function showComment(executeuuid){
        settings.source = '${pageContext.request.contextPath}/contact/zupincontactexecutecomment.html?zuPinContactUuid='+'${zuPinContact.uuid}'+'&executeuuid='+executeuuid;
        openModalPopup(settings);
    }

    /***************提车相关************************************/

    function openTiChePopup(zuPinContactUuid,current,keyWords,userUuid) {
        settings.source = '${pageContext.request.contextPath}/contact/zupincontactvehicleget.html?zuPinContactUuid='+'${zuPinContact.uuid}';
        openModalPopup(settings);
    }


    //展示提车输入框
    function showTiCheInfoBox() {
        var selectModuleAndNumber = jQuery('#moduleBrand').val();
        var token= new Array();
        token = selectModuleAndNumber.split('|');
        var module = token[0];
        var leftNumber = token[1];

        var line = "";

        if(leftNumber > 0) {
            jQuery('#selectModuleBrand').val(token[0]);
            jQuery('#vehicle_details_part').css('visibility', 'visible');

            var line = "";
            for(var i=1; i<= leftNumber; i++) {
                line = line + "<div class=\"layui-form-item\">" +
                    "<label class=\"layui-form-label\">第" + i + "辆：</label>" +
                    "<div  class=\"layui-input-block\">" +
                    "<input name=\"vehicleNumber\" maxlength=\"20\" onkeydown=\"vehicleNumberautocomplete("+i+")\" class=\"layui-input  vehicleNumberInput\" id=\"vehicle_"+i+"\" placeholder=\"请输入车牌号(至少两位英文或者数字),并从系统检索结果选择\"/>"+"<input name=\"comment\" maxlength=\"20\" class=\"layui-input\" style=\"margin-top:4px;\" placeholder=\"请输入备注（20字内）\"/>"
                    +"</div></div>";
            }
            jQuery('#vehicle_details_part').html(line);

            for(var i=1; i<= leftNumber; i++) {
                vehicleNumberautocomplete(i)
            }
        }
    }

    function vehicleNumberautocomplete(index) {
        var vehicleid = "#vehicle_"+index;
        jQuery(vehicleid).autocomplete('${pageContext.request.contextPath}/carrier/vehicleautocomplete.html?zuPinContactUuid='+'${zuPinContact.uuid}', {
            minChars: 2,
            max: 10,
            delay:1000,
            highlight: false,
            scroll: false,
            matchCase: true,
            dataType: "json",
            extraParams: { searchWords: function() { return jQuery(vehicleid).val(); }},
            parse: function(data) {

                return jQuery.map(data, function(row) {
                    return {
                        data: row,
                        value: row.vehicleUuid,
                        result: row.vehicleNumber
                    }
                });
            },
            formatItem: function(item) {
                return item.vehicleNumber;
            }
        }).result(function(e, item) {
            jQuery(vehicleid).val(item.vehicleNumber);
        });

    }

    //校验表单
    function saveTiChe() {
        var vehicleNumbers = '';
        jQuery("input[name='vehicleNumber']").each(
            function(){
                var number = jQuery(this).val();
                if(number != undefined && number != '') {
                    vehicleNumbers = vehicleNumbers + number + ',';
                }
            }
        )
        //今天
        var today = jQuery("input[name='today']").val();
        //提车时间
        var tiCheDate = jQuery('#tiCheDate').val();
        //提车时间不能超过今天的前15天
        var fifteendays = jQuery("input[name='fifteendays']").val();
        var zuPinContactUuid = jQuery("input[name='zuPinContactUuid']").val();

        if ( today < tiCheDate || tiCheDate < fifteendays) {
            jQuery("#tiCheDate_help").css("display","block");
            return;
        } else {
            jQuery("#tiCheDate_help").css("display","none");
        }
        jQuery.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/contact/ajaxvalidatevehicleget.html",
            data: { vehicleNumbers: vehicleNumbers,tiCheDate: tiCheDate,zuPinContactUuid:zuPinContactUuid},
            dataType: "json",
            success:  function (result) {
                jQuery('#vehiclenumber_errormessage').html("");
                //获取数据
                var duplicateCar = result.duplicateCar;
                var length = duplicateCar.length;
                if (length > 0 ) {
                    var innerHtml = "提示:" + duplicateCar ;
                    console.log(innerHtml)
                    jQuery('#vehiclenumber_errormessage').html(innerHtml);
                } else {
                    jQuery("#contact_tiche_form").submit();
                }

            }
        });
    }

    /***************换车相关************************************/

    function openVehicleChangePopup(zuPinContactUuid,vehicleNumberBefore,zuPinExecuteUuid,saleManName,contactNumber) {
        settings.source = '${pageContext.request.contextPath}/contact/vehiclechangehistoryform.html?zuPinContactUuid='+zuPinContactUuid+'&vehicleNumberBefore='+vehicleNumberBefore+'&zuPinExecuteUuid='+zuPinExecuteUuid+'&saleManName='+saleManName+'&contactNumber='+contactNumber;
        openModalPopup(settings);
    }

    function getvehicleChangeNumber() {
        jQuery("#targetNumber").autocomplete('${pageContext.request.contextPath}/carrier/vehicleautocomplete.html', {
            minChars: 2,
            max: 10,
            delay:1000,
            highlight: false,
            scroll: false,
            matchCase: true,
            dataType: "json",
            extraParams: { searchWords: function() { return jQuery('#targetNumber').val(); }},
            parse: function(data) {
                if(data.length <= 0) {
                    $("#targetNumber").val("");
                }
                return jQuery.map(data, function(row) {
                    return {
                        data: row,
                        value: row.vehicleUuid,
                        result: row.vehicleNumber
                    }
                });
            },
            formatItem: function(item) {
                return item.vehicleNumber;
            }
        }).result(function(e, item) {
            jQuery("#targetNumber").val(item.vehicleNumber);
        });
    }


    //校验表单
    function changeVehicle() {
        var vehicleNumberNow = '';
        vehicleNumberNow = jQuery('#targetNumber').val();
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/contact/ajaxvalidatevehiclechange.html",
            data: {vehicleNumberNow :vehicleNumberNow},
            dataType: "json",
            success: function (result) {
                jQuery('#vehicleNumber_help').html("");
                //获取数据
                var duplicateCar = result.duplicateCar;
                console.log(duplicateCar);
                var length = duplicateCar.length;
                if (length > 0) {
                    jQuery("#vehicleNumber_help").css("display","block");
                    jQuery('#vehicleNumber_help').text(duplicateCar);
                } else {
                    jQuery("#vehicle_change_form").submit();
                }
            }
        });
    }

    /***************打开合同账单************************************/

    function openContactBill(zuPinContactBaoYueType,zuPinContactUuid) {

        if(zuPinContactBaoYueType=="B_BAOYUE"){
            settings.source = '${pageContext.request.contextPath}/contact/zupincontactrepayoverview.html?zuPinContactUuid='+zuPinContactUuid;
            settings.width = 1200;
            openModalPopup(settings);
        }
        if(zuPinContactBaoYueType=="B_RIJIE"){
            settings.source = "${pageContext.request.contextPath}/contact/zupincontactrijierepayoverview.html?zuPinContactUuid=" + zuPinContactUuid;
            settings.width = 1200;
            openModalPopup(settings);
        }
    }


</script>
</body>
</html>
