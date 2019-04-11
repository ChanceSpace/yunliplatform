<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<body>
<div>
    <spring-form:form id="carrierBalanceForm" name="basic_validate" commandName="carrierBalanceHistory" class="layui-form" action="${pageContext.request.contextPath}/carrier/carrierbalancechongzhi.html" method="post" enctype="multipart/form-data">
        <input type="hidden" name="today" value="${today}"/>
        <input type="hidden" name="userUuid" value="${userUuid}"/>

        <fieldset class="layui-elem-field">
            <legend>
                    <i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;余额充值
            </legend>
            <div class="layui-field-box">

                <div class="layui-form-item">
                        <label class="layui-form-label">充值金额<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <input type="text" id="feeTotal" name="feeTotal" class="layui-input" maxlength="20" onkeyup="num(this)" onafterpaste="if(isNaN(value))execCommand('undo')"/>
                        <label id="feeTotal_help" class="error" style="display: none; color: red;">提示:充值金额不能为空，且必须为数字</label>
                    </div>
                </div>

                <div class="layui-form-item">
                        <label class="layui-form-label">充值日期<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <input id="happendDate" name="happendDate" class="datepicker layui-input" value="${today}"/>
                        <label id="happendDate_help" class="error" style="display: none; color: red;">提示:充值日期有误</label>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">交易单号<i class="font_E60012">*</i></label>
                    <div class="layui-input-block">
                        <input type="text" id="jiaoYiNumber" name="jiaoYiNumber" class="layui-input" maxlength="48" placeholder="请输入缴费凭证的单据编号" />
                        <label id="jiaoYiNumber_help" class="error" style="display: none; color: red;">提示:请输入交易单号</label>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea  name="comment" class="layui-textarea"></textarea>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-form-item">
                        <label class="layui-form-label">选择附件<i class="font_E60012">*</i></label>
                        <div class="layui-input-block">
                            <div class="files_style">
                                <div class="label_file" style="margin-top:20px;">选择文件</div>
                                <input type="file" id="picture" name="picture" multiple="false" class="layui-input" onchange="filePreviewPic(this,files)" style="top:20px;" />
                                <input type="hidden" value="YES" id="image_right"/>
                                <div id="previewPic"></div>
                            </div>


                            <label id="fuJians_help" class="error" style="display: none; color: red;">提示:附件不能为空</label>
                        </div>
                    </div>
                </div>
            </div>

        </fieldset>
    </spring-form:form>

     <div class="layui-form-item" style="text-align: center">
            <button class="layui-btn" onclick="saveCarrierBalanceConfirm();">确认</button>
            <button class="layui-btn layui-btn-primary" lay-filter="cancal" onclick="closePopup(settings.fadeOutTime);">取消</button>
    </div>

</div>

</body>
</html>