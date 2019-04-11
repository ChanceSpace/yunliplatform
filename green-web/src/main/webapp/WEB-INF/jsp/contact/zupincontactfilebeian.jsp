<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
</head>

<body>
<div >

    <%--合同条款部分--%>
    <form action="${pageContext.request.contextPath}/contact/zupincontactfilebeian.html" id="contact_file_form" name="basic_validate" novalidate="novalidate" class="layui-form" method="post"  enctype="multipart/form-data">
        <input type="hidden" name="zuPinContactUuid" value="${zuPinContactUuid}"/>

         <fieldset class="layui-elem-field">
            <legend><i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;合同附件备案</legend>
            <div class="layui-field-box">

                <div class="layui-form-item">
                    <label class="layui-form-label">选择附件</label>
                    <div class="layui-input-block">
                        <input type="file" id="contactFile" name="contactFile" class="layui-input"/>
                        <label id="contactFile_help" class="error" style="display: none; color: red;">提示:附件不能为空</label>
                    </div>
                </div>

            </div>
         </fieldset>
    </form>

    <div class="layui-form-item" style="text-align: center">
            <button class="layui-btn" onclick="saveZuPinContactFile();">确认</button>
            <button class="layui-btn layui-btn-primary" lay-filter="cancal" onclick="closePopup(settings.fadeOutTime);">取消</button>
    </div>

</div>
</body>
</html>
