<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="yj" uri="http://www.rajaev.com" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
</head>
<div>
    <fieldset class="fieldset_box">
        <div class="scum_nav">当前位置：<a href="javascript:;" onclick="backtooverview()" class="font_33a7fd">合同列表</a><img src="${pageContext.request.contextPath}/build/images/arrow_right.png" width="10">合同编辑</div>
    <%--合同主信息部分--%>
    <spring-form:form commandName="zuPinContact" id="basic_validate" name="basic_validate" novalidate="novalidate" class="layui-form" method="post">
        <spring-form:input type="hidden" path="uuid"/>
        <input type="hidden" id="jiaFangUuid" name="jiaFangUuid" value="${zuPinContact.jiaFangUuid}"/>
        <input type="hidden" id="yiFangUuid" name="yiFangUuid" value="${zuPinContact.yiFangUuid}"/>

        <fieldset class="layui-elem-field">
            <legend><i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;合同信息</legend>

            <div class="layui-field-box">
                <div class="layui-form-item">
                    <label class="layui-form-label">所属销售<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input  path="saleManName" lay-verify="required" placeholder="" autocomplete="off" class="layui-input"/>
                        <label class="error" style="color: red;"><spring-form:errors path="saleManName" style="color: red;"/></label>
                    </div>
                </div>
                <c:if test="${zuPinContact.uuid != null && zuPinContact.uuid != ''}">
                    <div class="layui-form-item">
                        <label class="layui-form-label">合同编号 </label>
                        <div class="layui-input-block">
                            <spring-form:input path="contactNumber" class="layui-input" readonly="true"/>
                        </div>
                    </div>
                </c:if>
                <div class="layui-form-item">
                    <label class="layui-form-label">出租方名称<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input path="jiaFangName" maxlength="100" class="layui-input"/>
                        <label class="error" style="color: red;"><spring-form:errors path="jiaFangName"/></label>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">出租方法人<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input path="jiaFangFaRen" maxlength="20" class="layui-input"/>
                        <label class="error" style="color: red;"><spring-form:errors path="jiaFangFaRen"/></label>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">出租方地址<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input path="jiaFangAddress" maxlength="100" class="layui-input"/>
                        <label class="error" style="color: red;"><spring-form:errors path="jiaFangAddress"/></label>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">出租方邮编<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input path="jiaFangPostCode" maxlength="6" class="layui-input"/>
                        <label class="error" style="color: red;"><spring-form:errors path="jiaFangPostCode"/></label>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label" for="contactType">合同类型<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <select  class="layui-input" id="contactType" name="contactType" >
                            <c:forEach items="${contactTypes}" var="type">
                                <option value="${type.value}" <c:if test="${type.value == zuPinContact.contactType}">selected="true"</c:if>>${type.label}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" for="contactType">包含电费<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <select class="layui-input" id="baodianTypes" name="contactBaoDianType" >
                            <c:forEach items="${baodianTypes}" var="type">
                                <option value="${type.value}" <c:if test="${type.value == zuPinContact.contactBaoDianType}">selected="true"</c:if>>${type.label}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label" for="yiFangName">承租方名称<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input id="yiFangName" path="yiFangName" maxlength="100" class="layui-input" placeholder="请输入客户名称，系统将根据你输入的关键字自动完成匹配，如京东，红旗等"/>
                        <label class="error" style="color: red;"><spring-form:errors path="yiFangName"/></label>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label" for="contactType">承租方类型<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <select class="layui-input" id="yiFangType" name="yiFangType" lay-filter="yiFangType">
                            <c:forEach items="${userTypes}" var="usertype">
                                <option value="${usertype.value}" <c:if test="${usertype.value == zuPinContact.yiFangType}">selected="true"</c:if>>${usertype.label}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>


                <div id="personinfo">
                    <div class="layui-form-item">
                        <label class="layui-form-label">承租方法人<i class="font_E60012">*</i></label>
                        <div class="layui-input-block">
                            <spring-form:input id="yiFangFaRen" path="yiFangFaRen" maxlength="20" class="layui-input"/>
                            <label class="error" style="color: red;"><spring-form:errors path="yiFangFaRen"/></label>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">承租方地址<i class="font_E60012">*</i></label>
                        <div class="layui-input-block">
                            <spring-form:input id="yiFangAddress" path="yiFangAddress" maxlength="100" class="layui-input"/>
                            <label class="error" style="color: red;"><spring-form:errors path="yiFangAddress"/></label>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">承租方邮编<i class="font_E60012">*</i></label>
                        <div class="layui-input-block">
                            <spring-form:input id="yiFangPostCode" path="yiFangPostCode" maxlength="6" class="layui-input"/>
                            <label class="error" style="color: red;"><spring-form:errors path="yiFangPostCode"/></label>
                        </div>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">承租方联系人<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input id="contactPerson" path="contactPerson" maxlength="10" class="layui-input"/>
                        <label class="error" style="color: red;"><spring-form:errors path="contactPerson"/></label>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">承租方联系电话<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input id="contactPhone" path="contactPhone" maxlength="11" class="layui-input" placeholder="系统自动催款短信，请核实无误后填写"/>
                        <label class="error" style="color: red;"><spring-form:errors path="contactPhone"/></label>
                    </div>
                </div>

                <c:if test="${zuPinContact.zuPinContactBaoYueType==null}">
                    <div class="layui-form-item">
                        <label class="layui-form-label">租赁方式<i class="font_E60012">*</i></label>
                        <div class="layui-input-block">
                            <select  id="zuPinContactBaoYueType" name="zuPinContactBaoYueType">
                                <c:forEach items="${zuPinBaoYueTypes}" var="type">
                                    <option value="${type.value}" <c:if test="${type.value == zuPinContact.zuPinContactBaoYueType}">selected="true"</c:if>>${type.label}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:if>

                <c:if test="${zuPinContact.zuPinContactBaoYueType!=null}">
                    <div class="layui-form-item">
                        <label class="layui-form-label">租赁方式</label>
                        <div class="layui-input-block">
                            <input  class="layui-input" value="${zuPinContact.zuPinContactBaoYueType.description}">
                            <spring-form:input path="zuPinContactBaoYueType" maxlength="11" class="layui-input" readonly="true" type="hidden"/>
                        </div>
                    </div>
                </c:if>


                <div class="layui-form-item">
                    <label class="layui-form-label">车辆押金</label>
                    <div class="layui-input-block">
                        <spring-form:input path="origYaJinFee" maxlength="11" class="layui-input" readonly="true" placeholder="合同中选择车型后自动生成" onkeyup="num(this)"  onbeforepaste="num(this)"/>
                        <label class="error" style="color: red;"><spring-form:errors path="origYaJinFee"/></label>
                    </div>
                </div>

            </div>
        </fieldset>
    </spring-form:form>

    <div class="layui-form-item">
            <button class="layui-btn" lay-submit lay-filter="submit" onclick="vehicleformsubmit()">保存合同</button>
    </div>

    <%--车辆信息部分--%>
    <c:if test="${zuPinContact.uuid != null && zuPinContact.uuid != ''}">

        <fieldset class="fieldset_box">
            <div class="buttons pull-right">
                <div class="widget-content nopadding">
                    <fieldset class="layui-elem-field">
                        <legend>车辆信息</legend>
                        <div style="margin:16px 0 35px 30px"> <a href="javascript:void(0);" onclick="openZuPinContactVehicleModule('${zuPinContact.zuPinContactBaoYueType}','');" class="layui-btn layui-btn-small" id="add" style="float:left;">
                            <i class="layui-icon">&#xe608;</i> 添加车辆信息
                        </a></div>

                        <br/>
                        <div class="layui-field-box">
                            <table class="site-table table-hover">
                                <thead>
                                    <tr>
                                        <th width="5%">序号</th>
                                        <th width="25%" class="th_com">车型信息</th>
                                        <th width="30%" class="th_com">租金信息</th>
                                        <th width="30%" class="th_com">押金信息</th>
                                        <td align="center">操作</td>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="selectModule" items="${zuPinContact.contactModules}" varStatus="counter">
                                    <tr class="gradeX">
                                        <td style="vertical-align: top">${counter.count}</td>
                                        <td style="vertical-align: top">
                                            ${selectModule.moduleName} [${selectModule.moduleType}-${selectModule.moduleBrand}<c:if test="${selectModule.moduleDianLiang != null && selectModule.moduleDianLiang != ''}">(${selectModule.moduleDianLiang}度电)</c:if>-${selectModule.moduleColor}]
                                        </td>
                                        <c:if test="${zuPinContact.zuPinContactBaoYueType == 'B_BAOYUE'}">
                                            <td style="vertical-align: top">
                                                <c:if test="${selectModule.zuPinContactRepayType.description == '先付租金'}">
                                                    <b>${selectModule.zuPinContactRepayType.description}</b>
                                                    &nbsp;&nbsp;
                                                    租用：${selectModule.rentNumber}辆 &nbsp;&nbsp;周期：${selectModule.rentMonth}月 &nbsp;&nbsp;月租/台：${selectModule.actualRentFee}
                                                </c:if>
                                                <c:if test="${selectModule.zuPinContactRepayType.description == '缓付租金'}">
                                                    <b>${selectModule.zuPinContactRepayType.description}</b>
                                                    &nbsp;&nbsp;
                                                    后付：${selectModule.delayMonth}月&nbsp;&nbsp;租用：${selectModule.rentNumber}辆 &nbsp;&nbsp;周期：${selectModule.rentMonth}月 &nbsp;&nbsp;月租/台：${selectModule.actualRentFee}
                                                </c:if>
                                            </td>

                                            <td style="vertical-align: top">
                                                <c:if test="${selectModule.zuPinYaJinType.description == '分期付款'}">
                                                    <b>${selectModule.zuPinYaJinType.description}</b>
                                                    &nbsp;&nbsp;
                                                    首付/台：${selectModule.shouFu} &nbsp;&nbsp;周期：${selectModule.fenQiShu}月 &nbsp;&nbsp;月供/台：${selectModule.yueGong}
                                                </c:if>
                                                <c:if test="${selectModule.zuPinYaJinType.description == '全款支付'}">
                                                    <b>${selectModule.zuPinYaJinType.description}</b>
                                                    &nbsp;&nbsp;
                                                    押金/台: ${selectModule.singleYaJin}
                                                </c:if>
                                            </td>
                                        </c:if>

                                        <c:if test="${zuPinContact.zuPinContactBaoYueType == 'B_RIJIE'}">
                                            <td style="vertical-align: top">
                                                 租用：${selectModule.rentNumber}辆  &nbsp;&nbsp;日租/台：${selectModule.actualRentFee}
                                            </td>

                                            <td style="vertical-align: top">
                                                 押金/台: ${selectModule.singleYaJin}
                                            </td>
                                        </c:if>

                                        <td style="vertical-align: top">
                                            <a href="javascript:void(0);" onclick="deleteZuPinContactVehicleModal('${selectModule.uuid}')" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini"><i class="layui-icon">&#xe640;</i>删除</a>
                                           <a href="javascript:void(0);" onclick="openZuPinContactVehicleModule('${zuPinContact.zuPinContactBaoYueType}','${selectModule.uuid}')" class="layui-btn layui-btn-mini layui-btn-normal"><img src="${pageContext.request.contextPath}/build/images/icon2.png" width="16px" style="margin-right:6px;vertical-align:middle;"/>编辑</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </fieldset>
                </div>
            </div>
    </c:if>


    <%--合同补充协议部分--%>
    <c:if test="${zuPinContact.uuid != null && zuPinContact.uuid != ''}">

        <div class="buttons pull-right">
            <div class="widget-content nopadding">
                <fieldset class="layui-elem-field">
                    <legend>
                        <i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;合同补充协议
                    </legend>
                    <div style="margin:16px 0 35px 30px">
                    <a href="javascript:void(0);" onclick="openZuPinContactSupply();" class="layui-btn layui-btn-small" id="add" style="float:left;">
                        <i class="layui-icon">&#xe608;</i> 添加补充协议
                    </a>
                    </div>
                    <br/>

                    <div class="layui-field-box">
                        <table class="site-table table-hover">
                            <thead>
                            <tr>
                                <th width="15%" class="th_time">编号</th>
                                <th width="75%">补充协议内容</th>
                                <th >操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="zuPinContactSupplyDTOS" items="${zuPinContact.zuPinContactSupplyDTOS}" varStatus="counter">
                                <tr class="gradeX" style="vertical-align: top;">
                                    <td>${zuPinContact.contactNumber}-${counter.count}</td>
                                    <td>
                                        <c:forEach var="zuPinContactSupplyContent" items="${zuPinContactSupplyDTOS.zuPinContactSupplyContentDTOS}" varStatus="counter">
                                            ${counter.count}.${zuPinContactSupplyContent.content}
                                            </br>
                                        </c:forEach>
                                    </td>
                                    <td class="center">
                                        <a href="javascript:void(0);" onclick="deleteZuPinContactSupplyModal('${zuPinContactSupplyDTOS.uuid}')" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini"><i class="layui-icon">&#xe640;</i>删除</a>
                                        <a href="javascript:void(0);" onclick="openZuPinContactSupplyContent('${zuPinContactSupplyDTOS.uuid}')" class="layui-btn layui-btn-mini layui-btn-normal"><img src="${pageContext.request.contextPath}/build/images/icon2.png" width="16px" style="margin-right:6px;vertical-align:middle;"/>编辑</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </fieldset>
            </div>
        </div>
    </c:if>

</div>


<!-- jQuery 2.2.0 -->
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/plugins/autocomplete/jquery.autocomplete.js"></script>
<script src="${pageContext.request.contextPath}/plugins/autocomplete/browser.js"></script>
<script src="${pageContext.request.contextPath}/plugins/popup/modal.popup.js" type="text/javascript"></script>

<script type="text/javascript">
    var selectContactVehicleUuid;
    var selectContactSupplyUuid;
    var selectContactYaJinUuid;
    var message = ${message};

    layui.config({
        base: '${pageContext.request.contextPath}/plugins/layui/modules/'
    });
    layui.use(['laypage', 'layer','form'], function() {
    });
    layui.use(['form'], function() {
        var form = layui.form;
        var layer = layui.layer;
        form.render();
        if(message) {
            layer.open({"content":"合同保存成功","time":2000});
        }
    });

    $(document).ready(function() {
        $("#yiFangName").autocomplete('${pageContext.request.contextPath}/contact/getsearchcustomers.html', {
            minChars: 1,
            max: 10,
            delay:1000,
            highlight: false,
            scroll: false,
            matchCase: true,
            dataType: "json",
            extraParams: { searchWords: function() { return $('#yiFangName').val(); }},
            parse: function(data) {
                if(data.length <= 0) {
                    $('#yiFangNameUuid').val("");
                }

                return $.map(data, function(row) {
                    return {
                        data: row,
                        value: row.uuid,
                        result: row.name
                    }
                });
            },
            formatItem: function(item) {
                return item.name;
            }
        }).result(function(e, item) {
            $('#yiFangUuid').val(item.uuid);
            $('#yiFangFaRen').val(item.faRen);
            $('#yiFangPostCode').val(item.postCode);
            $('#yiFangAddress').val(item.address);
            $('#contactPerson').val(item.lianXiRen);
            $('#contactPhone').val(item.mobile);
        });
    });

    function searchAndChange(yiFangValue) {
        var b = yiFangValue;
        var aa = $("#yiFangType").siblings().find(".layui-anim.layui-anim-upbit").find("dd[lay-value="+b+"]").text();
        $("#yiFangType").siblings("div[class='layui-unselect layui-form-select']").find("input").val(aa);
        $("#yiFangType").siblings().find(".layui-anim.layui-anim-upbit").find("dd[lay-value="+b+"]").addClass("layui-this").siblings().removeClass("layui-this")
    }

    function  backtooverview() {
        if(${zuPinContact.canBeExecute}){
            window.location.href='${pageContext.request.contextPath}/contact/zupincontactoverview.html';
        }else {
            layer.confirm('没有填写车型信息将不能执行合同？', {
                btn : [ '确定', '取消' ]//按钮
            }, function(index) {
                layer.close(index);
                window.location.href='${pageContext.request.contextPath}/contact/zupincontactoverview.html';
            });
        }
    }
    
    
    //弹出框部分
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

	function openZuPinContactVehicleModule(zuPinContactBaoYueType,contactVehicleModuleUuid) {

        if(zuPinContactBaoYueType=="B_BAOYUE"){
            window.location.href = '${pageContext.request.contextPath}/contact/zupincontactvehicleform.html?contactVehicleModuleUuid=' + contactVehicleModuleUuid + '&zuPinContactUuid=${zuPinContact.uuid}';
        }
        if(zuPinContactBaoYueType=="B_RIJIE"){
            window.location.href = '${pageContext.request.contextPath}/contact/zupincontactrijievehicleform.html?contactVehicleModuleUuid=' + contactVehicleModuleUuid + '&zuPinContactUuid=${zuPinContact.uuid}';
        }

	}

    function deleteZuPinContactVehicleModal(contactVehicleUuid) {
        selectContactVehicleUuid = contactVehicleUuid;
        layer.confirm('请确认是否删除该车型信息？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href = '${pageContext.request.contextPath}/contact/zupincontactvehicledelete.html?contactVehicleUuid=' + selectContactVehicleUuid + '&zuPinContactUuid=${zuPinContact.uuid}';
       });
    }

    //新增补充协议
    function openZuPinContactSupply() {
        window.location.href = '${pageContext.request.contextPath}/contact/zupincontactsupplyform.html?' + '&zuPinContactUuid=${zuPinContact.uuid}';
    }

    function deleteZuPinContactSupplyModal(contactSupplyUuid) {
       selectContactSupplyUuid = contactSupplyUuid;
       layer.confirm('请确认是否删除该补充协议条款？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href = '${pageContext.request.contextPath}/contact/zupincontactsupplydelete.html?contactSupplyUuid=' + selectContactSupplyUuid + '&zuPinContactUuid=${zuPinContact.uuid}';
       });
    }

//    添加补充协议内容
    function openZuPinContactSupplyContent(contactSupplyUuid) {
        window.location.href = '${pageContext.request.contextPath}/contact/zupincontactsupplycontentform.html?contactSupplyUuid=' + contactSupplyUuid + '&zuPinContactUuid=${zuPinContact.uuid}';
    }

    //验证金额输入
    function num(obj) {
        obj.value = obj.value.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
        obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字
        obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个, 清除多余的
        obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
    }

    function vehicleformsubmit() {
        jQuery('#basic_validate').submit();
    }

</script>
</body>
</html>
