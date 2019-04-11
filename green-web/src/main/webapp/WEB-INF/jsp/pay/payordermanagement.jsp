<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="yj" uri="http://www.rajaev.com" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>支付</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" media="all">
    <meta name="viewport" content="width=device-width,initial-scale=1, minimum-scale=1.0, maximum-scale=1, user-scalable=no, minimal-ui">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
<div class="layui-row pay_container">
    <yj:security grantRoles="ROLE_GREEN_CARRIER_RENT,ROLE_GREEN_FINANCE,ROLE_GREEN_ADMIN">
        <form class="layui-form">
            <div class="layui-form-item chegnyus">
                <label class="layui-form-label">承运商</label>
                <div class="layui-input-block">
                    <select name="selectCarrierUuid" lay-verify="required" lay-filter="selectCarrier">
                        <option value="-1">请选择</option>
                        <c:forEach items="${users}" var="user">
                            <option value="${user.uuid}" <c:if test="${user.uuid == selectCarrierUuid}">selected="true"</c:if>>${user.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </form>
    </yj:security>

    <c:if test="${notFinishOrder != null}">
        <%--<fieldset class="layui-elem-field">
            <legend><i class="layui-icon">&#xe658;</i>支付中订单</legend>--%>
            <div class="pay_list">
                <div class="pay_item">
                    <div class="" style="float: left;">
                        <input type="checkbox" class="allCheckbox" checked disabled/>
                    </div>
                    <span class="pl12 pay_title">订单编号：${notFinishOrder.orderNumber}  &nbsp;&nbsp;  甲方：${notFinishOrder.belongName}  &nbsp;&nbsp;  乙方：${notFinishOrder.chengYunName}</span>
                    <div style="float:right;">
                        <button class="layui-btn layui-btn-small layui-btn-danger" onclick="cancelOrder('${notFinishOrder.orderNumber}', '${selectCarrierUuid}');">取消订单</button>
                        <button class="layui-btn layui-btn-small" onclick="continueToPay('${notFinishOrder.orderNumber}');">立即支付</button>
                    </div>
                </div>
                <c:forEach items="${notFinishOrder.payItems}" var="rentHistory">
                    <div class="pay_con" data-price="${rentHistory.feeTotalInshow}" data-id="${rentHistory.uuid}">
                        <div class="pay_con_left">
                            <div class="pay_con_left_left">
                                <input type="checkbox" class="aloneCheckbox"  checked disabled/>
                            </div>
                            <div class="pay_con_left_right">
                                <img src="${pageContext.request.contextPath}/build/images/default.png" width="100%" height="100%" />
                            </div>
                        </div>
                        <div class="pay_con_right">
                            <P class="pay_name">车牌号：${rentHistory.vehicleNumber}  &nbsp;&nbsp;  扣费类型：${rentHistory.zuPinContactRepayLocation.description}</P>
                            <p class="pay_price">
                                <span class="font_fd6636 bold">￥${rentHistory.feeTotalInshow}</span>
                            </p>
                            <p class="pay_remark">
                                <span class="pr12">备注：${rentHistory.comment}</span>
                                <span class="pay_time"><img src="${pageContext.request.contextPath}/build/images/clock.png" width="12">&nbsp;创建时间：${rentHistory.happendDate}</span>
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        <%--</fieldset>--%>
     </c:if>


    <%--付款项--%>

    <c:forEach items="${willPayOrders}" var="willPayOrder">
        <%--<fieldset class="layui-elem-field">
            <legend><i class="layui-icon">&#xe658;</i>待支付订单</legend>--%>
            <c:if test="${fn:length(willPayOrder.histories)>0}">
            <div class="no_pay pay_list">
                <div class="pay_item">
                    <div class="" style="float: left;">
                        <input type="checkbox" class="allCheckbox" checked/>
                    </div>
                    <span class="pl12 pay_title">合同编号：${willPayOrder.contactNumber} &nbsp;&nbsp; 甲方：${willPayOrder.jiaFang}  &nbsp;&nbsp;  乙方：${willPayOrder.yiFang}</span>
                </div>
                    <c:forEach items="${willPayOrder.histories}" var="rentHistory">
                            <div class="pay_con" data-price="${rentHistory.feeTotalInshow}" data-id="${rentHistory.uuid}">
                                <div class="pay_con_left">
                                    <div class="pay_con_left_left">
                                        <input type="checkbox" class="aloneCheckbox" checked/>
                                    </div>
                                    <div class="pay_con_left_right">
                                        <img src="${pageContext.request.contextPath}/build/images/default.png" width="100%" height="100%" />
                                    </div>
                                </div>
                                <div class="pay_con_right">
                                    <P class="pay_name">车牌号：${rentHistory.vehicleNumber}  &nbsp;&nbsp;  扣费类型：${rentHistory.zuPinContactRepayLocation.description}</P>
                                    <p class="pay_price">
                                        <span class="font_fd6636 bold">￥${rentHistory.feeTotalInshow}</span>
                                    </p>
                                    <p class="pay_remark">
                                        <span class="pr12">备注：${rentHistory.comment}</span>
                                        <span class="pay_time"><img src="${pageContext.request.contextPath}/build/images/clock.png" width="12">&nbsp;创建时间：${rentHistory.happendDate}</span>
                                    </p>

                                </div>
                            </div>
                    </c:forEach>
            </div>
            </c:if>
         <%--</fieldset>--%>
    </c:forEach>
</div>

<c:if test="${notFinishOrder == null}">
<footer class="pay-footer">
    总量：${fn:length(willPayOrders)>0}
    <c:if test="${fn:length(willPayOrders)>0}">
        <form action="${pageContext.request.contextPath}/carrier/payorderfirststep.html" method="post">
        <input type="checkbox" id="selected-all" checked/><span class="pl12">全选</span>
            <input type="hidden" id="chengYunUuid" name="chengYunUuid" value="${selectCarrierUuid}"/>
            <input type="hidden" id="totalFee" name="totalFee" value="0"/>
            <input type="hidden" id="itemUuids" name="itemUuids" value=""/>
            <div class="settlement_msg">
                <span class="settlement">合计:<span class="font_E60012 bold" id="allPrice">￥0</span></span>
                <button class="layui-btn layui-btn-warm">去结算</button>
            </div>
        </form>
    </c:if>
</footer>
</c:if>

<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script type="text/javascript">
    layui.config({
        base: '${pageContext.request.contextPath}/plugins/layui/modules/'
    });

    //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
    layui.use([ 'icheck', 'form'], function() {
        var form = layui.form;
        var $ = layui.jquery;
        $('input').iCheck({
            checkboxClass: 'icheckbox_flat-green'
        });

        $('.allCheckbox').on('ifChecked ifUnchecked', function(event) {
            var checkboxs = $(this).parents(".pay_list").find(".aloneCheckbox");
            var checksAll = $("#selected-all");
            var checkAll = $(".allCheckbox");
            if(checkAll.filter(':checked').length == checkAll.length) {
                checksAll.prop('checked', true);
            } else {
                checksAll.prop('checked', false);
            }
            if(event.type == 'ifChecked') {
                checkboxs.iCheck('check');
            } else {
                checkboxs.iCheck('uncheck');
            }
            allPrice();
        });

        $(".aloneCheckbox").on('ifChanged', function(event) {
            var checkAll = $(this).parents(".pay_list").find(".allCheckbox");
            var checksAll = $("#selected-all");
            var checkboxs = $(this).parents(".pay_list").find(".aloneCheckbox");
            var checkboxsAll = $(".allCheckbox");
            if(checkboxs.filter(':checked').length == checkboxs.length) {
                checkAll.prop('checked', true);
            } else {
                checkAll.prop('checked', false);
            }
            if(checkboxsAll.filter(':checked').length == checkboxsAll.length) {
                checksAll.prop('checked', true);
            } else {
                checksAll.prop('checked', false);
            }
            checkAll.iCheck('update');
            checksAll.iCheck('update');
            allPrice();
            return false;
        });

        $("#selected-all").on('ifChecked ifUnchecked', function(event) {
            var checkboxs = $("input[type=checkbox]");
            if(event.type == 'ifChecked') {
                checkboxs.iCheck('check');
            } else {
                checkboxs.iCheck('uncheck');
            }
            allPrice();
            return false;
        });

        form.on('select(selectCarrier)', function(data){
            window.location.href = "${pageContext.request.contextPath}/carrier/payordermanagement.html?selectCarrierUuid="+data.value;
        });


        //计算价格
        function allPrice() {
            var totleprice = 0;
            var objArr = [];
            $(".no_pay .pay_con").each(function() {
                var aloneCheckbox = $(this).find(".aloneCheckbox:checked").length;
                if(aloneCheckbox > 0) {
                    totleprice += parseFloat($(this).data("price"));
                    objArr.push($(this).data("id"));
                }
            });
            $("#itemUuids").val(objArr.join(","));
            $("#allPrice").text("￥" + totleprice);
            $("#totalFee").val(totleprice);
        }

        allPrice();
    });

    function continueToPay(orderNumber) {
        window.location.href = "${pageContext.request.contextPath}/carrier/payorderfirststep.html?payOrderNumber=" + orderNumber;
    }

    function cancelOrder(payOrderNumber, selectCarrierUuid) {
        layer.confirm('请确认是否取消该订单？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href='${pageContext.request.contextPath}/carrier/payordercancel.html?payOrderNumber=' + payOrderNumber + '&selectCarrierUuid=' + selectCarrierUuid;
        });
    }

</script>

</body>
</html>
