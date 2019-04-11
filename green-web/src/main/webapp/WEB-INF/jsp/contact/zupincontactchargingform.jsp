<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>


<body>

<%--合同条款部分--%>
<div >

    <spring-form:form commandName="charging" id="charging_form" name="basic_validate" novalidate="novalidate" class="layui-form" method="post">
        <spring-form:input type="hidden" path="uuid"/>
        <input type="hidden" name="zuPinContactUuid" value="${zuPinContactUuid}"/>
        <input type="hidden" name="isPopup" value="${isPopup}"/>

        <fieldset class="layui-elem-field">
            <legend><i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;合同充电桩信息</legend>
            <div class="layui-field-box">

                <div class="layui-form-item">
                    <label class="layui-form-label" for="chargingNumber">编号<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input path="chargingNumber" maxlength="30" class="layui-input" id="chargingNumber"/>
                        <label id="chargingNumber_help" class="error" style="display: none;color: red;">提示：请填写编号</label>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" for="chargingAddress">详细地址<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <spring-form:input path="chargingAddress" maxlength="30" class="layui-input" id="chargingAddress"/>
                        <label id="chargingAddress_help" class="error" style="display: none;color: red;">提示：请填写地址</label>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" for="zuPinContactChargingType">类型<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <select name="zuPinContactChargingType">
                            <c:forEach items="${types}" var="loop">
                                <option value="${loop.value}" <c:if test="${loop.value == charging.zuPinContactChargingType}">selected="true"</c:if>>${loop.label} </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </fieldset>
    </spring-form:form>

    <div class="layui-form-item" style="text-align: center">
            <button class="layui-btn" onclick="saveContactChargingForm();">保 存</button>
            <button class="layui-btn layui-btn-primary" lay-filter="cancal" onclick="closePopup(settings.fadeOutTime);">取 消</button>
    </div>
</div>


</body>
</html>
