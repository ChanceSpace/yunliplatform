<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
</head>
<body>
<div class="max-height: 600px; overflow-y: auto;">
    <spring-form:form commandName="order" id="basic_validate" name="basic_validate" class="layui-form" method="post">
        <input type="hidden" name="orderUuid" value="${order.uuid}"/>
        <input type="hidden" name="keywords" value="${keywords}"/>
        <input type="hidden" name="current" value="${current}"/>
        <input type="hidden" name="orderNumber" value="${order.orderNumber}"/>

        <fieldset class="layui-elem-field">
            <legend><i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;服务信息</legend>
            <div class="layui-field-box">

                <div class="layui-form-item">
                    <label class="layui-form-label" for="serviceType">服务类型<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <select class="layui-input" id="orderType" name="orderType" >
                            <c:forEach items="${orderTypes}" var="type">
                                <option value="${type.value}" <c:if test="${type.value == order.orderType}">selected="true"</c:if>>${type.label}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label" for="vehicleNum">车牌号<i class="font_E60012">*</i></label><span class="text-danger"></span>
                    <div class="layui-input-block">
                        <spring-form:input id="targetNumber" onkeydown="getWorkOrderVehicleNumber()" name="vehicleNum" path="vehicleNum" maxlength="8" cssClass="layui-input" placeholder="请输入车牌号(至少两位),并从系统检索结果选择"/>
                        <label id="vehicleNum_help" class="error" style="display: none;color: red;">提示：请选择车辆</label>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label" for="contactName">联系人<i class="font_E60012">*</i></label><span class="text-danger"></span>
                    <div class="layui-input-block">
                        <spring-form:input id="contactName" path="contactName" maxlength="7" lay-verify="required" placeholder="" autocomplete="off" class="layui-input"/>
                        <label id="contactName_help" class="error" style="display: none;color: red;">提示：请填写联系人</label>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label" for="contactPhone">联系电话<i class="font_E60012">*</i></label><span class="text-danger"></span>
                    <div class="layui-input-block">
                        <spring-form:input id="contactPhone" path="contactPhone" maxlength="11" lay-verify="required" placeholder="" autocomplete="off" class="layui-input" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
                        <label id="contactPhone_help" class="error" style="display: none;color: red;">提示：请正确填写联系电话</label>
                    </div>
                </div>




                <div class="layui-form-item">
                    <label class="layui-form-label" for="orderNote">服务说明</label>
                    <div class="layui-input-block">
                        <spring-form:textarea path="orderNote" maxlength="2048"
                                              lay-verify="required" placeholder="" autocomplete="off" class="layui-textarea"/>
                    </div>
                </div>
                <br/>
            </div>
        </fieldset>
    </spring-form:form>

    <div class="layui-form-item" style="text-align: center">
            <button class="layui-btn" onclick="saveCarrierOrder();">保存</button>
            <button class="layui-btn layui-btn-primary" lay-filter="cancal" onclick="closePopup(settings.fadeOutTime);">取消</button>
    </div>
</div>
</body>
</html>