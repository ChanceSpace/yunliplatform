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
        <input type="hidden" name="xiaoShouContactUuid" value="${xiaoShouContactUuid}"/>

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
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label" for="saleNumber">购买数量<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input path="saleNumber" maxlength="3" class="layui-input" onkeyup="num(this)" onbeforepaste="num(this)"/>
                        <label class="error" style="color: red;"><spring-form:errors path="saleNumber"/></label>
                    </div>
                </div>


                <div class="layui-form-item">
                    <label class="layui-form-label">单价<i class="font_E60012">*</i><br/>(台)</label>
                    <div class="layui-input-block">
                        <spring-form:input id="xiaoshousalePrice" path="salePrice" maxlength="10" class="layui-input" onkeyup="num(this)" onbeforepaste="num(this)" />
                        <label class="error" style="color: red;"><spring-form:errors path="salePrice"/></label>
                    </div>
                </div>

                <div class="layui-form-item" id="dingJin" >
                    <label class="layui-form-label">定金<i class="font_E60012">*</i><br/>(台)</label>
                    <div class="layui-input-block">
                        <spring-form:input id="xiaoshoudingJin" path="dingJin" maxlength="10" class="layui-input" onkeyup="num(this)" onbeforepaste="num(this)" />
                        <label class="error" style="color: red;"><spring-form:errors path="dingJin"/></label>
                    </div>
                </div>

                <div class="layui-form-item" id="shouFu" style="display: none">
                    <label class="layui-form-label">首付扣除定金<i class="font_E60012">*</i><br/>(台) </label>
                    <div class="layui-input-block">
                        <spring-form:input path="shouFu" id="xiaoshoushouFu" maxlength="10" class="layui-input" onkeyup="num(this)" onbeforepaste="num(this)"/>
                        <label class="error" style="color: red;"><spring-form:errors path="shouFu"/></label>
                    </div>
                </div>

                <div class="layui-form-item" id="maxAnJieYear" style="display: none">
                    <label class="layui-form-label">最大贷款年限<i class="font_E60012">*</i><br/>(年)</label>
                    <div class="layui-input-block">
                        <spring-form:input path="maxAnJieYear" maxlength="10" class="layui-input" onkeyup="num(this)" onbeforepaste="num(this)"/>
                        <label class="error" style="color: red;"><spring-form:errors path="maxAnJieYear"/></label>
                    </div>
                </div>

                <div class="layui-form-item" id="weikuanAuto">
                    <label class="layui-form-label">尾款<i class="font_E60012">*</i><br/>(台)</label>
                    <div class="layui-input-block">
                        <input  class="layui-input" id="weikuanAutoResult" name="weiKuan" readonly="true" placeholder="无需填写保存后自动计算" value="${module.weiKuan}">
                        <label class="error" style="color: red;"><spring-form:errors path="maxAnJieYear"/></label>
                    </div>
                </div>

        </div>
    </fieldset>

    </spring-form:form>

    <div class="layui-form-item">
            <input type="button" value="返 回" 	class="layui-btn layui-btn-normal" onclick="window.location.href='${pageContext.request.contextPath}/contact/xiaoshoucontactform.html?xiaoShouContactUuid=${xiaoShouContactUuid}'"/>
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
        var type = '${xiaoshouType}';
        if(type == "XS_ANJIE"){
            jQuery('#shouFu').css('display','block');
            jQuery('#maxAnJieYear').css('display','block');
        }
        if(type == "XS_QK"){
            jQuery('#shouFu').css('display','none');
            jQuery('#maxAnJieYear').css('display','none');
        }
    });

    layui.use(['form'], function() {
        var form = layui.form;
        layer = layui.layer;

    function judgeSaveSuccessFul() {
        if(${infoSave}){
            layer.open({"content":"车型保存成功","time":2000});
        }
    }

    judgeSaveSuccessFul();
    
    function quanKuanClac(weiKuanObj) {
        var salePrice = jQuery("#xiaoshousalePrice").val();
        var dingJin = jQuery("#xiaoshoudingJin").val();
        var shouFu = jQuery("#xiaoshoushouFu").val();

        var   r   =    /^\\d+(\\.\\d+)?$/　　//非负浮点数（正浮点数   +   0）     ;
        var salePriceJudge = TestRgexp(r,salePrice);
        var dingJinJudge = TestRgexp(r,dingJin);
        var shouFuJudge = TestRgexp(r,shouFu);

        jQuery("#weikuanresult").val(salePrice-dingJin);
    }
    function anJieCalc(){
        var salePrice = jQuery("#xiaoshousalePrice").val();
        var dingJin = jQuery("#xiaoshoudingJin").val();
        var shouFu = jQuery("#xiaoshoushouFu").val();

        var   r   =    /^\\d+(\\.\\d+)?$/　　//非负浮点数（正浮点数   +   0）     ;
        var salePriceJudge = TestRgexp(r,salePrice);
        var dingJinJudge = TestRgexp(r,dingJin);
        var shouFuJudge = TestRgexp(r,shouFu);

        jQuery("#weikuanresult").val(salePrice-dingJin-shouFu);

    }
    function TestRgexp(re, s){  // 参数说明 re 为正则表达式   s 为要判断的字符
        return re.test(s)
    }
    });

    function saveContactSelectVehicleForm() {
        jQuery("#vehicle_module_form").submit();
    }

    function num(obj) {
        obj.value = obj.value.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
        obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字
        obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个, 清除多余的
        obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
    }
</script>
</body>
</html>
