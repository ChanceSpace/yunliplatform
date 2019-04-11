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
               <div class="scum_nav">当前位置：<a href="${pageContext.request.contextPath}/contact/zupincontactdetails.html?zuPinContactUuid=${zuPinContactUuid}" class="font_33a7fd">合同详情</a><img src="${pageContext.request.contextPath}/build/images/arrow_right.png" width="10">合同结束</div>
           </yj:security>
       </yj:security>
        <form action="${pageContext.request.contextPath}/contact/zupincontactfinishmanagement.html" id="zupincontactoverform" name="basic_validate" novalidate="novalidate" class="layui-form" method="post">
            <div class="layui-field-box">
                <input type="hidden" name="method" value="save"/>
                <input type="hidden" name="zuPinContactUuid" value="${zuPinContactUuid}"/>
                <div class="layui-form-item">
                    <c:if test="${zuPinContact.yajinHasPay > 0}">
                        <label class="layui-form-label">  合同已收押金(元) [备注：应退回]</label>
                        <div class="layui-input-block">
                            <input maxlength="10" class="layui-input" id="yajinOut" value="${zuPinContact.yajinHasPay}" style="color:#009E94;" readonly="true"/>
                        </div>
                    </c:if>
                    <c:if test="${zuPinContact.yajinHasPay < 0}">
                        <label class="layui-form-label">  合同已收押金(元) [备注：应收取]</label>
                        <div class="layui-input-block">
                            <input maxlength="10" id="yajinIn" class="layui-input" value="${-zuPinContact.yajinHasPay}" style="color:#FF0000;" readonly="true"/>
                        </div>
                    </c:if>
                </div>


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

                <c:if test="${fn:length(willPayOrders)>0}">
                    <div class="no_pay pay_list">
                        <div class="pay_item">
                            <span class="pl12 pay_title">合同未付账单</span>
                        </div>
                        <c:forEach items="${willPayOrders}" var="rentHistory">
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
                </c:if>



                <div class="layui-form-item">
                    <label class="layui-form-label"> 其他费用(元)</label><span id="qitaFeeError" class="text-danger" style="display: none;color: red"></span>
                    <div class="layui-input-block">
                        <input type="text" value="表单内容" class="layui-input" readonly="readonly" style="width:40%;display: inline-block;">
                        <i id="addContent" class=" layui-icon" style="font-size:22px;vertical-align: middle;cursor:pointer;color:#009F95;" onclick="addContentToFinishForm()">&#xe608;</i>
                    </div>
                </div>
                <div class="detailCon layui-form-item" style="display: none;">
                        <table class="layui-table" style="width:100%;">
                            <thead>
                            <tr>
                                <th width="8%">序号</th>
                                <th width="20%">金额(元)</th>
                                <th width="12%">支付方式</th>
                                <th>备注</th>
                                <th width="12%">操作</th>
                            </tr>
                            </thead>
                            <tbody class="tbody">
                            </tbody>
                        </table>
                </div>

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
                    var url = "${pageContext.request.contextPath}/contact/zupincontactfinishformdatechange.html"
                    contactOverFormFeeChange(value,url);
                }

                if(${zuPinContact.zuPinContactBaoYueType == 'B_RIJIE'}){
                    var url = "${pageContext.request.contextPath}/contact/zupincontactrijiefinishformdatechange.html"
                    contactOverFormFeeChange(value,url);
                }

            }
        });
    });



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
        var html = '<tr class="feerow"><td>'+thisIndex+'</td>'+
            '<td ><i class="font_E60012" style="position: absolute;right: 9px">*</i> <input placeholder="请输入金额" required class="layui-input overformcount" name="overformcount" /> </td>'+
            '<td>' +
            '<select id="finishCharge" name="finishCharge" class="overformcharge" >' +
            '<option value="SHOU_QU">收取</option>'+
            '<option value="ZHI_CHU">支付</option>'+
            '</select>' +
            '</td>'+
            '<td><input placeholder="请输入备注" name="comment" class="layui-input"/></td>'+
            '<td><a href="javascript:;" data-id="1" data-opt="del" class="deteleBtn layui-btn layui-btn-danger layui-btn-mini" onclick="addContentToFinishFormDelete(this)"><i class="layui-icon">&#xe640;</i>删除</a></td>'+
            "</tr>"
        $(".tbody").append(html);
    }

    function addContentToFinishFormDelete(obj){
        $(obj).parents("tr").remove();
        calculateTotalFee();
    }


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

        //其他费用
        if(checkOtherFeeForm()){
            layer.msg('其他费用项填写异常');
            return;
        }
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
        if(checkOtherFeeForm()){
            layer.msg('其他费用含有未填写项或填写非数字');
            return;
        }
        var feetotal = calculateTotalFee();
        var msg = '';
        if(feetotal>0){
            msg = "产生金额："+feetotal+"元的账单，并结束租赁"
        }else {
            msg = "产生金额："+feetotal+"元的账单，并结束租赁"
        }

        msg =msg+'结束租赁后，该合同所有车辆将不再自动产生账单？'
        layer.confirm(msg, {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            if(${zuPinContact.zuPinContactBaoYueType == 'B_BAOYUE'}){
                $("#zupincontactoverform").submit();
            }

            if(${zuPinContact.zuPinContactBaoYueType == 'B_RIJIE'}){
                $("#zupincontactoverform").attr("action","${pageContext.request.contextPath}/contact/zupincontactrijiefinishmanagement.html");
                $("#zupincontactoverform").submit();
            }

        });

    }


    function checkOtherFeeForm(){
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
                    $("#qitaFeeError").text("其他费用条款中含有空值重新输入");
                    jQuery("#qitaFeeError").css("display", "block");
                    return true;
                }
                if(!contarray[j].match(/^[1-9]{1}\d*(.\d{1,2})?$|^0.\d{1,2}$/)){
                    $("#qitaFeeError").text("其他费用条款中含有非法字符正数且最多两位小数");
                    jQuery("#qitaFeeError").css("display", "block");
                    return true;
                }
            }
        }
        return false;
    }
</script>
</body>
</html>
