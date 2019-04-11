<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/datepicker/datepicker3.css">
</head>
<body>

<style>
    .detailCon tr th,.detailCon tr td{text-align: center;}
    .detailCon tr td input{width:100%}
    #finishCharge {display: block;
        border: 1px solid #ccc;
        border-radius: 4px;
        padding: 4px 10px;}

</style>
<div>
    <yj:security grantRoles="ROLE_GREEN_CARRIER">
        <yj:security grantRoles="ROLE_GREEN_CARRIER">
            <div class="scum_nav">当前位置：<a href="${pageContext.request.contextPath}/contact/zupincontactdetails.html?zuPinContactUuid=${zuPinContactUuid}" class="font_33a7fd">合同详情</a><img src="${pageContext.request.contextPath}/build/images/arrow_right.png" width="10">车辆其他款项</div>
        </yj:security>
    </yj:security>


    <form action="" id="zupincontactoverform" name="basic_validate" novalidate="novalidate" class="layui-form" method="post">
        <div class="layui-field-box">
            <input type="hidden" name="method" value="save"/>
            <input type="hidden" name="zuPinContactUuid" value="${zuPinContactUuid}"/>
            <input type="hidden" name="executeUuid" value="${executeUuid}"/>

            <div class="layui-form-item">
                <label class="layui-form-label">车牌号</label>
                <div class="layui-input-block">
                    <input  class="layui-input" readonly="true" value="${vehicleNum}"/>
                </div>
            </div>

            <%@include file="additionbalance.jsp"%>
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;合计：￥<span id="feetotal"></span>
        </div>
    </form>
    <div align="center">
        &nbsp;
        <input type="button" value="计算总额" 	class="layui-btn" onclick="calculateTotalFee()"/>
        <input type="button" value="提交" 	class="layui-btn" onclick="saveAdditionBalanceForm('${pageContext.request.contextPath}/${url}')"/>
    </div>

</div>

<!-- jQuery 2.2.0 -->
<script src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/popup/modal.popup.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/plugins/autocomplete/jquery.autocomplete.js"></script>
<script src="${pageContext.request.contextPath}/plugins/autocomplete/browser.js"></script>
<script src="${pageContext.request.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${pageContext.request.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<%--导入页面费用计算js--%>
<script src="${pageContext.request.contextPath}/build/js/additionbalance.js"></script>
</body>
</html>
