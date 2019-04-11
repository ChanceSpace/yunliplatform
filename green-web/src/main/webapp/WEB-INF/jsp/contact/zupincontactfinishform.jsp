<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<body>

<style>
    .detailCon tr th,.detailCon tr td{text-align: center;}
    .detailCon tr td input{width:100%}
   #finishCharge {display: block;
        border: 1px solid #ccc;
        border-radius: 4px;
        padding: 4px 10px;}

</style>
<div>

                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;合同结束</legend>
                        <form action="${pageContext.request.contextPath}/contact/zupincontactfinishform.html" id="zupincontactoverform" name="basic_validate" novalidate="novalidate" class="layui-form" method="post">
                            <div class="layui-field-box">
                                <input type="hidden" name="method" value="save"/>
                                <input type="hidden" name="zuPinContactUuid" value="${zuPinContact.uuid}"/>

                                <div class="layui-form-item">
                                    <c:if test="${houFuFee > 0}">
                                        <label class="layui-form-label">合同后付欠费租金 [备注：应收取]</label></span>
                                        <div class="layui-input-block">
                                            <input maxlength="10" id="contactOverHouFuFee" name="houFuFee"  class="layui-input" value="${houFuFee}" style="color:#FF0000;"  readonly="true"/>
                                        </div>
                                    </c:if>
                                </div>

                                <div class="layui-form-item">
                                    <c:if test="${zuPinContact.yajinHasPay > 0}">
                                        <label class="layui-form-label">  合同已收押金 [备注：应退回]</label>
                                        <div class="layui-input-block">
                                            <input maxlength="10" class="layui-input" value="${zuPinContact.yajinHasPay}" style="color:#009E94;" readonly="true"/>
                                        </div>
                                    </c:if>
                                    <c:if test="${zuPinContact.yajinHasPay < 0}">
                                        <label class="layui-form-label">  合同已收押金 [备注：应收取]</label>
                                        <div class="layui-input-block">
                                            <input maxlength="10"  class="layui-input" value="${-zuPinContact.yajinHasPay}" style="color:#FF0000;" readonly="true"/>
                                        </div>
                                    </c:if>
                                </div>

                                <div class="layui-form-item">
                                    <label class="layui-form-label"> 其他费用 [必填]</label><span id="qitaFeeError" class="text-danger" style="display: none;color: red"></span>
                                    <div class="layui-input-block">
                                        <input type="text" value="表单内容" class="layui-input" readonly="readonly" style="width:40%;display: inline-block;">
                                        <i id="addContent" class=" layui-icon" style="font-size:22px;vertical-align: middle;cursor:pointer;color:#009F95;" onclick="addContentToFinishForm()">&#xe608;</i>
                                    </div>
                                </div>
                                <div class="detailCon layui-form-item" style="display: none;">
                                        <table class="layui-table" style="width:100%;">
                                            <thead>
                                            <tr>
                                                <th width="8%">序号</th>
                                                <th width="20%">金额</th>
                                                <th width="12%">支付方式</th>
                                                <th>备注</th>
                                                <th width="12%">操作</th>
                                            </tr>
                                            </thead>
                                            <tbody class="tbody">
                                            </tbody>
                                        </table>
                                </div>

                                <div class="layui-form-item">
                                    <label class="layui-form-label">结束日期 [必填]</label>  <span id="finishDate_help" class="text-danger" style="display: none;color: red">提示:结束日期不能为空</span>
                                    <div class="layui-input-block">
                                        <input id="finishDate" name="finishDate" data-date-format="yyyy-mm-dd"  class="datepicker layui-input" readonly="true" value="${today}"/>
                                            <c:if test="${zuPinContactRepayType =='C_AFTER'}">
                                                  <span class="layui-btn" onclick="contactOverFormFeeChange()">根据现有合同结束日期重新计算合同后付欠费租金</span>
                                            </c:if>
                                    </div>
                                </div>
                                <br>
                            </div>
                        </form>
                        <div align="center">
                            &nbsp;
                            <input type="button" value="确  认" 	class="layui-btn" onclick="saveZuPinContactFinish()"/>
                            <button class="layui-btn layui-btn-primary" lay-filter="cancal" onclick="closePopup(settings.fadeOutTime);">取消</button>
                        </div>
                </fieldset>
</div>

</body>
</html>
