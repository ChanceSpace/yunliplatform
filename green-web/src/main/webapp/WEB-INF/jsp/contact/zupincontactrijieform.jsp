<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/autocomplete/jquery.autocomplete.css"/>
    <style>
        .layui-timeline-title{font-size:24px;}
    </style>
</head>

<body>

<%--合同条款部分--%>
<div  style="margin:20px;min-width:950px;">

    <spring-form:form commandName="module" id="vehicle_module_form" name="basic_validate" novalidate="novalidate" class="layui-form" method="post">
        <spring-form:input type="hidden" path="uuid"/>
        <input type="hidden" name="zuPinContactUuid" value="${zuPinContactUuid}"/>

        <fieldset class="layui-elem-field">
            <legend><i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;车型信息</legend>
            <div class="layui-field-box">
                <div class="layui-form-item">
                    <label class="layui-form-label" for="vehicleModelUuid">车型<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <select name="moduleUuid">
                            <c:forEach items="${modules}" var="loop">
                                <option value="${loop.uuid}" <c:if test="${loop.uuid == module.moduleUuid}">selected="true"</c:if>>${loop.moduleName} [${loop.moduleType} - ${loop.moduleBrand} <c:if test="${loop.moduleDianLiang != null && loop.moduleDianLiang != ''}">(${loop.moduleDianLiang}度电)</c:if> - ${loop.moduleColor}]
                                </option>
                            </c:forEach>
                        </select>                                                                                      `
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label" for="rentNumber">租用数量<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input path="rentNumber" maxlength="3" class="layui-input" onkeyup="value=value.replace(/[^\d]/g,'')"/>
                        <label class="error" style="color: red;"><spring-form:errors path="rentNumber"/></label>
                    </div>
                </div>

        </div>
    </fieldset>

     <fieldset class="layui-elem-field">
        <legend><i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;租金信息</legend>
            <div class="layui-field-box">


                <div class="layui-form-item">
                    <label class="layui-form-label" for="actualRentFee">实际日租单价<i class="font_E60012">*</i>(台)</label>
                    <div class="layui-input-block">
                        <spring-form:input path="actualRentFee" maxlength="10" class="layui-input" onkeyup="num(this)" onbeforepaste="num(this)"/>
                        <label class="error" style="color: red;"><spring-form:errors path="actualRentFee"/></label>
                    </div>
                </div>
            </div>
        </fieldset>

    <fieldset class="layui-elem-field">
        <legend><i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;押金信息</legend>
            <div class="layui-field-box">

                <div class="layui-form-item">
                    <label class="layui-form-label">押金单价<i class="font_E60012">*</i><br/>(台)</label>
                    <div class="layui-input-block">
                        <spring-form:input path="singleYaJin" maxlength="10" class="layui-input" onkeyup="num(this)" onbeforepaste="num(this)" />
                        <label class="error" style="color: red;"><spring-form:errors path="singleYaJin"/></label>
                    </div>
                </div>

            </div>
        </fieldset>
    </spring-form:form>

    <div class="layui-form-item">
            <input type="button" value="返 回" 	class="layui-btn layui-btn-normal" onclick="window.location.href='${pageContext.request.contextPath}/contact/zupincontactform.html?zuPinContactUuid=${zuPinContactUuid}'"/>
            &nbsp;
            <input type="button" value="保 存" 	onclick="saveContactSelectVehicleForm();" class="layui-btn"/>
    </div>
</div>

<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/plugins/autocomplete/jquery.autocomplete.js"></script>
<script src="${pageContext.request.contextPath}/plugins/autocomplete/browser.js"></script>
<script src="${pageContext.request.contextPath}/plugins/popup/modal.popup.js" type="text/javascript"></script>

<script type="text/javascript">

    jQuery(document).ready(function () {
        var type = jQuery('#zuPinYaJinType').val();
        if(type == "Y_FENQI"){
            jQuery('#shouFu').css('display','block');
            jQuery('#fenQiShu').css('display','block');
            jQuery('#yueGong').css('display','block');
        }
        if(type == "Y_QUANKUAN"){
            jQuery('#fenQiShu').css('display','none');
            jQuery('#yueGong').css('display','none');
            jQuery('#shouFu').css('display','none');
        }
        var type = jQuery('#zuPinContactRepayType').val();
        if(type == "C_AFTER"){
            jQuery('#delayMonthShow').css('display','block');
            changeAfterMonthByRentMonth();
            var delayMonth = '${module.delayMonth}';
            $("#delayMonthSelect").val(delayMonth);
        }
        if(type == "C_BEFORE"){
            jQuery('#delayMonthShow').css('display','none');
        }
    });

    layui.use(['form'], function() {
        var form = layui.form;
        var data = layui.data;
        layer = layui.layer;

        form.on("select(yaJinFilter)", function(data) {
            var type = data.value;
                if(type == "Y_FENQI"){
                jQuery('#shouFu').css('display','block');
                jQuery('#fenQiShu').css('display','block');
                jQuery('#yueGong').css('display','block');
            }
            if(type == "Y_QUANKUAN"){
                jQuery('#shouFu').css('display','none');
                jQuery('#fenQiShu').css('display','none');
                jQuery('#yueGong').css('display','none');
            }
            form.render();
        });

        //选择缓付类型
        form.on("select(repayTypeFiler)", function(data) {
            var type = data.value;
            if(type == "C_AFTER"){
                jQuery('#delayMonthShow').css('display','block');
                changeAfterMonthByRentMonth();
                form.render();
            }
            if(type == "C_BEFORE"){
                jQuery('#delayMonthShow').css('display','none');
                jQuery('#delayMonthShow').empty();
                form.render();
            }

        });

        form.render();
    });
    
    
    function  changeAfterMonthByRentMonth() {
        var month = $("#rentMonth").val();
        var op = "";
        if(month<1){
            return;
        }
        for (var i=1; i<=month-1; i++)
        {
            op=op + "<option value=\""+i+"\">"+i+"月</option>" ;
        }
        jQuery('#delayMonthShow').append(
            "<label class=\"layui-form-label\" for=\"delayMonth\">延迟缴费月数(月)<i class=\"font_E60012\">*</i></label>"+"<span class=\"text-danger\"><spring-form\:errors path=\"delayMonth\"/></span>"
            +"<div class=\"layui-input-block\">"+
            "<select name=\"delayMonth\"  id=\"delayMonthSelect\">"+
            op+
            "</select>"+
            "</div>"
        )

    }
    

    function num(obj) {
        obj.value = obj.value.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
        obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字
        obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个, 清除多余的
        obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
    }

    function saveContactSelectVehicleForm() {
        jQuery("#vehicle_module_form").submit();
    }
</script>
</body>
</html>
