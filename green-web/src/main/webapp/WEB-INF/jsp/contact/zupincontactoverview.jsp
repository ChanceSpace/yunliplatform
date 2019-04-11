<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="yj" uri="http://www.rajaev.com" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>绿色共享运力平台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/datepicker/datepicker3.css">
</head>
<body>

<div class="admin-main">
    <fieldset class="fieldset_box min_fieldset">
    <blockquote class="layui-elem-quote">
        <form class="layui-form"  id="visit_search_form"  action="${pageContext.request.contextPath}/contact/zupincontactoverview.html" method="POST">
            <input type="hidden" name="init" value="true"/>

                <div class="layui-input-inline">
                    <label class="layui-form-label search-form-label">合同时间范围</label>
                    <div class="layui-input-inline">
                      <input type="text" name="startTime" class="layui-input" id="datepicker1" value="${paging.startTime}"/>
                    </div>
                </div>

                <div class="layui-input-inline">
                    <label>—</label>
                    <div class="layui-input-inline">
                        <input type="text" name="endTime" class="layui-input" id="datepicker2" value="${paging.endTime}"/>
                    </div>
                </div>

                <div class="layui-input-inline">
                    <label class="layui-form-label search-form-label">合同状态筛选</label>
                    <div class="layui-input-inline">
                        <select name="contactStatus" class="form-control">
                            <option value="all" <c:if test="${paging.contactStatus == 'all'}">selected="true"</c:if>>全部</option>
                            <option value="S_CREATED" <c:if test="${paging.contactStatus == 'S_CREATED'}">selected="true"</c:if>>创建，等待合同上报</option>
                            <option value="S_PROCESSING" <c:if test="${paging.contactStatus== 'S_PROCESSING'}">selected="true"</c:if>>合同执行中</option>
                            <option value="S_ENDBUTNOTOVER" <c:if test="${paging.contactStatus== 'S_ENDBUTNOTOVER'}">selected="true"</c:if>>合同到期未结束</option>
                            <option value="S_FINISHED" <c:if test="${paging.contactStatus== 'S_FINISHED'}">selected="true"</c:if>>合同结束</option>
                        </select>
                    </div>
                </div>


                <div class="layui-input-inline">
                    <label class="layui-form-label search-form-label">关键字</label>
                    <div class="layui-input-inline">
                        <input type="text" id="keyWords" name="keyWords" class="layui-input" value="${paging.keyWords}" placeholder="合同号、乙方名称或车牌号" style="width: 300px;">
                    </div>
                </div>
                <div class="layui-input-inline">
                    <a href="javascript:void(0);" onclick="searchContactInfo();" class="layui-btn layui-btn-small layui-btn-normal" id="search">
                        <i class="layui-icon">&#xe615;</i> 搜索
                    </a>

                    <yj:security grantRoles="ROLE_GREEN_CARRIER_RENT,ROLE_GREEN_ADMIN">
                        <a href="${pageContext.request.contextPath}/contact/zupincontactform.html" class="layui-btn layui-btn-small " id="add">
                            <i class="layui-icon">&#xe608;</i> 新增合同
                        </a>
                    </yj:security>
                </div>
        </form>
    </blockquote>
    <%--TITLE部分--%>
        <fieldset class="layui-elem-field min_fieldset">
        <legend>数据列表</legend>
        <div class="layui-field-box">
            <table class="site-table table-hover table_box">
                <thead>
                    <th width="5%">序号</th>
                    <th class="th_time" style="cursor: pointer;" onclick="changeSortBy('createTime')">
                        <yj:sortTag name="创建时间" field="createTime" sortBy="${paging.sortBy}" sortWay="${paging.sortWay}" ascImgUrl="${pageContext.request.contextPath}/build/images/arrow_up.gif" descImgUrl="${pageContext.request.contextPath}/build/images/arrow_down.gif"/>
                    </th>
                    <th class="th_com"  style="cursor: pointer;" onclick="changeSortBy('contactNumber')">
                        <yj:sortTag name="合同编号" field="contactNumber" sortBy="${paging.sortBy}" sortWay="${paging.sortWay}" ascImgUrl="${pageContext.request.contextPath}/build/images/arrow_up.gif" descImgUrl="${pageContext.request.contextPath}/build/images/arrow_down.gif"/>
                    </th>
                    <th class="th_name" style="cursor: pointer;" onclick="changeSortBy('yiFangName')">
                        <yj:sortTag name="乙方" field="yiFangName" sortBy="${paging.sortBy}" sortWay="${paging.sortWay}" ascImgUrl="${pageContext.request.contextPath}/build/images/arrow_up.gif" descImgUrl="${pageContext.request.contextPath}/build/images/arrow_down.gif"/>
                    </th>
                    <th class="th_price" style="cursor: pointer;" onclick="changeSortBy('currentBalance')">
                        <yj:sortTag name="已缴纳租金" field="currentBalance" sortBy="${paging.sortBy}" sortWay="${paging.sortWay}" ascImgUrl="${pageContext.request.contextPath}/build/images/arrow_up.gif" descImgUrl="${pageContext.request.contextPath}/build/images/arrow_down.gif"/>
                    </th>
                    <th class="th_price" style="cursor: pointer;" onclick="changeSortBy('actualYaJinFee')">
                        <yj:sortTag name="已缴纳押金" field="actualYaJinFee" sortBy="${paging.sortBy}" sortWay="${paging.sortWay}" ascImgUrl="${pageContext.request.contextPath}/build/images/arrow_up.gif" descImgUrl="${pageContext.request.contextPath}/build/images/arrow_down.gif"/>
                    </th>
                    <th class="th_time">开始时间</th>
                    <th class="th_time" >结束时间</th>
                    <th class="th_status">状态</th>
                    <th class="th_sale">销售</th>
                    <th class="th_time">操作</th>
                </thead>
                <tbody id="strtegyTable">
                <c:forEach items="${contacts}" var="line" varStatus="counter">
                    <tr class="gradeX">
                        <td>${counter.count}</td>
                        <td>${line.createTime}</td>
                        <td>${line.contactNumber}</td>
                        <td> ${line.yiFangName}</td>
                        <td>
                            ${line.zujinHasPay}
                        </td>
                        <td>
                            ${line.yajinHasPay}
                        </td>
                        <td>
                            <c:if test="${line.beginExecute}">
                                ${line.startDate}
                            </c:if>
                            <c:if test="${!line.beginExecute}">
                                未开始,还未提车
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${line.beginExecute}">
                                <c:if test="${line.endExecute}">
                                    ${line.endDate}
                                </c:if>
                                <c:if test="${!line.endExecute}">
                                    还未提车完毕
                                </c:if>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${line.contactStatus == 'S_CREATED'}">
                                <span class="bg_999999"> ${line.contactStatusName} </span>
                            </c:if>
                            <c:if test="${line.contactStatus == 'S_PROCESSING'}">
                                <span class="bg_009E94"> ${line.contactStatusName} </span>
                            </c:if>
                            <c:if test="${line.contactStatus == 'S_FINISHED'}">
                                <span class="bg_33a7fd"> ${line.contactStatusName} </span>
                            </c:if>
                            <c:if test="${line.contactStatus == 'S_ENDBUTNOTOVER'}">
                                <span class="bg_E60012"> ${line.contactStatusName} </span>
                            </c:if>
                        </td>

                        <td>${line.saleManName}</td>
                        <td class="center">
                            <c:if test="${line.contactStatus == 'S_CREATED'}">
                                <a href="javascript:void(0);" class="layui-btn layui-btn-danger layui-btn-mini" onclick="openContactDeleteModal('${line.uuid}');" style="margin-right:6px;vertical-align:middle;"><i class="layui-icon">&#xe640;</i>删除</a>
                                <c:if test="${line.canBeExecute}"><a href="javascript:void(0);" class="layui-btn layui-btn-mini editBtn" onclick="openContactCheckModal('${line.uuid}', 'S_PROCESSING');" style="margin-right:6px;vertical-align:middle;"><i class="layui-icon">&#xe618;</i>执行</a></c:if>
                                <a href="${pageContext.request.contextPath}/contact/zupincontactform.html?zuPinContactUuid=${line.uuid}" class="layui-btn layui-btn-mini layui-btn-normal" style="margin-right:6px;vertical-align:middle;"><i class="layui-icon">&#xe63c;</i>编辑</a>
                            </c:if>
                            <c:if test="${line.contactStatus == 'S_PROCESSING'}">
                                <yj:security grantRoles="ROLE_GREEN_ADMIN">
                                    <a href="javascript:void(0);" class="layui-btn layui-btn-danger layui-btn-mini" onclick="openContactBackModal('${line.uuid}', 'S_CREATED');" style="margin-right:6px;vertical-align:middle;"><i class="layui-icon">&#xe65c;</i>撤销</a>
                                </yj:security>
                            </c:if>
                            <c:if test="${line.contactStatus != 'S_CREATED'}">
                                <a href="${pageContext.request.contextPath}/contact/zupincontactpdfdownload.html?zuPinContactUuid=${line.uuid}" class="layui-btn layui-btn-mini layui-btn-warm" style="margin-right:6px;vertical-align:middle;"><i class="layui-icon">&#xe625;</i>下载</a>
                            </c:if>
                            <a href="${pageContext.request.contextPath}/contact/zupincontactdetails.html?zuPinContactUuid=${line.uuid}" class="layui-btn layui-btn-mini layui-btn-normal" style="margin-right:6px;vertical-align:middle;"><i class="layui-icon">&#xe62a;</i>详情</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>


        </div>
            <div class="admin-table-page">
                <div id="page" class="page">
                    <yj:paging urlMapping="${pageContext.request.contextPath}/contact/zupincontactoverview.html" paging="${paging}"/>
                </div>
            </div>
    </fieldset>
    </fieldset>
</div>

<!-- jQuery 2.2.0 -->
<script src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/popup/modal.popup.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${pageContext.request.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>


<script type="text/javascript">
    var sortBy = '${paging.sortBy}';
    var sortWay = '${paging.sortWay}';
    var selectZuPinContactUuid;
    var selectChangeStatus;

    var form;
    layui.config({
        base: '${pageContext.request.contextPath}/plugins/layui/modules/'
    });
    layui.use(['laypage', 'layer','laydate','form'], function() {
        form = layui.form
    });
    layui.use(['form'], function() {
        var form = layui.form,
        layer = layui.layer;
    });

    layui.use(['laydate'],function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#datepicker1'
            ,value: '${paging.startTime}'
        });
        laydate.render({
            elem: '#datepicker2'
            ,value: '${paging.endTime}'
        });
    });

    function searchContactInfo() {
        jQuery('#visit_search_form').submit();
    }

    jQuery(document).on('keydown', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
            searchContactInfo();
        }
    });

    function changeSortBy(anotherSort) {
        if (anotherSort == sortBy) {
            if(sortWay == 'desc') {
                sortWay = 'asc';
            } else {
                sortWay = 'desc';
            }
        } else {
            sortBy = anotherSort;
            sortWay = 'desc';
        }
        window.location.href = '${pageContext.request.contextPath}/contact/zupincontactoverview.html?addSort=true&sortBy=' + sortBy + '&sortWay=' + sortWay;
    }

    /*********************************************合同状态部分**************************************************/

    function openContactCheckModal(zuPinContactUuid, changeStatus) {
        selectZuPinContactUuid = zuPinContactUuid;
        selectChangeStatus = changeStatus;
        layer.confirm('请确认是否开始执行该合同？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href = '${pageContext.request.contextPath}/contact/zupincontactcheck.html?zuPinContactUuid=' + selectZuPinContactUuid + "&changeStatus=" + selectChangeStatus;
       });
    }

    function openContactBackModal(zuPinContactUuid, changeStatus) {
        selectZuPinContactUuid = zuPinContactUuid;
        selectChangeStatus = changeStatus;
        layer.confirm('请确认是否撤销该合同？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href = '${pageContext.request.contextPath}/contact/zupincontactcheck.html?zuPinContactUuid=' + selectZuPinContactUuid + "&changeStatus=" + selectChangeStatus;
       });
    }

    function openContactDeleteModal(zuPinContactUuid) {
        selectZuPinContactUuid = zuPinContactUuid;
        layer.confirm('请确认是否删除该合同？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
            layer.close(index);
            window.location.href = '${pageContext.request.contextPath}/contact/zupincontactdelete.html?zuPinContactUuid=' + selectZuPinContactUuid;
       });
    }

</script>

</body>
</html>

