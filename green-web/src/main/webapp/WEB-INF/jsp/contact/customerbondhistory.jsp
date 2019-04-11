<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                合同详情 -> 销售:${saleManName}&nbsp;&nbsp;编号:${contactNumber}
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><a href="#">合同管理</a></li>
                <li class="active">押金变动历史</li>
            </ol>
        </section>

        <section class="content">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">押金历史</h3>
                    <a href='${pageContext.request.contextPath}/contact/zupincontactdetails.html?zuPinContactUuid=${zuPinContactUuid}&check=${check}'  class="btn btn-warning pull-right">返回</a>
                </div>

                <%--<div class="box-header">
                    <form id="user_search_form" action="${pageContext.request.contextPath}/contact/usermanagement.html" method="POST" class="form">
                        <div class="row">
                            <div class="col-md-2">
                                <label for="name">姓名:</label>
                                <input type="text" id="name" name="name" class="form-control" value="${name}">
                            </div>
                            <div class="col-md-2">
                                <label for="searchDepartmentId">部门:</label>
                                <select id="searchDepartmentId" name="searchDepartmentUuid" class=" form-control">
                                    <option value="">全部</option>
                                    <c:forEach items="${departments}" var="department">
                                        <option value="${department.uuid}" <c:if test="${department.uuid == searchDepartmentUuid}">selected="true"</c:if>>${department.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <label for="personalStatus">人员状态:</label>
                                <select id="personalStatus" name="personalStatus" class="form-control">
                                    <option value="">全部</option>
                                    <c:forEach items="${personalStatuss}" var="status">
                                        <option value="${status.name}" <c:if test="${status.name == personalStatus}">selected="true"</c:if>>${status.descrption}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-1">
                                <label>&nbsp;</label><br/>
                                <a href="javascript:void(0);" onclick="jQuery('#user_search_form').submit();" class="btn btn-info" >查询</a>
                            </div>

                            <div class="col-md-2 pull-right">
                                <a class="btn btn-default" href="${pageContext.request.contextPath}/contact/userform.html?name=${name}&current=${current}&searchDepartmentUuid=${searchDepartmentUuid}&personalStatus=${personalStatus}"><i class="fa fa-plus"></i> 添加员工</a>
                            </div>
                        </div>
                    </form>
                </div>
--%>
                <div class="container-fluid">
                    <div class="table-responsive col-md-12">
                        <table class="table mb30">
                            <thead>
                                <th width="10%">序号</th>
                                <th width="15%">公司名称</th>
                                <th width="15%">时间</th>
                                <th width="10%">交易类型</th>
                                <th width="15%">单次交易金额</th>
                                <th width="15%">操作人</th>
                                <th width="35%">备注</th>
                            </thead>
                            <tbody>
                            <c:forEach items="${historys}" var="history" varStatus="counter">
                            <tr class="gradeX">
                                <td>
                                        ${counter.count}
                                </td>
                                <td>
                                        ${history.customerName}
                                </td>
                                <td>
                                        ${history.chongZhiTime}
                                </td>
                                <td>
                                    <c:if test="${history.add==true}">
                                        充值
                                    </c:if>
                                    <c:if test="${history.add==false}">
                                        转出
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${history.add==true}">
                                        <font color="green">${history.feeOnce}</font>
                                    </c:if>
                                    <c:if test="${history.add==false}">
                                        <font color="red">${history.feeOnce}</font>
                                    </c:if>
                                </td>
                                <td>${history.jiaoYiRen}</td>
                                <td>${history.comment}</td>
                            </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>


                <div class="box-footer no-padding">
                    <div class="container-fluid no-padding text-center">
                        <div class="pagination col-md-12 text-center no-padding">
                            <ul class="pagination">
                                <kf:paging urlMapping='${pageContext.request.contextPath}/contact/zupincontactyajin.html' paging="${paging}"/>
                            </ul>
                        </div>
                    </div>
                </div>



            </div>
        </section>
    </div>
</div>
<jsp:include page="/WEB-INF/decorators/footer.jsp"/>

<!-- jQuery 2.2.0 -->
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="${pageContext.request.contextPath}/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="${pageContext.request.contextPath}/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/dist/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${pageContext.request.contextPath}/dist/js/demo.js"></script>

</body>
</html>