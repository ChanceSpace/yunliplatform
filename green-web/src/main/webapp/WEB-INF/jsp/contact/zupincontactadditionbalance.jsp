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

    <fieldset class="layui-elem-field">
        <legend><i class="layui-icon">&#xe63c;</i>合同结束 -> 合同编号:${zuPinContact.contactNumber}&nbsp;</legend>
        <form action="${pageContext.request.contextPath}/contact/zupincontactadditionbalance.html" id="zupincontactoverform" name="basic_validate" novalidate="novalidate" class="layui-form" method="post">
            <div class="layui-field-box">
                <input type="hidden" name="method" value="save"/>
                <input type="hidden" name="zuPinContactUuid" value="${zuPinContactUuid}"/>
                <input type="hidden" name="executeUuid" value="${executeUuid}"/>

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

                <br>

                计算总金额 合计：￥<span id="feetotal"></span>

            </div>
        </form>
        <div align="center">
            &nbsp;
            <input type="button" value="计算总额" 	class="layui-btn" onclick="calculateTotalFee()"/>
            <input type="button" value="提交" 	class="layui-btn" onclick="saveZuPinContactFinish()"/>
        </div>
    </fieldset>
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
            msg = "产生金额："+feetotal+"元的账单"
        }else {
            msg = "产生金额："+feetotal+"元的账单"
        }

        msg =msg;
        layer.confirm(msg, {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            $("#zupincontactoverform").submit();
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
