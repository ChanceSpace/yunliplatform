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
    <spring-form:form commandName="xiaoShouContact" id="basic_validate" name="basic_validate" novalidate="novalidate" class="layui-form" method="post">
        <spring-form:input type="hidden" path="uuid"/>
        <input type="hidden" id="jiaFangUuid" name="jiaFangUuid" value="${xiaoShouContact.jiaFangUuid}"/>
        <input type="hidden" id="yiFangUuid" name="yiFangUuid" value="${xiaoShouContact.yiFangUuid}"/>

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
                <c:if test="${xiaoShouContact.uuid != null && xiaoShouContact.uuid != ''}">
                    <div class="layui-form-item">
                        <label class="layui-form-label">合同编号 </label>
                        <div class="layui-input-block">
                            <spring-form:input path="contactNumber" class="layui-input" readonly="true"/>
                        </div>
                    </div>
                </c:if>
                <div class="layui-form-item">
                    <label class="layui-form-label">出售方名称<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input path="jiaFangName" maxlength="100" class="layui-input"/>
                        <label class="error" style="color: red;"><spring-form:errors path="jiaFangName"/></label>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">出售方法人<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input path="jiaFangFaRen" maxlength="20" class="layui-input"/>
                        <label class="error" style="color: red;"><spring-form:errors path="jiaFangFaRen"/></label>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">出售方地址<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input path="jiaFangAddress" maxlength="100" class="layui-input"/>
                        <label class="error" style="color: red;"><spring-form:errors path="jiaFangAddress"/></label>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">出售方邮编<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input path="jiaFangPostCode" maxlength="6" class="layui-input"/>
                        <label class="error" style="color: red;"><spring-form:errors path="jiaFangPostCode"/></label>
                    </div>
                </div>

                <c:if test="${xiaoShouContact.xiaoShouType==''||xiaoShouContact.xiaoShouType==null}">
                    <div class="layui-form-item">
                        <label class="layui-form-label" for="contactType">合同类型<i class="font_E60012">*</i></label>
                        <div class="layui-input-block">
                            <select  class="layui-input" id="contactType" name="xiaoShouType" >
                                <c:forEach items="${contactTypes}" var="type">
                                    <option value="${type.value}" <c:if test="${type.value == xiaoShouContact.xiaoShouType}">selected="true"</c:if>>${type.label}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:if>



                <c:if test="${xiaoShouContact.xiaoShouType!=''&&xiaoShouContact.xiaoShouType!=null}">
                    <label class="layui-form-label">合同类型<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <input name="xiaoShouType" type="hidden" class="layui-input" value="${xiaoShouContact.xiaoShouType}">
                        <input  class="layui-input" readonly="true"  value="${xiaoShouContact.xiaoShouType.description}">
                        <label class="error" style="color: red;"><spring-form:errors path="xiaoShouType"/></label>
                    </div>
                </c:if>

                <div class="layui-form-item">
                    <label class="layui-form-label" for="contactType">包含电费<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <select class="layui-input" id="baodianTypes" name="baoDianType" >
                            <c:forEach items="${baodianTypes}" var="type">
                                <option value="${type.value}" <c:if test="${type.value == xiaoShouContact.baoDianType}">selected="true"</c:if>>${type.label}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label" for="yiFangName">购买方名称<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input id="yiFangName" path="yiFangName" maxlength="100" class="layui-input" placeholder="请输入客户名称，系统将根据你输入的关键字自动完成匹配，如京东，红旗等"/>
                        <label class="error" style="color: red;"><spring-form:errors path="yiFangName"/></label>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label" for="contactType">购买方类型<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <select class="layui-input" id="yiFangType" name="yiFangType" lay-filter="yiFangType">
                            <c:forEach items="${userTypes}" var="usertype">
                                <option value="${usertype.value}" <c:if test="${usertype.value == xiaoShouContact.yiFangType}">selected="true"</c:if>>${usertype.label}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>


                <div id="personinfo">
                    <div class="layui-form-item">
                        <label class="layui-form-label">购买方法人<i class="font_E60012">*</i></label>
                        <div class="layui-input-block">
                            <spring-form:input id="yiFangFaRen" path="yiFangFaRen" maxlength="20" class="layui-input"/>
                            <label class="error" style="color: red;"><spring-form:errors path="yiFangFaRen"/></label>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">购买方地址<i class="font_E60012">*</i></label>
                        <div class="layui-input-block">
                            <spring-form:input id="yiFangAddress" path="yiFangAddress" maxlength="100" class="layui-input"/>
                            <label class="error" style="color: red;"><spring-form:errors path="yiFangAddress"/></label>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">购买方邮编<i class="font_E60012">*</i></label>
                        <div class="layui-input-block">
                            <spring-form:input id="yiFangPostCode" path="yiFangPostCode" maxlength="6" class="layui-input"/>
                            <label class="error" style="color: red;"><spring-form:errors path="yiFangPostCode"/></label>
                        </div>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">购买方联系人<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input id="contactPerson" path="contactPerson" maxlength="10" class="layui-input"/>
                        <label class="error" style="color: red;"><spring-form:errors path="contactPerson"/></label>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">购买方联系电话<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input id="contactPhone" path="contactPhone" maxlength="11" class="layui-input" placeholder="系统自动催款短信，请核实无误后填写"/>
                        <label class="error" style="color: red;"><spring-form:errors path="contactPhone"/></label>
                    </div>
                </div>


                <div class="layui-form-item">
                    <label class="layui-form-label">直流充电桩数量<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input id="zhiliuChargeNumber" path="zhiliuChargeNumber" maxlength="11" class="layui-input" placeholder="直流充电桩数量"/>
                        <label class="error" style="color: red;"><spring-form:errors path="zhiliuChargeNumber"/></label>
                    </div>
                </div>


                <div class="layui-form-item">
                    <label class="layui-form-label">交流充电桩数量<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input id="jiaoliuChargeNumber" path="jiaoliuChargeNumber" maxlength="11" class="layui-input" placeholder="交流充电桩数量" onkeyup="num(this)" onbeforepaste="num(this)" />
                        <label class="error" style="color: red;"><spring-form:errors path="jiaoliuChargeNumber"/></label>
                    </div>
                </div>
            </div>
        </fieldset>
    </spring-form:form>

    <div class="layui-form-item">
            <button class="layui-btn" lay-submit lay-filter="submit" onclick="vehicleformsubmit()">保存合同</button>
    </div>

    <%--车辆信息部分--%>
    <c:if test="${xiaoShouContact.uuid != null && xiaoShouContact.uuid != ''}">

        <fieldset class="fieldset_box">
            <div class="buttons pull-right">
                <div class="widget-content nopadding">
                    <fieldset class="layui-elem-field">
                        <legend>车辆信息</legend>
                        <c:if test="${fn:length(xiaoShouContact.origModules)==0}">
                            <div style="margin:16px 0 35px 30px">
                                <a href="javascript:void(0);" onclick="openXiaoShouContactVehicleForm('${xiaoShouContact.xiaoShouType}','');" class="layui-btn layui-btn-small" id="add" style="float:left;">
                                    <i class="layui-icon">&#xe608;</i> 添加车辆信息
                                </a>
                            </div>
                        </c:if>


                        <br/>
                        <div class="layui-field-box">
                            <table class="site-table table-hover">
                                <thead>
                                    <tr>
                                        <th width="5%">序号</th>
                                        <th width="25%" class="th_com">车型信息</th>
                                        <th width="50%" class="th_com">销售信息</th>
                                        <td align="center">操作</td>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="selectModule" items="${xiaoShouContact.origModules}" varStatus="counter">
                                    <tr class="gradeX">
                                        <td style="vertical-align: top">${counter.count}</td>
                                        <td style="vertical-align: top">
                                            ${selectModule.moduleName} [${selectModule.moduleType}-${selectModule.moduleBrand}<c:if test="${selectModule.moduleDianLiang != null && selectModule.moduleDianLiang != ''}">(${selectModule.moduleDianLiang}度电)</c:if>-${selectModule.moduleColor}]
                                        </td>
                                        <%--全款方式 --%>
                                        <c:if test="${xiaoShouContact.xiaoShouType == 'XS_QK'}">
                                            <td style="vertical-align: top">
                                                    <b>全款</b>
                                                    &nbsp;&nbsp;
                                                    售价/台：${selectModule.salePrice} &nbsp;&nbsp;定金/台：${selectModule.dingJin}
                                            </td>
                                        </c:if>
                                        <%--分期方式 --%>
                                        <c:if test="${xiaoShouContact.xiaoShouType == 'XS_ANJIE'}">
                                            <td style="vertical-align: top">
                                                <b>按揭</b>
                                                &nbsp;&nbsp;
                                                售价/台：${selectModule.salePrice} &nbsp;&nbsp;定金/台：${selectModule.dingJin}&nbsp;&nbsp;首付/台：${selectModule.shouFu}&nbsp;&nbsp;尾款/台：${selectModule.weiKuan}
                                            </td>
                                        </c:if>

                                        <td style="vertical-align: top">
                                            <a href="javascript:void(0);" onclick="deleteZuPinContactVehicleModal('${selectModule.uuid}')" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini"><i class="layui-icon">&#xe640;</i>删除</a>
                                           <a href="javascript:void(0);" onclick="openXiaoShouContactVehicleForm('${xiaoShouContact.xiaoShouType}','${selectModule.uuid}')" class="layui-btn layui-btn-mini layui-btn-normal"><img src="${pageContext.request.contextPath}/build/images/icon2.png" width="16px" style="margin-right:6px;vertical-align:middle;"/>编辑</a>
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
    <c:if test="${xiaoShouContact.uuid != null && xiaoShouContact.uuid != ''}">

        <div class="buttons pull-right">
            <div class="widget-content nopadding">
                <fieldset class="layui-elem-field">
                    <legend>
                        <i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;合同补充协议
                    </legend>
                    <div style="margin:16px 0 35px 30px">
                    <a href="javascript:void(0);" onclick="openXiaoShouContactSupply();" class="layui-btn layui-btn-small" id="add" style="float:left;">
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
                            <c:forEach var="zuPinContactSupplyDTOS" items="${xiaoShouContact.zuPinContactSupplies}" varStatus="counter">
                                <tr class="gradeX" style="vertical-align: top;">
                                    <td>${xiaoShouContact.contactNumber}-${counter.count}</td>
                                    <td>
                                        <c:forEach var="zuPinContactSupplyContent" items="${zuPinContactSupplyDTOS.zuPinXiaoShouContactSupplyContentDTOList}" varStatus="counter">
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
                        value: row.cusomerUuid,
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
        if(${xiaoShouContact.canBeExecute}){
            window.location.href='${pageContext.request.contextPath}/contact/xiaoshoucontactoverview.html';
        }else {
            layer.confirm('没有填写车型信息将不能执行合同？', {
                btn : [ '确定', '取消' ]//按钮
            }, function(index) {
                layer.close(index);
                window.location.href='${pageContext.request.contextPath}/contact/xiaoshoucontactoverview.html';
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

	function openXiaoShouContactVehicleForm(xiaoShouType,contactVehicleModuleUuid) {
        window.location.href = '${pageContext.request.contextPath}/contact/xiaoshouvehicleform.html?contactVehicleModuleUuid=' + contactVehicleModuleUuid + '&xiaoShouContactUuid=${xiaoShouContact.uuid}';
	}

    function deleteZuPinContactVehicleModal(contactVehicleUuid) {
        selectContactVehicleUuid = contactVehicleUuid;
        layer.confirm('请确认是否删除该车型信息？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href = '${pageContext.request.contextPath}/contact/xiaoshoucontactvehicledelete.html?contactVehicleUuid=' + selectContactVehicleUuid + '&xiaoShouContactUuid=${xiaoShouContact.uuid}';
       });
    }

    //新增补充协议
    function openXiaoShouContactSupply() {
        window.location.href = '${pageContext.request.contextPath}/contact/xiaoshoucontactsupplyform.html?' + '&xiaoShouContactUuid=${xiaoShouContact.uuid}';
    }

    function deleteZuPinContactSupplyModal(contactSupplyUuid) {
       selectContactSupplyUuid = contactSupplyUuid;
       layer.confirm('请确认是否删除该补充协议条款？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href = '${pageContext.request.contextPath}/contact/xiaoshoucontactsupplydelete.html?contactSupplyUuid=' + selectContactSupplyUuid + '&xiaoShouContactUuid=${xiaoShouContact.uuid}';
       });
    }

//    添加补充协议内容
    function openZuPinContactSupplyContent(contactSupplyUuid) {
        window.location.href = '${pageContext.request.contextPath}/contact/xiaoshoucontactsupplycontentform.html?contactSupplyUuid=' + contactSupplyUuid + '&xiaoShouContactUuid=${xiaoShouContact.uuid}';
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
