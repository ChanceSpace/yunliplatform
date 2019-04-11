<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<body>
<div>

    <fieldset class="layui-elem-field">
        <legend><i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;车辆备注</legend>
        <ul class="layui-timeline">
            <c:forEach items="${comments}" var="line">

                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">备注</h3>
                            <ul>
                                <li>${line}</li>
                            </ul>
                        </div>
                    </li>

            </c:forEach>
            <li class="layui-timeline-item">
                <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                <div class="layui-timeline-content layui-text">
                </div>
            </li>
        </ul>
        <div style="text-align: center"><button class="layui-btn layui-btn-primary" onclick="closePopup(settings.fadeOutTime)" >确定</button></div>
    </fieldset>
</div>

</body>
</html>
