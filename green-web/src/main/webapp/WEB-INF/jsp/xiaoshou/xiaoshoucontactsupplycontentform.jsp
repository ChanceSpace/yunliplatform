<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>绿色共享运力平台</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<div  style="margin:20px;">

    <fieldset class="layui-elem-field">
        <legend>
            <i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;合同补充协议
        </legend>
        <div class="buttons pull-right">
            <div class="widget-content nopadding">
                <fieldset class="layui-elem-field">
                    <div class="layui-field-box">
                        <table class="site-table table-hover">
                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th width="60%" class="th_com">内容</th>
                                    <th width="20%" class="th_com">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="xiaoShouContactSupplyContents" items="${xiaoShouContactSupplyDTO.zuPinXiaoShouContactSupplyContentDTOList}"  varStatus="counter">
                                    <tr class="gradeX">
                                        <td>${counter.count}</td>
                                        <td>${xiaoShouContactSupplyContents.content}</td>

                                        <td class="center">
                                            <a href="javascript:void(0);" onclick="deleteZuPinContactSupplyModal('${xiaoShouContactSupplyContents.uuid}')" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini"><i class="layui-icon">&#xe640;</i>删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </fieldset>
            </div>
        </div>

        <spring-form:form commandName="xiaoShouContactSupplyContentDTO" id="contact_buchong_form" name="basic_validate" novalidate="novalidate" class="layui-form" method="post">
            <input type="hidden" name="xiaoShouContactUuid" value="${xiaoShouContactUuid}"/>
            <input type="hidden" name="xiaoShouContactSupplyUuid"  value="${xiaoShouContactSupplyDTO.uuid}"/>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">补充内容 </label>
                <div class="layui-input-block">
                    <spring-form:textarea path="content" maxlength="255" class="layui-textarea" rows="6" autocomplete="off" id="content"/>
                    <label class="error" id="content_help" style="display: none;color: red;">提示：请输入补充协议内容</label>
                </div>
            </div>
        </spring-form:form>

    </fieldset>


    <div class="layui-form-item">
        <div class="layui-input-inline">
            <input type="button" value="返 回" 	class="layui-btn layui-btn-normal" onclick="window.location.href='${pageContext.request.contextPath}/contact/xiaoshoucontactform.html?xiaoShouContactUuid=${xiaoShouContactUuid}'"/>
            <input type="submit" value="新 增" class="layui-btn" onclick="saveBuChongXieYi();"/>
        </div>
    </div>

</div>

<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/plugins/autocomplete/browser.js"></script>

<script type="text/javascript">

    layui.use(['laypage', 'layer','form'], function() {
    });
    layui.use(['form'], function() {
        var form = layui.form,
            layer = layui.layer;
        form.render();
    });

    function saveBuChongXieYi() {
        var validate = true;
        var content = jQuery("#content").val();
        if (content == null || content == '') {
            jQuery("#content_help").css("display","block");
            validate = false;
        } else {
            jQuery("#content_help").css("display","none");
        }
        if (validate) {
            jQuery("#contact_buchong_form").submit();
        }
    }

    function deleteZuPinContactSupplyModal(contactSupplyContentUuid) {
        layer.confirm('请确认是否删除该补充协议条款？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href = '${pageContext.request.contextPath}/contact/xiaoshoucontactsupplycontentdelete.html?contactSupplyContentUuid=' + contactSupplyContentUuid + '&xiaoShouContactUuid=${xiaoShouContactUuid}&contctSupplyId=${xiaoShouContactSupplyDTO.uuid}';
       });
    }

</script>
</body>
</html>
