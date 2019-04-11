<%--
  合同日志相关
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--------------------------------------合同附件-----------------------------------------%>
<fieldset class="layui-elem-field">
    <legend><i class="layui-icon">&#xe61d;</i>合同附件</legend>
    <yj:security
            grantRoles="ROLE_GREEN_CONTACT_SALE,ROLE_GREEN_ADMIN">
        <c:if test="${processing}">
            <a href="javascript:void(0)" onclick="openContactFilePopup();" class="layui-btn">附件备案</a>
        </c:if>
    </yj:security>
    <div class="row-fluid">
        <div class="span12">
            <div class="widget-box">

                <div class="widget-content nopadding">
                    <div class="widget-box">
                        <div class="table-responsive col-md-12">
                            <table class="layui-table">
                                <thead>
                                <tr>
                                    <th width="6%">序号</th>
                                    <th width="10%" class="th_time">上传时间</th>
                                    <th>附件名称</th>
                                    <th width="14%" class="th_com">上传者</th>
                                    <th width="15%">操作</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach var="contactFile" items="${xiaoShouContact.contactFiles}" varStatus="counter">
                                    <tr class="gradeX">
                                        <td>${counter.count}</td>
                                        <td>${contactFile.uploadTime}</td>
                                        <td>${contactFile.uploadFileName}</td>
                                        <td>${contactFile.actorManName}</td>
                                        <td align="center">
                                            <a href="${applicationHost}contact/${xiaoShouContact.contactNumber}/${contactFile.actualFileName}" target="_blank" class="layui-btn layui-btn-mini layui-btn-normal" style="margin-right:6px;vertical-align:middle;"><i class="layui-icon">&#xe625;</i>附件下载</a>
                                        </td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</fieldset>
<br/>

<%--------------------------------------充电桩建设信息-----------------------------------------%>
<fieldset class="layui-elem-field">
    <legend><i class="layui-icon">&#xe609;</i>充电桩信息</legend>
    <yj:security grantRoles="ROLE_GREEN_CONTACT_SALE,ROLE_GREEN_ADMIN">
        <c:if test="${processing}">
            <a href="javascript:void(0)" onclick="oPenChargingPopup('');" class="layui-btn">添加充电桩</a>
        </c:if>
    </yj:security>

    <div class="row-fluid">
        <div class="span12">
            <div class="widget-box">

                <div class="widget-content nopadding">
                    <div class="widget-box">
                        <div class="table-responsive col-md-12">
                            <table class="layui-table">
                                <thead>
                                <tr>
                                    <th width="10%" class="th_com">编号</th>
                                    <th class="th_com">地址</th>
                                    <th class="th_com" width="14%">操作人</th>
                                    <th class="th_com" width="8%">类型</th>
                                    <th width="8%" class="th_status">状态</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach var="charging" items="${xiaoShouContact.chargings}" varStatus="counter">
                                    <tr class="gradeX">
                                        <td>${charging.chargingNumber}</td>
                                        <td>${charging.chargingAddress}</td>
                                        <td>${charging.actorManName}</td>
                                        <td>${charging.xiaoShouContactChargingTypeName}</td>
                                        <td>${charging.xiaoShouContactChargingStatusName}</td>
                                        <%--<c:if test="${charging.chargingCanBeEdit}" >
                                            <td>${charging.chargingNumber}</td>
                                            <td>${charging.chargingAddress}</td>
                                            <td>${charging.actorManName}</td>
                                            <td>${charging.xiaoShouContactChargingTypeName}</td>
                                            <td>${charging.xiaoShouContactChargingStatusName}</td>
                                            <td style="text-align: center;">
                                                <yj:security grantRoles="ROLE_GREEN_CARRIER_RENT,ROLE_GREEN_ADMIN">
                                                    <a href="javascript:void(0);" onclick="deleteZuPinContactChargingModal('${charging.uuid}')" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini"><i class="layui-icon">&#xe640;</i>删除</a>
                                                    <a href="javascript:void(0);" onclick="oPenChargingPopup('${charging.uuid}')" class="layui-btn layui-btn-mini layui-btn-normal"><img src="${pageContext.request.contextPath}/build/images/icon2.png" width="16px" style="margin-right:6px;vertical-align:middle;"/>编辑</a>
                                                    <a href="javascript:void(0);" class="layui-btn layui-btn-mini editBtn" onclick="changeZuPinContactChargingStatus('${charging.uuid}');" style="vertical-align:middle;"><i class="layui-icon">&#xe618;</i>处理</a>
                                                </yj:security>
                                            </td>
                                        </c:if>--%>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</fieldset>
<br/>

<%----------------------------------------合同日志-----------------------------------------%>
<fieldset class="layui-elem-field">
    <legend><i class="layui-icon">&#xe637;</i>合同操作日志</legend>
    <table class="layui-table" lay-skin="line">
        <thead>
        <tr>
            <th width="6%">序号</th>
            <th width="14%">操作时间</th>
            <th>描述</th>
            <th width="25%">操作者</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="contactLog" items="${xiaoShouContact.contactLogs}" varStatus="counter">
            <tr class="gradeX">
                <td>${counter.count}</td>
                <td>${contactLog.happenTime}</td>
                <td>${contactLog.description}</td>
                <td>${contactLog.actorManName}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</fieldset>
</fieldset>


<script type="text/javascript">

    /***************合同附件部分************************************/
    function openContactFilePopup() {
        settings.source = '${pageContext.request.contextPath}/contact/xiaoshoucontactfilebeian.html?xiaoShouContactUuid=${xiaoShouContact.uuid}'
        openModalPopup(settings);
    }

    /***************充电桩处理************************************/

    //处理充电桩信息
    function changeZuPinContactChargingStatus(chargingUuid) {
        layer.confirm('请确认是否已处理该充电桩？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href='${pageContext.request.contextPath}/contact/zupincontactchargingstatus.html?chargingUuid=' + chargingUuid + '&xiaoShouContactUuid=' + '${xiaoShouContact.uuid}';
        });
    }

    //校验充电桩表单
    function saveContactChargingForm() {
        var validate = true;
        var chargingNumber = jQuery("#chargingNumber").val();
        var chargingAddress = jQuery("#chargingAddress").val();
        if (chargingNumber == null || chargingNumber == '') {
            jQuery("#chargingNumber_help").css("display","block");
            validate = false;
        } else {
            jQuery("#chargingNumber_help").css("display","none");
        }
        if (chargingAddress == null || chargingAddress == '') {
            jQuery("#chargingAddress_help").css("display","block");
            validate = false;
        } else {
            jQuery("#chargingAddress_help").css("display","none");
        }
        if (validate) {
            jQuery("#charging_form").submit();
        }
    }
    //充电桩信息编辑弹出框
    function oPenChargingPopup(chargingUuid) {
        settings.source ='${pageContext.request.contextPath}/contact/xiaoshoucontactchargingform.html?chargingUuid=' + chargingUuid + '&xiaoShouContactUuid=${xiaoShouContact.uuid}' ;
        openModalPopup(settings);
    }
    //删除充电桩信息
    function deleteZuPinContactChargingModal(chargingUuid) {
        selectContactChargingUuid = chargingUuid;
        layer.confirm('请确认是否删除该充电桩信息？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href = '${pageContext.request.contextPath}/contact/xiaoshoucontactchargingdelete.html?chargingUuid=' + selectContactChargingUuid + '&xiaoShouContactUuid=${xiaoShouContact.uuid}';
        });
    }
</script>