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

<div class="payStyle_container">
    <p class="title">绿色共享运力平台收银台</p>

    <div class="nav_box">
        <div class="nav_left">
            <img src="${pageContext.request.contextPath}/build/images/waiting.gif" width="80" />
        </div>
        <div class="nav_right">
            <p>交易进行中......</p>
        </div>
    </div>

    <c:if test="${!applicationPaymentLive}">
        <div class="pay_con">
            <div class="pay_btn">
                <button class="layui-btn layui-btn-normal layui-btn-big" onclick="testOnlinePayOrder();">测试环境支付成功</button>
            </div>
        </div>
    </c:if>
</div>

<!-- jQuery 2.2.0 -->
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script type="text/javascript">
    layui.config({
        base: '${pageContext.request.contextPath}/plugins/layui/modules/'
    });

    /****************************************真实付款部分*******************************************/

    var payOrderNumber = '${payOrder.payOrderNumber}';

    $(document).ready(function() {
        setInterval("updatePaymentStatus()", 4000);
        setInterval("finishWaiting()", 53000);
    });

    function updatePaymentStatus() {
         $.ajax({
            type : "POST",
            url : '${pageContext.request.contextPath}/carrier/xiaoshoupaymentonlinesecond.html?payOrderNumber=${payOrderNumber}',
            data:{payOrderNumber:payOrderNumber},
            dataType : "json",
            success : function(result) {
                //完成，未完成，等待
                var flag = result.FLAG;
                if('YES' == flag) {
                    var totalFee = result.totalFee;
                    var jiaoYiNumber = result.jiaoYiNumber;
                    window.location.href = '${pageContext.request.contextPath}/carrier/xiaoshoupaymentfinish.html?payOrderNumber=${payOrderNumber}&jiaoYiNumber=' + jiaoYiNumber + '&totalFee=' + totalFee;
                } if('NO' == flag) {
                    window.location.href = '${pageContext.request.contextPath}/carrier/xiaoshoupaymenterror.html';
                }
            }
        });

    }

    function finishWaiting() {
        window.location.href = '${pageContext.request.contextPath}/carrier/xiaoshoupaymentonlinefinishwaiting.html?payOrderNumber=${payOrderNumber}';
    }

    /*****************************************测试部分**********************************************/

    function testOnlinePayOrder() {
        window.location.href = '${pageContext.request.contextPath}/carrier/xiaoshoupaymentonlinesecondfake.html?payOrderNumber=${payOrderNumber}&jiaoYiNumber=TESTTESTTEST';
    }
</script>

</html>