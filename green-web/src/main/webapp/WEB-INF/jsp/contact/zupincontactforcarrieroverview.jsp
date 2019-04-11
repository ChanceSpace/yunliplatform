<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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
<fieldset class="fieldset_box min_fieldset">
<div class="admin-main">
    <blockquote class="layui-elem-quote">
        <form class="layui-form"  id="visit_search_form"  action="${pageContext.request.contextPath}/carrier/zupincontactcarrieroverview.html" method="POST">
            <input type="hidden" name="init" value="true"/>
            <div class="row">
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
                    <label class="layui-form-label search-form-label">关键字</label>
                    <div class="layui-input-inline">
                        <input type="text" id="keyWords" name="keyWords" class="layui-input" value="${paging.keyWords}" placeholder="合同号或车牌号">
                    </div>
                </div>

                <a href="javascript:void(0);" onclick="searchContactInfo();" class="layui-btn layui-btn-small" id="search">
                    <i class="layui-icon">&#xe615;</i> 搜索
                </a>

            </div>
        </form>
    </blockquote>

    <%--TITLE部分--%>
    <fieldset class="layui-elem-field min_fieldset">
        <legend>数据列表</legend>
        <div class="layui-field-box">
            <table class="site-table table-hover">
                <thead>
                    <th width="5%">序号</th>
                    <th style="cursor: pointer;" onclick="changeSortBy('createTime')">
                        <yj:sortTag name="创建时间" field="createTime" sortBy="${paging.sortBy}" sortWay="${paging.sortWay}" ascImgUrl="${pageContext.request.contextPath}/build/images/arrow_up.gif" descImgUrl="${pageContext.request.contextPath}/build/images/arrow_down.gif"/>
                    </th>
                    <th  style="cursor: pointer;" onclick="changeSortBy('contactNumber')">
                        <yj:sortTag name="合同编号" field="contactNumber" sortBy="${paging.sortBy}" sortWay="${paging.sortWay}" ascImgUrl="${pageContext.request.contextPath}/build/images/arrow_up.gif" descImgUrl="${pageContext.request.contextPath}/build/images/arrow_down.gif"/>
                    </th>
                    <th>甲方</th>
                     <th style="cursor: pointer;" onclick="changeSortBy('zujinHasPay')">
                        <yj:sortTag name="已缴纳租金" field="zujinHasPay" sortBy="${paging.sortBy}" sortWay="${paging.sortWay}" ascImgUrl="${pageContext.request.contextPath}/build/images/arrow_up.gif" descImgUrl="${pageContext.request.contextPath}/build/images/arrow_down.gif"/>
                    </th>
                    <th style="cursor: pointer;" onclick="changeSortBy('yajinHasPay')">
                        <yj:sortTag name="已缴纳押金" field="yajinHasPay" sortBy="${paging.sortBy}" sortWay="${paging.sortWay}" ascImgUrl="${pageContext.request.contextPath}/build/images/arrow_up.gif" descImgUrl="${pageContext.request.contextPath}/build/images/arrow_down.gif"/>
                    </th>
                    <th >开始时间</th>
                    <th >结束时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </thead>
                <tbody id="strtegyTable">
                <c:forEach items="${contacts}" var="line" varStatus="counter">
                    <tr class="gradeX">
                        <td>${counter.count}</td>
                        <td>${line.createTime}</td>
                        <td>${line.contactNumber}</td>
                        <td> ${line.jiaFangName}</td>
                        <td>
                             <c:if test="${line.zujinHasPay <0}">
                                <font color="red">${line.zujinHasPay}</font>
                            </c:if>
                            <c:if test="${line.zujinHasPay >=0}">
                                <font color="green">${line.zujinHasPay}</font>
                            </c:if>
                        </td>
                        <td>
                             <c:if test="${line.yajinHasPay <0}">
                                <font color="red">${line.yajinHasPay}</font>
                            </c:if>
                            <c:if test="${line.yajinHasPay >=0}">
                                <font color="green">${line.yajinHasPay}</font>
                            </c:if>
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

                        <td class="center">
                            <a href="${pageContext.request.contextPath}/contact/zupincontactdetails.html?zuPinContactUuid=${line.uuid}"
                               class="layui-btn layui-btn-mini layui-btn-normal" style="margin-right:6px;vertical-align:middle;"><i class="layui-icon">&#xe62a;</i>详情</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="admin-table-page">
            <div id="page" class="page">
                <yj:paging urlMapping="${pageContext.request.contextPath}/carrier/zupincontactcarrieroverview.html" paging="${paging}"/>
            </div>
        </div>

    </fieldset>
</div>
</fieldset>
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
        window.location.href = '${pageContext.request.contextPath}/carrier/zupincontactcarrieroverview.html?addSort=true&sortBy=' + sortBy + '&sortWay=' + sortWay;
    }

</script>

</body>
</html>

