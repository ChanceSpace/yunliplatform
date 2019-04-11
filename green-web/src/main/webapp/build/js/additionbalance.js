/**
 * Created by Administrator on 2017/12/11.
 */
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

function saveAdditionBalanceForm(url) {
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
        msg = "应退还："+(-feetotal)+"元"
    }

    msg =msg
    layer.confirm(msg, {
        btn : [ '确定', '取消' ]//按钮
    }, function(index) {
        layer.close(index);
        $("#zupincontactoverform").attr("action",url);
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