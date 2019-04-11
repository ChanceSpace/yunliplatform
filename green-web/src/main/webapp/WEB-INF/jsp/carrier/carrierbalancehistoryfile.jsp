<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<body>
<div>
    <spring-form:form id="carrierBalanceForm" name="basic_validate" commandName="carrierBalanceHistory" class="layui-form" action="${pageContext.request.contextPath}/carrier/carrierbalancehistoryfile.html" method="post" enctype="multipart/form-data">
        <input type="hidden" name="today" value="${today}"/>
        <input type="hidden" name="userUuid" value="${userUuid}"/>
        <input type="hidden" name="rentFeeHisUuid" value="${rentFeeHisUuid}"/>

        <fieldset class="layui-elem-field">

            <legend>
                    <i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;附件添加
            </legend>

            <div class="layui-field-box">

                <div class="layui-form-item">
                    <div class="layui-form-item">
                        <label class="layui-form-label">选择附件</label>
                        <div class="layui-input-block">
                            <input type="file" id="picture" name="picture" multiple="false" class="layui-input" />
                            <input type="hidden" value="YES" id="image_right"/>
                        </div>
                    </div>
                </div>

            </div>

        </fieldset>
    </spring-form:form>

     <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" onclick="jQuery('#carrierBalanceForm').submit();">确认</button>
            <button class="layui-btn layui-btn-primary" lay-filter="cancal" onclick="closePopup(settings.fadeOutTime);">取消</button>
        </div>
    </div>

</div>

</body>
</html>