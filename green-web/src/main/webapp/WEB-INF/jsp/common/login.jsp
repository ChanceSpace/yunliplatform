<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="Bookmark" href="${pageContext.request.contextPath}/build/images/favicon.png">
    <link rel="Shortcut Icon" href="${pageContext.request.contextPath}/build/images/favicon.png">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>绿色共享运力平台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/build/css/login.css" />
    <style>
        body {
            margin: 0;
            padding: 0;
            background: url('${pageContext.request.contextPath}/build/images/login.jpg') no-repeat;
            background-size: 100% 100%;
            background-attachment:fixed;
            width:100%;height:100%;
            filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='build/images/login.png', sizingMethod='scale');
            -ms-filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='build/images/login.png', sizingMethod='scale');
        }
        .title{font-size:40px;margin-top:6%;margin-left:10%;color:#fff;}
        .verification_code{position: absolute;left:140px;top:4px;width:60px;height:30px;line-height:30px;text-align: center;background:#fff;color: #333}
        .change_code{position: absolute;left:210px;top:2px;font-size:12px;line-height:34px;cursor: pointer}
        .change_code_icon{padding-left:4px;vertical-align: bottom}
    </style>
</head>

<h2 class="title">为客户创造价值，为人类净化环境</h2>

<div class="beg-login-box">
    <header>
        <h1>绿色共享运力平台</h1>
    </header>
    <div class="beg-login-main">
        <form id="login_form" action="${pageContext.request.contextPath}/j_spring_security_check" method="post" class="layui-form">
            <div class="layui-form-item">
                <label class="beg-login-icon"><i class="layui-icon">&#xe612;</i></label>
                <input id="j_username" name="j_username" type="tel" maxlength="11" autocomplete="off" placeholder="用户名" class="layui-input isMobile">
            </div>
            <div class="layui-form-item">
                <label class="beg-login-icon"><i class="layui-icon">&#xe642;</i></label>
                <input id="j_password" name="j_password" type="password"  autocomplete="off" placeholder="密    码" class="layui-input pass">
            </div>
            <div class="layui-form-item">
                <div style="width:120px"><label class="beg-login-icon"><i class="layui-icon">&#xe64a;</i></label><input id="j_code" name="j_code" type="text"  autocomplete="off" placeholder="验证码" class="layui-input"></div>
                <div class="verification_code"><img id="imageCode" src="${pageContext.request.contextPath}/getcode.html" width="100%" height="100%"></div>
                <div class="change_code" onclick="imageChange();">看不清<i class="layui-icon change_code_icon">&#x1002;</i></div>
            </div>
            <div class="layui-form-item">
                <a class="layui-btn layui-btn-primary" lay-submit lay-filter="login" style="width:100%;" onclick="login();">
                    <i class="layui-icon">&#xe715;</i> 登录
                </a>
                <div class="beg-clear"></div>
            </div>
        </form>
    </div>
   <%-- <footer>
        <p>绿色运力平台登录</p>
    </footer>--%>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>

<script type="text/javascript">
    var exception = '${SPRING_SECURITY_LAST_EXCEPTION}';

    layui.config({
        base: '${pageContext.request.contextPath}/plugins/layui/modules/'
    });
    layui.use(['layer', 'form'], function() {
        var form = layui.form;

        if(exception != null && exception != '') {
            layer.open({
                type: 1
                ,title: false //不显示标题栏
                ,closeBtn: false
                ,area: '300px;'
                ,shade: 0.8
                ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                ,moveType: 1 //拖拽模式，0或者1
                ,content: '<div style="padding: 20px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">您的用户名或密码错误<br><br>请确认后再尝试登录!</div>'
                ,"time":3000
              });
            }

        });

     jQuery(document).on('keydown', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
            login();
        }
    });

    function imageChange() {
        var img = document.getElementById("imageCode");
        img.src = "${pageContext.request.contextPath}/getcode.html?" + Math.random();
    }

    function login() {
        var validate = true;
        var codeValue = jQuery("#j_code").val();
        if(codeValue == null || codeValue == '') {
            showCode();
        } else {
            $.ajax({
                type : "POST",
                url : "${pageContext.request.contextPath}/checkcode.html",
                data:{codeValue:codeValue},
                dataType : "json",
                success : function(result) {
                    var flag = result.flag;
                    if("NO" == flag) {
                        showCode();
                    } else {
                        jQuery('#login_form').submit();
                    }
                }
            });
        }
    }

    function showCode() {
        layer.open({
            type: 1
            ,title: false //不显示标题栏
            ,closeBtn: false
            ,area: '300px;'
            ,shade: 0.8
            ,id: 'LAY_layuipro1' //设定一个id，防止重复弹出
            ,moveType: 1 //拖拽模式，0或者1
            ,content: '<div style="padding: 20px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">验证码不正确</div>'
            ,"time":2000
          });
    }

</script>

</body>
</html>
