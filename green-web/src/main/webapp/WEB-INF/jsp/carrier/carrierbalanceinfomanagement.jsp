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
    <style>
        .layui-laydate-content td.layui-this{background:#009E94}
    </style>
</head>
<body>
<div class="admin-main">
    <fieldset class="fieldset_box min_fieldset">
    <blockquote class="layui-elem-quote">
        <form class="layui-form" id="visit_search_form" action="${pageContext.request.contextPath}/carrier/carrierbalanceinfomanagement.html" method="POST" >
            <input type="hidden" name="carrierList" value="${carrierList}"/>

             <div class="layui-input-inline">
                <label class="layui-form-label search-form-label">欠费金额</label>
                <div class="layui-input-inline">
                    <select id="qianFeiTotal" name="qianFeiTotal" >
                        <option value="">请选择</option>
                        <option value="5000" <c:if test="${5000 == paging.qianFeiTotal}">selected="true"</c:if>>大于5000</option>
                        <option value="10000" <c:if test="${10000 == paging.qianFeiTotal}">selected="true"</c:if>>大于10000</option>
                        <option value="50000" <c:if test="${50000 == paging.qianFeiTotal}">selected="true"</c:if>>大于50000</option>
                        <option value="100000" <c:if test="${100000 == paging.qianFeiTotal}">selected="true"</c:if>>大于100000</option>
                    </select>
                </div>
            </div>


            <div class="layui-input-inline">
                <label class="layui-form-label search-form-label">欠费时间</label>
                <div class="layui-input-inline">
                    <select id="qianFeiDate" name="qianFeiDate" >
                        <option value="" >请选择</option>
                        <option value="5" <c:if test="${5 == paging.qianFeiDate}">selected="true"</c:if>>大于5天</option>
                        <option value="10" <c:if test="${10 == paging.qianFeiDate}">selected="true"</c:if>>大于10天</option>
                        <option value="15" <c:if test="${15 == paging.qianFeiDate}">selected="true"</c:if>>大于15天</option>
                        <option value="30" <c:if test="${30 == paging.qianFeiDate}">selected="true"</c:if>>大于30天</option>
                    </select>
                </div>
            </div>
            <div class="layui-input-inline">
                 <label class="layui-form-label search-form-label">关键字</label>
                 <div class="layui-input-inline">
                  <input type="text" id="keywords" name="keywords" class="layui-input" value="${paging.keywords}" placeholder="用户名称或者证件号" style="width: 200px;"/>
                </div>
            </div>
            <div class="layui-input-inline">
                <a href="javascript:void(0);" onclick="searchUserInfo();" class="layui-btn layui-btn-small" id="search">
                    <i class="layui-icon">&#xe615;</i> 搜索
                </a>
            </div>
        </form>

    </blockquote>

    <fieldset class="layui-elem-field min_fieldset">
        <legend>数据列表</legend>
        <div class="layui-field-box" style="padding-bottom:100px">
            <table class="site-table table-hover">
                <thead>
                    <tr>
                        <th width="4%">序号</th>
                        <th width="10%" class="th_time">注册时间</th>
                        <th width="12%" class="th_name">名称</th>
                        <th width="12%" class="th_com">证件号码</th>
                        <th width="10%" class="th_com">用户类型</th>
                        <th width="10%" class="th_com">用户性质</th>
                        <th width="5%" class="th_com">账户余额</th>
                        <th width="5%" class="th_com">欠费开始时间</th>
                        <th width="5%" class="th_com">欠费余额</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody id="strtegyTable">
                    <c:forEach items="${users}" var="line" varStatus="counter">
                        <tr class="gradeX">
                            <td>${counter.count}</td>
                            <td>${line.registerTime}</td>
                            <td>
                                ${line.name}
                            </td>
                            <td>${line.num}</td>
                            <td>${line.userTypeName}</td>
                            <td>${line.serviceTypeName}</td>
                            <td>
                                <c:if test="${line.currentBalance >= 0 }">
                                    <font color="green">${line.currentBalance}</font>
                                </c:if>
                                <c:if test="${line.currentBalance < 0 }">
                                    <font color="red">${line.currentBalance}</font>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${line.qianFeiBeginTime!=null && line.qianFeiBeginTime!='' && line.qianFeiBalance > 0}">
                                    <font color="red">${line.qianFeiBeginTime}</font>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${line.qianFeiBeginTime!=null && line.qianFeiBeginTime!='' && line.qianFeiBalance > 0}">
                                    <font color="red">${line.qianFeiBalance}</font>
                                </c:if>
                            </td>
                            <td class="center">
                                <a href="javascript:void(0);" onclick="addCarrierBalancePopUp('${line.uuid }')" class="layui-btn layui-btn-mini layui-btn-danger"><i class="layui-icon">&#xe62a;</i>线下充值</a>
                                <a href="javascript:void(0);" onclick="openTiXianPopUp('${line.uuid}','${line.currentBalance}')" class="layui-btn layui-btn-mini layui-btn-warm"><i class="layui-icon">&#xe62a;</i>线下提现</a>
                                <a href="javascript:void(0);" onclick="openCarrierChongZhiLog('${line.uuid}','${carrierList}')" class="layui-btn layui-btn-mini layui-btn-normal"><i class="layui-icon">&#xe62a;</i>账户详情</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="admin-table-page">
                <div id="page" class="page">
                    <yj:paging urlMapping="${pageContext.request.contextPath}/carrier/carrierbalanceinfomanagement.html" paging="${paging}"/>
                </div>
            </div>
        </div>

    </fieldset>
    </fieldset>
</div>

<script src="${pageContext.request.contextPath}/build/js/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jQuery/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/plugins/popup/modal.popup.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/build/js/pictureuploadvalidate.js"></script>
<script type="text/javascript">
    var form;
    var chongZhiMessage = ${chongZhiMessage};
    var tiXianMessage = ${tiXianMessage};
    layui.config({
        base: '${pageContext.request.contextPath}/plugins/layui/modules/'
    });
    layui.use(['laypage', 'layer','form'], function() {
        form = layui.form
    });
    layui.use(['form'], function() {
        var form = layui.form,
            layer = layui.layer;
        if(chongZhiMessage) {
            layer.open({"content":"充值操作成功","time":2000});
        }
        if(tiXianMessage) {
            layer.open({"content":"提现操作成功","time":2000});
        }
    });
    //弹出框部分
    jQuery(function() {
        settings = {
            align : 'center',									//Valid values, left, right, center
            top : 50, 											//Use an integer (in pixels)
            width : 800,
            height :600,                                        //Use an integer (in pixels)
            padding : 10,										//Use an integer (in pixels)
            backgroundColor : 'white', 						    //Use any hex code
            source : '', 				    					//Refer to any page on your server, external pages are not valid e.g. http://www.google.co.uk
            borderColor : '#333333', 							//Use any hex code
            borderWeight : 4,									//Use an integer (in pixels)
            borderRadius : 5, 									//Use an integer (in pixels)
            fadeOutTime : 300, 									//Use any integer, 0 : no fade
            disableColor : '#666666', 							//Use any hex code
            disableOpacity : 40, 								//Valid range 0-100
            loadingImage : '${pageContext.request.contextPath}/plugins/popup/loading.gif'
        };
    });

    function openModalPopup(obj) {
		modalPopup(obj.align, obj.top, obj.width, obj.padding, obj.disableColor, obj.disableOpacity, obj.backgroundColor, obj.borderColor, obj.borderWeight, obj.borderRadius, obj.fadeOutTime, obj.source, obj.loadingImage);
	}

    //用户操作部分
    function searchUserInfo() {
        jQuery('#visit_search_form').submit();
    }

    jQuery(document).on('keydown', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
            searchUserInfo();
        }
    });

    //账户余额详情
    function openCarrierChongZhiLog(userUuid,carrierList) {
        window.location.href ='${pageContext.request.contextPath}/carrier/carrierbalanceoverview.html?userUuid=' + userUuid + "&carrierList=" + carrierList;
    }
    //线下提现
    function openTiXianPopUp(userUuid,currentBalance) {
        settings.source = '${pageContext.request.contextPath}/carrier/carrierbalancetixian.html?userUuid=' + userUuid + '&currentBalance=' + currentBalance;
        openModalPopup(settings);
    }
    //校验表单
    function saveCarrierBalanceTiXianConfirm() {
        var feeTotal = jQuery("#feeTotal").val();
        var currentBalance = jQuery("#currentBalance").val();
        if (feeTotal ==null || feeTotal==''||isNaN(feeTotal)) {
            jQuery("#feeTotal_help").css("display","block");
            return;
        } else {
            jQuery("#feeTotal_help").css("display","none");
        }
        if (parseFloat(currentBalance) < parseFloat(feeTotal)){
            jQuery("#feeTotal_help2").css("display","none");
            jQuery("#feeTotal_help1").css("display","block");
            return;
        } else {
            jQuery("#feeTotal_help1").css("display","none");
        }
        if (parseFloat(feeTotal) == 0){
            jQuery("#feeTotal_help1").css("display","none");
            jQuery("#feeTotal_help2").css("display","block");
            return;
        } else {
            jQuery("#feeTotal_help2").css("display","none");
        }
        //今天
        var today = jQuery("input[name='today']").val();
        //充值时间
        var happendDate = jQuery("#happendDate").val();
        if ( today < happendDate ) {
            jQuery("#happendDate_help").css("display","block");
            return;
        } else {
            jQuery("#happendDate_help").css("display","none");
        }
        //充值时附件不能为空
        var fuJians = jQuery("#picture");
        if (jQuery.trim(fuJians.val()) == '') {
            jQuery("#fuJians_help").css("display", "block");
            return;
        } else {
            jQuery("#fuJians_help").css("display","none");
        }
        //交易单号不能为空
        var jiaoYiNumber = jQuery("#jiaoYiNumber").val();
        if (jiaoYiNumber ==null || jiaoYiNumber==''){
            jQuery("#jiaoYiNumber_help").css("display","block");
            return;
        } else {
            jQuery("#jiaoYiNumber_help").css("display","none");
        }
        var message = '请确认是否进行余额提现？'
        layer.confirm(message, {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            //提交提现申请之前先进行图片大小验证
            var contactFile = jQuery("#picture");
            var canbeLoad = validatepicturesize(contactFile,1024*1024);
            if(canbeLoad == true){
                layer.close(index);
                jQuery("#carrierBalanceForm").submit();
            }else {
                layer.msg("图片文件超过了1M，请压缩后再上传");
            }
        });
    }
    //线下充值
    function addCarrierBalancePopUp(userUuid) {
        settings.source = '${pageContext.request.contextPath}/carrier/carrierbalancechongzhi.html?userUuid=' + userUuid;
        openModalPopup(settings);
    }
    //校验表单
    function saveCarrierBalanceConfirm() {
        var feeTotal = jQuery("#feeTotal").val();
        if (feeTotal ==null || feeTotal==''||isNaN(feeTotal)) {
            jQuery("#feeTotal_help").css("display","block");
            return;
        } else {
            jQuery("#feeTotal_help").css("display","none");
        }
        //今天
        var today = jQuery("input[name='today']").val();
        //充值时间
        var happendDate = jQuery("#happendDate").val();
        if ( today < happendDate ) {
            jQuery("#happendDate_help").css("display","block");
            return;
        } else {
            jQuery("#happendDate_help").css("display","none");
        }
        //充值时附件不能为空
        var fuJians = jQuery("#picture");
        if (jQuery.trim(fuJians.val()) == '') {
            jQuery("#fuJians_help").css("display", "block");
            return;
        } else {
            jQuery("#fuJians_help").css("display","none");
        }
        //交易单号不能为空
        var jiaoYiNumber = jQuery("#jiaoYiNumber").val();
        if (jiaoYiNumber ==null || jiaoYiNumber==''){
            jQuery("#jiaoYiNumber_help").css("display","block");
            return;
        } else {
            jQuery("#jiaoYiNumber_help").css("display","none");
        }
        var message = '请确认是否进行余额充值？'
        layer.confirm(message, {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {

            //提交提现申请之前先进行图片大小验证
            var contactFile = jQuery("#picture");
            var canbeLoad = validatepicturesize(contactFile,1024*1024);
            if(canbeLoad == true){
                layer.close(index);
                jQuery("#carrierBalanceForm").submit();
            }else {
                layer.msg("图片文件超过了1M，请压缩后再上传");
            }

        });
    }
    function filePreviewPic(obj,files) {
        if(files){
            $("#previewPic").text("共"+files.length+"个文件");
        }else{
            $("#previewPic").text("未选择任何文件");
        }
    }
    //校验充值金额
    function num(obj){
        obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
        obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
        obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
    }
</script>
</body>
</html>
