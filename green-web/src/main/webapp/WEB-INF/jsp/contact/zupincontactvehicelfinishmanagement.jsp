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

        <form action="${pageContext.request.contextPath}/contact/overzupincontactvehicle.html" id="zupincontactoverform" name="basic_validate" novalidate="novalidate" class="layui-form" method="post">
            <div class="layui-field-box">
                <input type="hidden" name="method" value="save"/>
                <input type="hidden" name="zuPinContactUuid" value="${zuPinContactUuid}"/>
                <input type="hidden" name="executeUuid" value="${executeUuid}"/>
                <input type="hidden" name="tiChePiCi" value="${tiChePiCi}"/>
                <input type="hidden" name="vehicleNum" value="${vehicleNum}"/>
                <c:if test="${fn:length(houFuFeeList)>0}">
                    <div class="no_pay pay_list" id="ypbill">
                        <div class="pay_item">
                            <span class="pl12 pay_title">合同应付账单</span>
                        </div>
                        <div id="ypdetail">
                            <c:forEach items="${houFuFeeList}" var="rentHistory">
                                <div class="pay_con" data-price="${rentHistory.feeTotalInshow}" data-id="${rentHistory.uuid}">
                                    <div class="pay_con_left">
                                        <div class="pay_con_left_right">
                                            <img src="${pageContext.request.contextPath}/build/images/default.png" width="100%" height="100%" />
                                        </div>
                                    </div>
                                    <div class="pay_con_right">
                                        <P class="pay_name">车牌号：${rentHistory.vehicleNumber}  &nbsp;&nbsp;  扣费类型：${rentHistory.zuPinContactRepayLocation.description}</P>
                                        <p class="pay_price">
                                            ￥<span class="font_fd6636 bold feeHistoryFee">${rentHistory.feeTotalInshow}</span>
                                        </p>
                                        <p class="pay_remark">
                                            <span class="pr12">备注：${rentHistory.comment}</span>
                                            <span class="pay_time"><img src="${pageContext.request.contextPath}/build/images/clock.png" width="12">&nbsp;录入时间：${rentHistory.happendDate}</span>
                                        </p>

                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>

                <div class="layui-form-item">
                    <label class="layui-form-label">结束日期 <i class="font_E60012">*</i></label>  <span id="finishDate_help" class="text-danger" style="display: none;color: red">提示:结束日期不能为空</span>
                    <div class="layui-input-block">
                        <input id="finishDate" name="finishDate" data-date-format="yyyy-mm-dd"  class="datepicker layui-input" readonly="true" value="${today}"/>
                    </div>
                </div>
                <br>

                计算总金额 合计：￥<span id="feetotal"></span>

            </div>
        </form>
        <div align="center">
            &nbsp;
            <input type="button" value="计算总额" 	class="layui-btn" onclick="calculateTotalFee()"/>
            <input type="button" value="提交" 	class="layui-btn" onclick="saveZuPinContactFinish()"/>
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
<script type="text/javascript">

    layui.config({
        base: '${pageContext.request.contextPath}/plugins/layui/modules/'
    });

    layui.use(['laypage', 'layer','laydate','form','icheck'], function() {
        form = layui.form
    });

    //单车时 修改 日期改变 页面中的款项
    layui.use(['laydate'],function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#finishDate'
            ,value: '${today}'
            ,max: '${jieshuMaxDay}'
            ,min: '${jieshuMinDay}'
            ,showBottom: false
            ,done: function(value, date, endDate){
                if(${zuPinContact.zuPinContactBaoYueType == 'B_BAOYUE'}){
                    var url = "${pageContext.request.contextPath}/contact/zupincontactvehiclefinishformdatechange.html"+"?zuPinContactUuid="+"${zuPinContact.uuid}"+"&executeUuid="+"${executeUuid}"+"&vehicleNum="+"${vehicleNum}";
                    contactOverFormFeeChange(value,url);
                }

                if(${zuPinContact.zuPinContactBaoYueType == 'B_RIJIE'}){
                    var url = "${pageContext.request.contextPath}/contact/zupincontactvehiclerijiefinishformdatechange.html"+"?zuPinContactUuid="+"${zuPinContact.uuid}"+"&executeUuid="+"${executeUuid}"+"&vehicleNum="+"${vehicleNum}";
                    contactOverFormFeeChange(value,url);
                }

            }
        });
    });


    //合同结束 根据日期的改变计算后付模式下 应该缴纳的欠款
    function contactOverFormFeeChange(finishDate,url){

        jQuery.ajax({
            type: "GET",
            url: url,
            data: { zuPinContactUuid: '${zuPinContact.uuid}',finishDate:finishDate},
            dataType: "json",
            success:  function (result) {
                var unPayBills = result.unCreateBills;

                var childens = '';
                for(var o in unPayBills){
                    var child =
                        '<div class="pay_con" data-price="'+unPayBills[o].feeTotalInshow+'" data-id="'+unPayBills[o].uuid+'">'+
                        '<div class="pay_con_left">'+
                        '<div class="pay_con_left_right">'+
                        '<img src="${pageContext.request.contextPath}/build/images/default.png" width="100%" height="100%" />'+
                        '</div>'+
                        '</div>'+
                        '<div class="pay_con_right">'+
                        '<P class="pay_name">车牌号：'+unPayBills[o].vehicleNumber +'&nbsp;&nbsp;'+ '扣费类型：'+unPayBills[o].zuPinRepayLocationInShow+'</P>'+
                        '<p class="pay_price">'+
                        '￥<span class="font_fd6636 bold feeHistoryFee">'+unPayBills[o].feeTotalInshow+'</span>'+
                        '</p>'+
                        '<p class="pay_remark">'+
                        '<span class="pr12">备注：'+unPayBills[o].comment+'</span>'+
                        '<span class="pay_time"><img src="${pageContext.request.contextPath}/build/images/clock.png" width="12">&nbsp;录入时间：'+unPayBills[o].happendDate+'</span>'+
                        '</p>'+
                        '</div>'+
                        '</div>'
                    childens = childens+child;
                }

                $("#ypdetail").html(childens)
                calculateTotalFee();
            }
        });
    }

    //计算合同结束 的总价
    function  calculateTotalFee() {
        //包含 合同后付欠费租金(元) [备注：应收取] 合同已收押金(元) [备注：应退回] 合同已收押金(元) [备注：应收取] 合同未付账单 其他费用

        //结束时 应付 租金


        // 退换押金
        var yajinOut = $("#yajinOut").val();
        if(yajinOut == undefined){
            yajinOut = 0;
        }
        // 收取押金
        var yajinIn = $("#yajinIn").val();
        if(yajinIn == undefined){
            yajinIn = 0;
        }
        // 未完成账单
        var feeHistoryTotal = 0;
        var feeHistoryFee = $(".feeHistoryFee").each(function(index,domEle){
            //相加
            feeHistoryTotal = feeHistoryTotal+parseInt($(this).text());
        })
        var otherFee = parseInt(0);
        $(".feerow").each(function(index,domEle){
            var row = $(this);
            var row_fee = row.find(".overformcount").val();
            row_fee = parseFloat(row_fee);
            var row_type = row.find(".overformcharge option:selected").attr("value");
            if(row_type=='ZHI_CHU'){
                otherFee = (otherFee*1-row_fee).toFixed(2);
            }else {
                otherFee = (otherFee*1+row_fee).toFixed(2);
            }

        })

        //计算四大项的总和
        var feeTotal = 0.00-parseFloat(yajinOut)+parseFloat(yajinIn)+parseFloat(feeHistoryTotal)+parseFloat(otherFee);

        $("#feetotal").text(feeTotal.toFixed(2));

        return feeTotal;
    }
    calculateTotalFee();

    function saveZuPinContactFinish() {
        var finishNote = jQuery("#finishNote").val();

        var feetotal = calculateTotalFee();
        var msg = '';
        if(feetotal>0){
            msg = "产生金额："+feetotal+"元的账单，并结束租赁"
        }else {
            msg = "产生金额："+feetotal+"元的账单，并结束租赁"
        }

        msg =msg+'结束租赁后，该车辆回到空置状态？'
        layer.confirm(msg, {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            if(${zuPinContact.zuPinContactBaoYueType == 'B_BAOYUE'}){
                $("#zupincontactoverform").submit();
            }

            if(${zuPinContact.zuPinContactBaoYueType == 'B_RIJIE'}){
                $("#zupincontactoverform").attr("action","${pageContext.request.contextPath}/contact/rijiedateform.html");
                $("#zupincontactoverform").submit();
            }
        });
    }


</script>
</body>
</html>

