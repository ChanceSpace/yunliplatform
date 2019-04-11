<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <title>绿色共享运力平台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
    <style>
    	.layui-timeline-title{font-size:24px;}
    </style>
</head>

<body>

<div style="margin:20px;">
    <spring-form:form commandName="password" id="user_password_form" class="layui-form" method="post">
        <fieldset class="layui-elem-field">
           <legend><i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;修改密码</legend>
           <div class="layui-field-box">

               <div class="layui-form-item">
                    <label class="layui-form-label">当前用户</label>
                    <div class="layui-input-block">
                        <input type="text" id="name" name="name" lay-verify="required" placeholder="" autocomplete="off" class="layui-input" readonly="true" value="${password.name}"/>
                    </div>
                </div>

               <div class="layui-form-item">
                    <label class="layui-form-label">新密码<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <input type="password" id="newPassword" name="newPassword"  maxlength="18" lay-verify="required" placeholder="8-18位数字字母组成" autocomplete="off" class="layui-input"/>
                        <label id="newPassword_help" class="error" style="display: none; color: red;">提示:新密码不能为空</label>
                        <label id="newPasswordShort_help" class="error" style="display: none; color: red;">提示:新密码至少8位</label>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">确认新密码<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <input type="password" id="newPasswordAgain" name="newPasswordAgain" maxlength="18" lay-verify="required" placeholder="8-18位数字字母组成" autocomplete="off" class="layui-input"/>
                        <label id="newPasswordAgain_help" class="error" style="display: none; color: red;">提示:两次密码不一致</label>
                    </div>
                </div>
		  </div>

        </fieldset>
    </spring-form:form>

    <div class="layui-form-item">
            <button class="layui-btn" onclick="saveUserPassword();">保存</button>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>

<script type="text/javascript">
    var message = ${message};

    layui.use(['form'], function() {
        var form = layui.form,
        layer = layui.layer;

        if(message) {
            layer.open({"content":"密码修改成功","time":2000});
        }
    });

    function saveUserPassword() {
        var validate = true;
        var newPassword = jQuery("#newPassword").val();
        var newPasswordAgain = jQuery("#newPasswordAgain").val();

        if(newPassword == null || newPassword == '') {
            jQuery("#newPassword_help").css("display","block");
            validate = false;
        } else {
            if (newPassword.length < 8) {
                jQuery("#newPasswordShort_help").css("display","block");
                validate = false;
            } else {
                jQuery("#newPasswordShort_help").css("display","none");
            }
            jQuery("#newPassword_help").css("display","none");
        }
        if(newPasswordAgain == null || newPasswordAgain == '') {
            jQuery("#newPasswordAgain_help").css("display","block");
            validate = false;
        } else {
            jQuery("#newPasswordAgain_help").css("display","none");
        }



        if(validate) {
            if(newPassword != newPasswordAgain) {
                jQuery("#newPasswordAgain_help").css("display","block");
            } else {
                jQuery('#user_password_form').submit();
            }
        }
    }

</script>

</body>
</html>