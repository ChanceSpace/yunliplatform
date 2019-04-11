<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<body>

<div>
    <spring-form:form commandName="vehicleChangeHistoryDTO" id="vehicle_change_form"
                      name="basic_validate" novalidate="novalidate" class="layui-form"
                      method="post">

        <input type="hidden" name="zuPinContactUuid" value="${zuPinContactUuid}"/>

        <fieldset class="layui-elem-field">
            <legend><i class="layui-icon">&#xe63c;</i>&nbsp;车辆变更</legend>
            <div class="layui-field-box">

                <div class="layui-form-item">
                    <label class="layui-form-label">车牌号<br/> (更换前)</label>
                    <div class="layui-input-block">
                        <spring-form:input path="vehicleNumberBefore" maxlength="8" cssClass="layui-input" readonly="true"/>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">车牌号<i class="font_E60012">*</i><br/>(更换后)</label>
                    <div class="layui-input-block">
                        <spring-form:input id="targetNumber" onkeydown="getvehicleChangeNumber()" name="vehicleNumberNow" path="vehicleNumberNow" maxlength="8" cssClass="layui-input" placeholder="请输入车牌号(至少两位),并从系统检索结果选择"/>
                        <label id="vehicleNumber_help" class="error" style="display: none; color: red;"></label>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea name="comment"   class="layui-textarea" placeholder="请输入内容"></textarea>
                    </div>
                </div>

            </div>
        </fieldset>
    </spring-form:form>


    <div align="center">
        <button class="layui-btn" onclick="changeVehicle()">确认</button>
        <button class="layui-btn layui-btn-primary" lay-filter="cancal" onclick="closePopup(settings.fadeOutTime);">取消</button>
    </div>

</div>

</body>
</html>
