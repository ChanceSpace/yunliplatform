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
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/autocomplete/jquery.autocomplete.css" />
</head>
<body>
<fieldset class="fieldset_bo min_fieldset">
<div class="admin-main">

    <%--报表内容部分    --%>

    <blockquote class="layui-elem-quote">
        <form class="layui-form" id="visit_search_form" action="${pageContext.request.contextPath}/carrier/orderinfomanagement.html" method="POST">
            <div class="layui-input-inline">
                <label class="layui-form-label search-form-label">工单类型</label>
                <div class="layui-input-inline">
                    <select name="selStatus">
                        <option value="ALL">全部</option>
                        <c:forEach items="${types}" var="types">
                            <option value="${types.value}" <c:if test="${types.value == paging.selStatus}">selected="true"</c:if>>${types.label}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-input-inline">
                <label class="layui-form-label search-form-label">状态</label>
                <div class="layui-input-inline">
                    <select name="selType">
                        <option value="ALL">全部</option>
                        <c:forEach items="${status}" var="types">
                            <option value="${types.value}" <c:if test="${types.value == paging.selType}">selected="true"</c:if>>${types.label}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label search-form-label" for="keywords">关键字</label>
                    <div class="layui-input-inline">
                        <input type="text" id="keywords" name="keywords" class="layui-input" value="${paging.keywords}" placeholder="车牌号或订单号" maxlength="30">
                    </div>
                </div>
            <div class="layui-input-inline">
                <a href="javascript:void(0);" onclick="searchOrderInfo();" class="layui-btn layui-btn-small layui-btn-normal">
                    <i class="layui-icon">&#xe615;</i> 查询
                </a>
                <a href="javascript:void(0);" onclick="addCarrierOrder('');" class="layui-btn layui-btn-small" style="float:right;">
                    <i class="layui-icon">&#xe608;</i> 新建工单
                </a>
            </div>
        </form>
    </blockquote>

    <fieldset class="layui-elem-field min_fieldset">
        <legend>数据列表</legend>
        <div class="layui-field-box">
                <table class="site-table table-hover table_box">
                    <thead>
                    <tr>
                        <th width="5%">序号</th>
                        <th width="10%" class="th_com">订单号</th>
                        <th width="10%" class="th_time">创建时间</th>
                        <th width="10%" class="th_com" >车牌号</th>
                        <th width="15%" class="th_sale">客户</th>
                        <th width="15%" class="th_com">联系方式</th>
                        <th width="10%" class="th_com">服务类型</th>
                        <th width="10%" class="th_com">状态</th>
                        <th class="th_com">操作</th>
                    </tr>
                    </thead>
                    <tbody id="strtegyTable">
                        <c:forEach items="${orders}" var="line" varStatus="counter">
                            <tr class="gradeX">
                                <td>${counter.count}</td>
                                <td>${line.orderNumber}</td>
                                <td>${line.createTime}</td>
                                <td>${line.vehicleNum}</td>
                                <td>${line.carrierName}</td>
                                <td>${line.contactName} [${line.contactPhone}]</td>
                                <td>${line.orderTypeName}</td>
                                <td>
                                    <c:if test="${line.orderStatus == 'M_CREATED'}">
                                        <span class="bg_999999"> ${line.orderStatusName} </span>
                                    </c:if>
                                    <c:if test="${line.orderStatus == 'M_PROCESS'}">
                                        <span class="bg_009E94"> ${line.orderStatusName} </span>
                                    </c:if>
                                    <c:if test="${line.orderStatus == 'M_FINISHED'}">
                                        <span class="bg_33a7fd"> ${line.orderStatusName} </span>
                                    </c:if>
                                </td>
                                <td class="center">
                                    <c:if test="${line.orderStatus == 'M_CREATED'}">
                                        <a href="javascript:void(0);" onclick="deleteOrderConfirm('${line.uuid}')" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini"><i class="layui-icon">&#xe640;</i>删除</a>
                                        <a href="#" onclick="addCarrierOrder('${line.uuid}')" class="layui-btn layui-btn-mini layui-btn-normal"><img src="${pageContext.request.contextPath}/build/images/icon2.png" width="16px" style="margin-right:6px;vertical-align:middle;"/>编辑</a>
                                    </c:if>
                                    <a href="${pageContext.request.contextPath}/service/orderdetails.html?orderUuid=${line.uuid}&keywords=${paging.keywords}&current=${paging.currentPageNumber}&isCarrier=true" class="layui-btn layui-btn-mini layui-btn-normal"><img src="${pageContext.request.contextPath}/build/images/icon2.png" width="16px" style="margin-right:6px;vertical-align:middle;"/>查看</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            <%--分页处理--%>
            <div class="admin-table-page">
                    <div id="page" class="page">
                            <yj:paging urlMapping="${pageContext.request.contextPath}/carrier/orderinfomanagement.html" paging="${paging}"/>
                    </div>
            </div>
            </div>
    </fieldset>
</div>
</fieldset>
<script src="${pageContext.request.contextPath}/build/js/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/popup/modal.popup.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/plugins/autocomplete/jquery.autocomplete.js"></script>
<script src="${pageContext.request.contextPath}/plugins/autocomplete/browser.js"></script>
<script src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>

<script type="text/javascript">
    var form;
    layui.config({
        base: '${pageContext.request.contextPath}/plugins/layui/modules/'
    });
    layui.use(['icheck', 'laypage', 'layer','form'], function() {
        form = layui.form
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

    function searchOrderInfo() {
        jQuery('#visit_search_form').submit();
    }

    function addCarrierOrder(orderUuid) {
        settings.source = '${pageContext.request.contextPath}/carrier/orderinfo.html?orderUuid=' + orderUuid + '&keywords=${paging.keywords}&current=${paging.currentPageNumber}';
        openModalPopup(settings);
    }
    //提交工单
    function orderStart(orderUuid) {
        layer.confirm('请确认是否提交该订单信息？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href='${pageContext.request.contextPath}/service/orderstart.html?orderUuid=' + orderUuid + '&keywords=${paging.keywords}&current=${paging.currentPageNumber}';
        });
    }

    function saveCarrierOrder() {
        var validate = true;
        var contactName = jQuery("#contactName").val();
        var contactPhone = jQuery("#contactPhone").val();
        var vehicleNum = jQuery("#targetNumber").val();
        if (contactName == null || contactName =='') {
            jQuery("#contactName_help").css("display","block");
            validate = false;
        } else {
            jQuery("#contactName_help").css("display","none");
        }
        if (contactPhone == null || contactPhone =='') {
            jQuery("#contactPhone_help").css("display","block");
            validate = false;
        } else {
            var contactPhoneOK = checkMobile(contactPhone);
            if (contactPhoneOK) {
                jQuery("#contactPhone_help").css("display","none");
            } else {
                jQuery("#contactPhone_help").css("display","block");
                validate = false;
            }
        }

        if (vehicleNum == null || vehicleNum =='') {
            jQuery("#vehicleNum_help").css("display","block");
            validate = false;
        } else {
            jQuery("#vehicleNum_help").css("display","none");
        }

        if (validate) {
            jQuery('#basic_validate').submit();
        }
    }
    //删除订单
    function deleteOrderConfirm(orderUuid) {
        layer.confirm('请确认是否删除该订单信息？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href='${pageContext.request.contextPath}/carrier/orderdelete.html?orderUuid=' + orderUuid + '&keywords=${paging.keywords}&current=${paging.currentPageNumber}';
        });
    }

    //创建工单自动填充
    function getWorkOrderVehicleNumber() {
        jQuery("#targetNumber").autocomplete('${pageContext.request.contextPath}/carrier/workordervehicleautocomplete.html', {
            minChars: 2,
            max: 10,
            delay:1000,
            highlight: false,
            scroll: false,
            matchCase: true,
            dataType: "json",
            extraParams: { searchWords: function() { return jQuery('#targetNumber').val(); }},
            parse: function(data) {
                if(data.length <= 0) {
                    $("#targetNumber").val("");
                }
                return jQuery.map(data, function(row) {
                    return {
                        data: row,
                        value: row.vehicleUuid,
                        result: row.vehicleNumber
                    }
                });
            },
            formatItem: function(item) {
                return item.vehicleNumber;
            }
        }).result(function(e, item) {
            jQuery("#targetNumber").val(item.vehicleNumber);
        });
    }
</script>
</body>
</html>
