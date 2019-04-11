<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>

<body>
    <%--合同条款部分--%>
    <div>
        <spring-form:form commandName="execute" id="contact_tiche_form" class="layui-form" action="${pageContext.request.contextPath}/contact/zupincontactvehicleget.html" method="post">
            <spring-form:input type="hidden" path="uuid"/>
            <input type="hidden" name="tiChePiCi" value="${tiChePiCi}"/>
            <input type="hidden" name="zuPinContactUuid" value="${zuPinContactUuid}"/>
            <input type="hidden" id="selectModuleBrand" name="selectModuleBrand"/>
            <input type="hidden" name="today" value="${today}"/>
            <input type="hidden" name="fifteendays" value="${fifteendays}"/>

            <fieldset class="layui-elem-field">
                <legend><i class="layui-icon">&#xe705;</i>合同提车</legend>
                 <div class="layui-field-box">

                     <div class="layui-form-item">
                         <label class="layui-form-label">选择车型</label>
                         <div class="layui-input-block">
                            <select id="moduleBrand" name="moduleBrand" lay-filter="moduleBrand">
                                <option value="ALL|0">
                                    请选择要提车的车型
                                </option>
                                <c:forEach items="${contact.contactModules}" var="loop">
                                    <option value="${loop.moduleBrand}|${loop.leftTiCheNumber}">${loop.moduleName} [${loop.moduleType} - ${loop.moduleBrand}<c:if test="${loop.moduleDianLiang != null && loop.moduleDianLiang != ''}">(${loop.moduleDianLiang}度电)</c:if> - ${loop.moduleColor}]  [合同车辆:${loop.rentNumber}辆 - 已提车${loop.alreadyTiChe}辆]
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                     <div class="layui-form-item">
                        <label class="layui-form-label">提车日期</label>
                        <div class="layui-input-block">
                            <spring-form:input id="tiCheDate" path="tiCheDate" class="datepicker layui-input" readonly="true"/>
                            <label id="happendDate_help" class="error" style="display: none; color: red;">提示:<spring-form:errors path="tiCheDate"/></label>
                            <label id="tiCheDate_help" class="error" style="display: none; color: red;">提示:提车日期需在合同范围内</label>
                        </div>
                    </div>

                     <div class="layui-form-item">
                        <div class="layui-form-label">&nbsp;</div>
                        <div  class="layui-input-block">
                                <p id="vehiclenumber_errormessage" style="color: red;"></p>
                        </div>
                     </div>

                     <div id="vehicle_details_part" style="visibility: hidden;">
                         <%--提车车牌号填写区域 由detail页面动态生成--%>
                     </div>

                 </div>
            </fieldset>
        </spring-form:form>

        <div align="center">
                <button class="layui-btn" onclick="saveTiChe();">保存</button>
                <button class="layui-btn layui-btn-primary" lay-filter="cancal" onclick="closePopup(settings.fadeOutTime);">取消</button>
        </div>

    </div>

</body>
</html>
