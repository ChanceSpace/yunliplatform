<%--附加页面--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="layui-form-item">
    <label class="layui-form-label"> 其他费用(元)</label><span id="qitaFeeError" class="text-danger" style="display: none;color: red"></span>
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
            <th width="20%">金额(元)</th>
            <th width="12%">支付方式</th>
            <th>备注</th>
            <th width="12%">操作</th>
        </tr>
        </thead>
        <tbody class="tbody">
        </tbody>
    </table>
</div>


