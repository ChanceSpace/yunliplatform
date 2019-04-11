<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="yj" uri="http://www.rajaev.com" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>支付方式</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css" media="all"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" media="all">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
	<style>
		body{background:#f0f2f5}
	</style>
</head>
<body>
<fieldset class="fieldset_box">
	<div class="layui-container">
		<div class="order_detail">
			<yj:security grantRoles="ROLE_GREEN_ADMIN,ROLE_GREEN_CARRIER_RENT,ROLE_GREEN_CONTACT_SALE">
				<c:if test="${carrierList}">
					<div class="scum_nav" style="background:#ffffff;">当前位置：<a href="${pageContext.request.contextPath}/carrier/carrierbalanceinfomanagement.html" class="font_33a7fd">客户列表</a><img src="${pageContext.request.contextPath}/build/images/arrow_right.png" width="10"><a href="${pageContext.request.contextPath}/carrier/carrierbalanceoverview.html?userUuid=${userUuid}&carrierList=${carrierList}&userList=${userList}" class="font_33a7fd">账户详情</a><img src="${pageContext.request.contextPath}/build/images/arrow_right.png" width="10">交易详情</div>
				</c:if>
				<c:if test="${!carrierList}">
					<div class="scum_nav" style="background:#ffffff;">当前位置：<a href="${pageContext.request.contextPath}/carrier/payordercarrieroverview.html" class="font_33a7fd">我的交易</a><img src="${pageContext.request.contextPath}/build/images/arrow_right.png" width="10">交易详情</div>
				</c:if>
		</yj:security>
		<yj:security grantRoles="ROLE_GREEN_CARRIER">
            <yj:security grantRoles="ROLE_GREEN_CARRIER_RENT">
                <div class="scum_nav" style="background:#ffffff;">当前位置：<a href="${pageContext.request.contextPath}/carrier/payordercarrieroverview.html" class="font_33a7fd">我的交易</a><img src="${pageContext.request.contextPath}/build/images/arrow_right.png" width="10">交易详情</div>
            </yj:security>
		</yj:security>
			<div class="detail_section1">
			<div class="detail_section1">
			<div class="layui-row">
				<div class="layui-col-md3 left_box">
					<p>&nbsp;&nbsp;订单号:${payOrderNumber }</p>
					<c:if test="${order.payOrderStatus =='O_FINSIH'}">
						<p class="subtitle">完成</p>
					</c:if>
					<c:if test="${order.payOrderStatus =='O_CREATE'}">
						<p class="subtitle" style="color: red">未完成</p>
					</c:if>
				</div>
				<div class="layui-col-md9 right_box">
					<div class="right_progress">
						<div class="right_progress_list">
							<img src="${pageContext.request.contextPath}/build/images/progress-1.png" width="30"/>
							<p class="title">提交订单</p>
							<p class="subtitle">${order.createTime }</p>
						</div>
						<div class="right_progress_list_2">
							<img src="${pageContext.request.contextPath}/build/images/progress-4.png" width="140"/>
						</div>
						<div class="right_progress_list">
							<img src="${pageContext.request.contextPath}/build/images/progress-2.png" width="30"/>
							<c:if test="${order.payOrderStatus =='O_FINSIH'}">
								<p class="title">支付成功</p>
								<p class="subtitle">${order.finishTime }</p>
							</c:if>
							<c:if test="${order.payOrderStatus =='O_CREATE'}">
								<p class="title">待支付</p>
							</c:if>
						</div>
						<div class="right_progress_list_2">
							<img src="${pageContext.request.contextPath}/build/images/progress-4.png" width="140"/>
						</div>
						<div class="right_progress_list">
							<img src="${pageContext.request.contextPath}/build/images/progress-3.png" width="30"/>
							<c:if test="${order.payOrderStatus =='O_FINSIH'}">
								<p class="title">已完成</p>
							</c:if>
							<c:if test="${order.payOrderStatus =='O_CREATE'}">
								<p class="title">未完成</p>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="detail_section2">
			<div class="layui-row">

				<div class="layui-col-md6">
					<div class="left_box">
						<div class="title">支付信息</div>
						<p>支付方式: ${order.payOrderWayName }</p>
						<c:if test="${order.payOrderStatus =='O_FINSIH'}">
							<p>付款时间: ${order.finishTime }</p>
						</c:if>
						<c:if test="${order.payOrderStatus =='O_CREATE'}">
							<p>下单时间: ${order.finishTime }</p>
						</c:if>
						<p>交易单号: ${order.jiaoYiNumber}</p>
						<p>交易金额: ${order.totalFee}</p>
						<c:if test="${order.orderNote != null}">
						    <p>备注: ${order.orderNote}</p>
						</c:if>
					</div>
				</div>

				<div class="layui-col-md6">
					<div class="right_box">
						<div class="title">附件信息</div>
						<spring-form:form id="payOrderDetailForm" name="basic_validate" commandName="payOrderFile" class="layui-form files_style" action="${pageContext.request.contextPath}/carrier/payorderdetailfile.html" method="post" enctype="multipart/form-data">
							<c:forEach var="payorderfile" items="${order.payOrderFileDTOS}" varStatus="counter">
								<a href="${applicationHost}payorder/${payOrderNumber}/${payorderfile.actualFileName}" class="files_a" style="color: #1E9FFF" target="_blank">${counter.count}、${payorderfile.uploadFileName}</a><br>
							</c:forEach>
							<div class="files_list"></div>
						<div class="files">
							<input type="hidden" name="orderUuid" value="${order.uuid}"/>
							<input type="hidden" name="payOrderNumber" value="${payOrderNumber}"/>

							<div style="position: relative">
								<input type="file" id="fuJian" name="fuJian" multiple="multiple" onchange="filesFun(this,files);" />
								<div class="label_file">选择文件</div><P class="label_file_num">请选择文件</P>
							</div>
						</div>
						</spring-form:form>
						<div>
							<button class="layui-btn layui-btn-small layui-btn-normal" onclick="validateAndSubmit();" style="float:right">提交上传</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="detail_section3">
			
			<div class="title">费用明细</div>
			<table class="site-table table-hover">
				<thead>
					<tr>
						<th class="th_com" width="20%">费用来源</th>
						<th class="th_com" width="60%">费用项</th>
						<th class="th_com" width="20%">金额</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${order.payItems}" var="line" >
						<tr class="gradeX">
							<td>${line.source}</td>
							<td>${line.details} ${line.comment}</td>
							<td><font color="green"> ${line.feeTotal}</font></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</div>

	</div>
	</div>
</fieldset>
	<script src="${pageContext.request.contextPath}/build/js/validate.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
	<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugins/jQuery/jquery.validate.js"></script>
	<script src="${pageContext.request.contextPath}/plugins/popup/modal.popup.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/build/js/pictureuploadvalidate.js"></script>
	<script src="${pageContext.request.contextPath}/plugins/abigImage/abigimage.jquery.js"></script>
	<script type="text/javascript">
		layui.config({
			base: 'plugins/layui/modules/'
		});
		//注意：选项卡 依赖 element 模块，否则无法进行功能性操作
		layui.use('jquery', function() {
			$ = layui.jquery;
		});

        layui.use(['laypage', 'layer','form'], function() {
            form = layui.form
        });

		function filesFun(obj,files){
			var str = "待上传文件:</br>";
			if(files){
				for(var i=0;i<files.length;i++){
					str += '<p class="font_33a7fd">'+files[i].name+'</p>';
				}
				$(".files_list").html(str);
				if(files.length>0){
                    $(".label_file_num").text("共"+files.length+"个文件");
				}else{
                    $(".label_file_num").text("请选择文件");
				}

			}
		}

		//图片预览功能
        $(function() {
			$('a[href$=".jpg"],a[href$=".png"],a[href$=".jpeg"],a[href$=".emp"]').abigimage();
        });


		//验证并提交图片form
		function validateAndSubmit() {
		    var	contactFile = jQuery("#fuJian");
            var canbeLoad = validatepicturesize(contactFile,1024*1024);
            if(canbeLoad == true){
                jQuery("#payOrderDetailForm").submit();
            }else {
                layer.msg("图片文件超过了1M，请压缩后再上传");
            }
        }

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

	</script>

</body>

</html>