<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<body>
<div style="margin:30px 0 30px ">
    <form action="${pageContext.request.contextPath}/carrier/cancelHistory.html" id="cancelForm" name="basic_validate" novalidate="novalidate" class="layui-form" method="post">
        <input type="hidden" name="zuPinContactUuid" value="${zuPinContactUuid}">
        <input type="hidden" name="selectCarrierUuid" value="${selectCarrierUuid}">
        <input type="hidden" name="historyUuid" value="${historyUuid}">

        <div class="layui-form-item">
            <label class="layui-form-label">结束备注</label>
            <div class="layui-input-block">
                <textarea name="comment"   class="layui-textarea" placeholder="请输入内容"></textarea>
            </div>
        </div>
    </form>

    <div style="text-align: center">
        <input type="button" class="layui-btn" onclick="submitCancelForm()" value="提交"></input>
        <input type="button" class="layui-btn" onclick="closePopup(settings.fadeOutTime);" value="取消"></input>
    </div>

</div>

</body>
</html>
