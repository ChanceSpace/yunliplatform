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


    <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/echarts/echarts.min.js"></script>

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

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                员工管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><a href="#">人员管理</a></li>
                <li class="active">员工管理</li>
            </ol>
        </section>
        <section class="content">
            <!-- Default box -->
            <!-- /.box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">添加/编辑员工</h3>
                </div>
                <div class="box-body">
                    <div class="container-fluid">
                        <spring-form:form commandName="poster" id="basic_validate" name="basic_validate" novalidate="novalidate" class="form-horizontal" method="post">
                            <input type="hidden" name="uuid" value="${poster.uuid}"/>
                            <input type="hidden" name="carrierUuid" value="${poster.carrierUuid}"/>

                            <div class="control-group">
                                <label class="control-label" for="carrierPoster">展示状态</label>
                                <div class="controls">
                                    <c:if test="${poster.published}">
                                        已发布
                                    </c:if>
                                    <c:if test="${!poster.published}">
                                        未发布
                                    </c:if>
                                </div>
                            </div>

                            <div class="control-group">
                                <label class="control-label" for="carrierPoster">展示内容</label>
                                <div class="controls">
                                    <spring-form:textarea id="carrierPoster" path="carrierPoster" cssClass="form-control"/>
                                </div>
                            </div>
                            <br/>
                            <div class="form-actions">
                                <c:if test="${!poster.published}">
                                        <input type="button" value="对外发布" class="btn btn-primary" onclick="changePublish();">
                                    </c:if>
                                    <c:if test="${poster.published}">
                                        <input type="button" value="停止发布" class="btn btn-primary" onclick="changePublish();">
                                    </c:if>
                                <input type="submit" value="保 存" class="btn btn-primary">
                            </div>
                        </spring-form:form>
                    </div>
                </div>
                <div class="box-footer">
                    <section class="content">
                        <div style="width:100%;height: 600px" id="main"></div>
                    </section>
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
<script src="${pageContext.request.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${pageContext.request.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${pageContext.request.contextPath}/dist/js/demo.js"></script>

<script src="${pageContext.request.contextPath}/dwr/engine.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/dwr/util.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/dwr/interface/SystemDWRHandler.js" type="text/javascript"></script>

<%--<CKEDITOR></CKEDITOR>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/ckeditor/plugins/image/dialogs/image.js"></script>

<script type="text/javascript">
    CKEDITOR.replace('carrierPoster', {filebrowserUploadUrl : '${pageContext.request.contextPath}/ckeditor/ckeditor/uploader?Type=File',
         filebrowserImageUploadUrl : '${pageContext.request.contextPath}/ckeditor/ckeditor/uploader?Type=Image',
         filebrowserFlashUploadUrl : '${pageContext.request.contextPath}/ckeditor/ckeditor/uploader?Type=Flash'
    });

    function changePublish() {
        window.location.href = '${pageContext.request.contextPath}/common/carrierposterstatus.html'
    }
</script>

</body>
</html>