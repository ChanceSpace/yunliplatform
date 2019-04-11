<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<body>
<div>
    <spring-form:form id="chongzhiForm1" name="basic_validate" commandName="zuPinContactRentingFeeHistory"
                      class="layui-form"
                      action="${pageContext.request.contextPath}/contact/zupincontactyajinchongzhi.html" method="post">
        <input type="hidden" name="customeruuid" value="${customeruuid}"/>
        <input type="hidden" name="zuPinContactUuid" value="${zuPinContactUuid}"/>
        <input type="hidden" name="contactNumber" value="${contactNumber}"/>
        <input type="hidden" name="saleManName" value="${saleManName}"/>
        <input type="hidden" name="startDate" value="${startDate}"/>
        <input type="hidden" name="today" value="${today}"/>
        <input type="hidden" name="endDate" value="${endDate}"/>
        <input type="hidden" name="endExecute" value="${endExecute}"/>
        <input type="hidden" name="chongzhi" value="${chongzhi}"/>

        <fieldset class="layui-elem-field">
            <legend>
                <i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;押金扣费
            </legend>
            <div class="layui-field-box">
                <div class="layui-form-item">
                    <label class="layui-form-label">扣款金额<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <input type="text" id="feeTotal" name="feeTotal" class="layui-input" maxlength="20"
                               onkeyup="if(isNaN(value))execCommand('undo')"
                               onafterpaste="if(isNaN(value))execCommand('undo')"/>
                        <label id="feeTotal_help" class="error"
                               style="display: none; color: red;">提示:费用字段不能为空，而且必须为数字</label>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">扣费日期<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <input id="happendDate" name="happendDate" class="datepicker layui-input" readonly="true"
                               value="${today}"/>
                        <label id="happendDate_help" class="error"
                               style="display: none; color: red;">提示:付款日期需在合同范围内</label>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea name="comment" class="layui-textarea"></textarea>
                    </div>
                </div>
            </div>

        </fieldset>
    </spring-form:form>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" onclick="saveMoneyConfirm1(${chongzhi});">确认</button>
            <button class="layui-btn layui-btn-primary" lay-filter="cancal" onclick="closePopup(settings.fadeOutTime);">
                取消
            </button>
        </div>
    </div>

</div>

</body>
</html>