<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spring-form" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>绿色共享运力平台</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/ionicons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/datepicker/datepicker3.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/admin.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/skins/_all-skins.min.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/dist/css/RAJA.css">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="wrapper">
    <%--开头菜单部分***********************************************************--%>

    <jsp:include page="/WEB-INF/decorators/header.jsp"/>

    <%--内容部分***********************************************************--%>

    <div class="content-wrapper">
        <%--导航栏部分--%>
        <section class="content-header">
            <h1>
                租金缴纳 -> 销售:${saleManName}&nbsp;&nbsp;合同编号:${contactNumber}
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><a href="#">租赁订单</a></li>
                <li class="active">合同管理</li>
            </ol>
        </section>

        <%--报表内容部分    --%>
        <section class="content">
            <div class="box">

                <%--TITLE部分--%>
                <div class="container-fluid">
                    <spring-form:form class="table-responsive col-md-12" id="basic_validate" name="basic_validate" commandName="zuPinVehicleExecuteDTO" action="${pageContext.request.contextPath}/contact/zupincontactrepay.html" method="post">
                        <input type="hidden" name="zuPinContactUuid" value="${zuPinContactUuid}">
                        <input type="hidden" name="tiChePiCi" value="${tichePiCi}">
                        <table class="table mb30">
                            <thead style="cursor: pointer;">
                            <th width="5%">序号</th>
                            <th width="10%">车牌号</th>
                            <th width="10%">品牌</th>
                            <th width="10%">车辆名称</th>
                            <th width="10%">车牌颜色</th>
                            <th width="10%">合同每月租金</th>
                            <th width="10%">实际每月租金</th>
                            <th width="10%">下次缴费时间</th>
                            <th width="5%">租期</th>
                            <th width="10%">批次号</th>
                            <%--<th width="10%">状态</th>--%>
                            <%--<th>操作</th>--%>
                            </thead>
                            <tbody>
                            <c:forEach items="${speExcuteDtoList}" var="line" varStatus="counter">
                                <tr class="gradeX">
                                    <td>${counter.count}</td>
                                    <td>${line.vehicleNum}</td>
                                    <td>${line.moduleBrand}</td>
                                    <td>${line.moduleName}</td>
                                    <td>${line.moduleColor} </td>
                                    <td>${line.singleRentFee}</td>
                                    <td>${line.actualRentFee}</td>
                                    <td><font color="red"> ${line.nextFeeDate}</font></td>
                                    <td> ${line.rentMonth}</td>
                                    <td>${line.tiChePiCi}</td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <%--<td colspan="2"></td>--%>
                                <td align="center" colspan="8">
                                    <p style="font-size: 15px">当前合同余额${currentBalance}，共应缴纳租金<font color="red">${piciFee}</font><span class="text-danger"><spring-form:errors path="singleRentFee"/></span></p>
                                </td>
                            </tr>
                            <tr>
                                <%--<td colspan="4"></td>--%>
                                <td align="center" colspan="8">
                                    <a href="${pageContext.request.contextPath}/contact/zupincontactdetails.html?zuPinContactUuid=${zuPinContactUuid}" class="btn btn-primary">返回</a>
                                    <button type="button" class="btn btn-success" onclick="savePiCiFeeConfirm();">确认缴纳</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </spring-form:form>
                </div>

                <%--分页处理--%>
                <%--<div class="box-footer no-padding">
                    <div class="container-fluid no-padding text-center">
                        <div class="pagination col-md-12 text-center no-padding">
                            <ul class="pagination">
                                <kf:paging urlMapping="${pageContext.request.contextPath}/contact/zupincontactoverview.html" paging="${paging}"/>
                            </ul>
                        </div>
                    </div>
                </div>--%>
            </div>
        </section>
    </div>
</div>

<%--页脚部分***********************************************************--%>
<jsp:include page="/WEB-INF/decorators/footer.jsp"/>

<%--弹出框--%>
<div class="modal fade" id="savePiCiFeeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content" style="height: 170px;border-radius:5px">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    系统对话框
                </h4>
            </div>
            <div class="modal-body">
                <p>租金缴纳后无法撤销，请确认是否缴纳</p>
                <br>
                <div class="pull-right">
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        取消
                    </button>
                    <button type="button" class="btn btn-primary" onclick="savePiCiFee();">
                        确认
                    </button>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- jQuery 2.2.0 -->
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="${pageContext.request.contextPath}/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/dist/js/app.min.js"></script>
<!-- User JS -->
<script src="${pageContext.request.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${pageContext.request.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${pageContext.request.contextPath}/dist/js/demo.js"></script>
<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/plugins/BlockUI/BlockUI.js"></script>

<script type="text/javascript">
    function savePiCiFeeConfirm() {
        jQuery("#savePiCiFeeModal").modal();
    }
    function savePiCiFee() {
        jQuery("#basic_validate").submit();
    }

</script>

</body>
</html>

