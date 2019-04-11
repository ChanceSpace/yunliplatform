<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="yj" uri="http://www.rajaev.com" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>绿色共享运力平台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/datepicker/datepicker3.css">
    <style>
        .layui-laydate-content td.layui-this{background:#009E94}
    </style>
</head>
<body>

<div class="admin-main">
    <fieldset class="fieldset_box">
    <blockquote class="layui-elem-quote">
        <form class="layui-form"  id="visit_search_form"  action="${pageContext.request.contextPath}/carrier/payordercarrieroverview.html" method="POST">
            <input type="hidden" name="init" value="true"/>

            <div class="layui-input-inline">
                <label class="layui-form-label search-form-label">支付方式</label>
                <div class="layui-input-inline">
                    <select name="selPayType">
                        <option value="ALL">全部</option>
                        <c:forEach items="${payTypes}" var="payType">
                            <option value="${payType.value}" <c:if test="${payType.value == paging.selPayType}">selected="true"</c:if>>${payType.label}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label search-form-label">交易时间范围</label>
                    <div class="layui-input-inline">
                      <input type="text" name="startTime" class="layui-input" id="datepicker1" value="${paging.startTime}"/>
                    </div>
                </div>

                <div class="layui-input-inline">
                    <label>—</label>
                    <div class="layui-input-inline">
                        <input type="text" name="endTime" class="layui-input" id="datepicker2" value="${paging.endTime}"/>
                    </div>
                </div>

                <div class="layui-input-inline">
                    <label class="layui-form-label search-form-label">关键字</label>
                    <div class="layui-input-inline">
                        <input type="text" id="keyWords" name="keyWords" class="layui-input" value="${paging.keyWords}" placeholder="承运商名称或订单号" style="width: 300px;">
                    </div>
                </div>
                <div class="layui-input-inline">
                    <a href="javascript:void(0);" onclick="searchPayOrderInfo();" class="layui-btn layui-btn-small" id="search">
                        <i class="layui-icon">&#xe615;</i> 搜索
                    </a>
                </div>


        </form>
    </blockquote>
    <%--TITLE部分--%>
        <fieldset class="layui-elem-field">
            <p style="margin:10px 15px 0;">
                <c:if test="${company.userType == 'U_CARRIER_RENTING'}">
                    <c:if test="${company.currentBalance >= 0 }">
                        合计流水：<i style="font-size:20px;color:#ff6d15">${company.currentBalance}</i>&nbsp;&nbsp;
                    </c:if>
                    <c:if test="${company.currentBalance < 0 }">
                        合计流水：<i style="font-size:18px;color:#ff6d15">${company.currentBalance}</i>&nbsp;&nbsp;
                    </c:if>
                    当前交易时间范围(收入: <i style="color:#ff6d15 ">${totalFeeBelongIn}</i>&nbsp;
                    支出: <i style="color:#ff6d15 ">${totalFeeBelongOut}</i>) &nbsp;
                </c:if>
                <c:if test="${company.userType == 'U_CARRIER'}">
                    <c:if test="${company.currentBalance >= 0 }">
                        账户余额：<i style="font-size:20px;color:#ff6d15">${company.currentBalance}</i>&nbsp;&nbsp;
                    </c:if>
                    <c:if test="${company.currentBalance < 0 }">
                        账户余额：<i style="font-size:18px;color:#ff6d15">${company.currentBalance}</i>&nbsp;&nbsp;
                    </c:if>
                    当前交易时间范围(收入: <i style="color:#ff6d15 " >${totalFeelChengYunIn}</i>&nbsp;
                    支出: <i style="color:#ff6d15 ">${totalFeelChengYunOut}</i>) &nbsp;&nbsp;
                </c:if>
            </p>

        <legend>交易记录</legend>
        <div class="layui-field-box" style="padding-bottom:100px">
            <table class="site-table table-hover">
                <thead>
                    <th width="5%">序号</th>
                    <th class="th_carNo">订单号</th>
                    <th class="th_time" >交易时间</th>
                    <th class="th_carNo">订单来源</th>
                    <th class="th_name">付款方</th>
                    <th class="th_name">金额</th>
                    <th  class="th_status">状态</th>
                    <th  class="th_status">支付方式</th>
                    <th class="th_time">操作</th>
                </thead>
                <tbody id="strtegyTable">
                <c:forEach items="${orders}" var="line" varStatus="counter">
                    <tr class="gradeX">
                        <td>${counter.count}</td>
                        <td>${line.orderNumberShow}</td>
                        <td> ${line.finishTime}</td>
                        <td>${line.payOrderSourceName}</td>
                        <td>${line.chengYunName}</td>

                        <c:if test="${company.userType == 'U_CARRIER_RENTING'}">
                            <c:if test="${line.totalFeeBelone >= 0}">
                                <td><font color="green">${line.totalFeeBelone}</font> </td>
                            </c:if>
                            <c:if test="${line.totalFeeBelone < 0}">
                                <td><font color="red">${line.totalFeeBelone}</font> </td>
                            </c:if>
                        </c:if>

                        <c:if test="${company.userType == 'U_CARRIER'}">
                            <c:if test="${line.totalFeeChengYun >= 0}">
                                <td><font color="green">${line.totalFeeChengYun}</font> </td>
                            </c:if>
                            <c:if test="${line.totalFeeChengYun < 0}">
                                <td><font color="red">${line.totalFeeChengYun}</font> </td>
                            </c:if>
                        </c:if>

                        <td>
                            <c:if test="${line.payOrderStatus == 'O_CREATE'}">
                                <font color="red"> ${line.payOrderStatusName} </font>
                            </c:if>
                            <c:if test="${line.payOrderStatus == 'O_FINSIH'}">
                                ${line.payOrderStatusName}
                            </c:if>
                        </td>
                        <td>
                             ${line.payOrderWayName}
                        </td>
                        <td><a href="${pageContext.request.contextPath}/carrier/payorderdetail.html?payOrderNumber=${line.orderNumber}" class="layui-btn layui-btn-mini layui-btn-normal" style="margin-right:6px;vertical-align:middle;"><i class="layui-icon">&#xe63c;</i>查看详情</a></td>
                </c:forEach>
                </tbody>
            </table>


        </div>
            <div class="admin-table-page">
                <div id="page" class="page">
                    <yj:paging urlMapping="${pageContext.request.contextPath}/carrier/payordercarrieroverview.html" paging="${paging}"/>
                </div>
            </div>
    </fieldset>
    </fieldset>
</div>

<!-- jQuery 2.2.0 -->
<script src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/popup/modal.popup.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${pageContext.request.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>


<script type="text/javascript">

    var form;
    layui.config({
        base: '${pageContext.request.contextPath}/plugins/layui/modules/'
    });
    layui.use(['laypage', 'layer','laydate','form'], function() {
        form = layui.form
    });
    layui.use(['form'], function() {
        var form = layui.form,
        layer = layui.layer;
    });

    layui.use(['laydate'],function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#datepicker1'
            ,value: '${paging.startTime}'
        });
        laydate.render({
            elem: '#datepicker2'
            ,value: '${paging.endTime}'
        });
    });

    function searchPayOrderInfo() {
        jQuery('#visit_search_form').submit();
    }

    jQuery(document).on('keydown', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
            searchPayOrderInfo();
        }
    });

</script>

</body>
</html>

