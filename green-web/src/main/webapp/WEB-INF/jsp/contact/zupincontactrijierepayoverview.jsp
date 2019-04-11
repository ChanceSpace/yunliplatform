<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--<%@ taglib prefix="kf" uri="http://www.yj.com" %>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>

<body>
<div style="height: 600px; overflow-y: auto">

        <%--日结方式合同结束附加费用--%>
        <c:if test="${fn:length(additionhistorys)>0}">
            <fieldset class="layui-elem-field">
                <legend><i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;合同结束其他款项&nbsp;&nbsp;</legend>
                <table class="layui-table">
                    <thead>
                    <tr>
                        <th width="15%">类型</th>
                        <th width="12%">创建时间</th>
                        <th width="15%">金额</th>
                        <th width="12%">操作者</th>
                        <th width="30%">描述</th>
                        <th width="13">状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach  var="line" items="${additionhistorys}">
                        <tr class="gradeX">
                            <td>${line.zuPinContactRepayLocation.description}</td>
                            <td>${line.actualFeeDate}</td>
                            <td>
                                <c:if test="${line.feeTotalInshow < 0}">
                                    <font color="green"> ${line.feeTotalInshow}</font>
                                </c:if>
                                <c:if test="${line.feeTotalInshow > 0}">
                                    <font color="red"> ${line.feeTotalInshow}</font>
                                </c:if>
                            </td>
                            <td>${line.jiaoYiRen}</td>
                            <td title="${line.comment}">${line.comment}</td>
                            <td>
                                <c:if test="${line.status == 'O_FINSIH'}">
                                    <font color="green"> ${line.status.description}</font>
                                </c:if>
                                <c:if test="${line.status == 'O_ABANDON'}">
                                    <font color="green"> ${line.status.description}</font>
                                </c:if>
                                <c:if test="${line.status == 'O_CREATE'}">
                                    <font color="red"> ${line.status.description}</font>
                                </c:if>
                            </td>
                            <c:set var="groupTotal" value="${groupTotal + line.feeTotalInshow }"/>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </fieldset>
        </c:if>


        <%--日结车辆部分  押金和租金部分--%>
        <c:set var="groupTotal" value="0"/>
        <c:forEach var="history" items="${historys}">
         <c:set var="groupTotal" value="0"/>
            <fieldset class="layui-elem-field">
                <legend><i class="layui-icon" style="color:#009900;font-size:22px;">&#xe63c;</i>&nbsp;账单明细&nbsp;&nbsp;${history.key}</legend>
                <table class="layui-table">
                    <thead>
                        <tr>
                            <th width="10%">类型</th>
                            <th width="10%">车牌号</th>
                            <th width="12%">提车时间</th>
                            <th width="12%">结束时间</th>
                            <th width="13%">金额</th>
                            <th width="10%">操作者</th>
                            <th width="20%">描述</th>
                            <th width="16%">状态</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr class="gradeX">
                            <td colspan="8"><i class="fa fa-money"></i>&nbsp;<b>租金部分</b></td>
                        </tr>
                        <c:forEach var="line" items="${history.value.addZuJinFees}" varStatus="counter">
                            <tr class="gradeX">
                                <td>${line.zuPinContactRepayLocation.description}</td>
                                <td>${line.vehicleNumber}</td>
                                <td>${line.actualFeeDate}</td>
                                <td>${line.actualFeeDateEnd}</td>
                                <td>
                                    <c:if test="${line.feeTotalInshow < 0}">
                                        <font color="green"> ${line.feeTotalInshow}</font>
                                    </c:if>
                                    <c:if test="${line.feeTotalInshow > 0}">
                                        <font color="red"> ${line.feeTotalInshow}</font>
                                    </c:if>
                                </td>
                                <td>${line.jiaoYiRen}</td>
                                <td title="${line.comment}">${line.comment}</td>
                                <td>
                                    <c:if test="${line.status == 'O_FINSIH'}">
                                        <font color="green"> ${line.status.description}</font>
                                    </c:if>
                                    <c:if test="${line.status == 'O_ABANDON'}">
                                        <font color="green"> ${line.status.description}</font>
                                    </c:if>
                                    <c:if test="${line.status == 'O_CREATE'}">
                                        <font color="red"> ${line.status.description}</font>
                                    </c:if>
                                </td>
                                <c:set var="groupTotal" value="${groupTotal + line.feeTotalInshow }"/>
                            </tr>
                        </c:forEach>
                        <c:forEach var="line" items="${history.value.subZuJinFees}" varStatus="counter">
                            <tr class="gradeX">
                                <td>${line.zuPinContactRepayLocation.description}</td>
                                <td>${line.vehicleNumber}</td>
                                <td>${line.actualFeeDate}</td>
                                <td>${line.actualFeeDateEnd}</td>
                                <td>
                                    <c:if test="${line.feeTotalInshow < 0}">
                                        <font color="green"> ${line.feeTotalInshow}</font>
                                    </c:if>
                                    <c:if test="${line.feeTotalInshow > 0}">
                                        <font color="red"> ${line.feeTotalInshow}</font>
                                    </c:if>
                                </td>
                                <td>${line.jiaoYiRen}</td>
                                <td title="${line.comment}">${line.comment}</td>
                                <td>
                                    <c:if test="${line.status == 'O_FINSIH'}">
                                        <font color="green"> ${line.status.description}</font>
                                    </c:if>
                                    <c:if test="${line.status == 'O_ABANDON'}">
                                        <font color="green"> ${line.status.description}</font>
                                    </c:if>
                                    <c:if test="${line.status == 'O_CREATE'}">
                                        <font color="red"> ${line.status.description}</font>
                                    </c:if>
                                </td>
                                <c:set var="groupTotal" value="${groupTotal + line.feeTotalInshow}"/>
                            </tr>
                        </c:forEach>

                        <tr class="gradeX">
                            <td colspan="8"><i class="fa fa-money"></i>&nbsp;<b>押金部分</b></td>
                        </tr>
                        <c:forEach var="line" items="${history.value.addYaJinFees}" varStatus="counter">
                            <tr class="gradeX">
                                <td>${line.zuPinContactRepayLocation.description}</td>
                                <td>${line.vehicleNumber}</td>
                                <td>${line.actualFeeDate}</td>
                                <td><%--${line.actualFeeDateEnd}--%></td>
                                <td>
                                    <c:if test="${line.feeTotalInshow < 0}">
                                        <font color="green"> ${line.feeTotalInshow}</font>
                                    </c:if>
                                    <c:if test="${line.feeTotalInshow > 0}">
                                        <font color="red"> ${line.feeTotalInshow}</font>
                                    </c:if>
                                </td>
                                <td>${line.jiaoYiRen}</td>
                                <td title="${line.comment}">${line.comment}</td>
                                <td>
                                    <c:if test="${line.status == 'O_FINSIH'}">
                                        <font color="green"> ${line.status.description}</font>
                                    </c:if>
                                    <c:if test="${line.status == 'O_ABANDON'}">
                                        <font color="green"> ${line.status.description}</font>
                                    </c:if>
                                    <c:if test="${line.status == 'O_CREATE'}">
                                        <font color="red"> ${line.status.description}</font>
                                    </c:if>
                                </td>
                                <c:set var="groupTotal" value="${groupTotal + line.feeTotalInshow }"/>
                            </tr>
                        </c:forEach>
                        <c:forEach var="line" items="${history.value.subYaJinFees}" varStatus="counter">
                            <tr class="gradeX">
                                <td>${line.zuPinContactRepayLocation.description}</td>
                                <td>${line.vehicleNumber}</td>
                                <td>${line.actualFeeDate}</td>
                                <td><%--${line.actualFeeDateEnd}--%></td>
                                <td>
                                    <c:if test="${line.feeTotalInshow < 0}">
                                        <font color="green"> ${line.feeTotalInshow}</font>
                                    </c:if>
                                    <c:if test="${line.feeTotalInshow > 0}">
                                        <font color="red"> ${line.feeTotalInshow}</font>
                                    </c:if>
                                </td>
                                <td>${line.jiaoYiRen}</td>
                                <td title="${line.comment}">${line.comment}</td>
                                <td>
                                    <c:if test="${line.status == 'O_FINSIH'}">
                                        <font color="green"> ${line.status.description}</font>
                                    </c:if>
                                    <c:if test="${line.status == 'O_ABANDON'}">
                                        <font color="green"> ${line.status.description}</font>
                                    </c:if>
                                    <c:if test="${line.status == 'O_CREATE'}">
                                        <font color="red"> ${line.status.description}</font>
                                    </c:if>
                                </td>
                                <c:set var="groupTotal" value="${groupTotal + line.feeTotalInshow }"/>
                            </tr>
                        </c:forEach>
                        <tr class="gradeX">
                            <td colspan="2">
                                <b>
                                合计：
                                </b>
                            </td>
                            <td colspan="6">
                                <b>
                                <c:if test="${groupTotal < 0}">
                                    <font color="green"> ${groupTotal}元</font>
                                </c:if>
                                <c:if test="${groupTotal >= 0}">
                                    <font color="red"> ${groupTotal}元</font>
                                </c:if>
                                </b>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </fieldset>
        </c:forEach>

        <div align="center">
            <button class="layui-btn" onclick="closePopup(settings.fadeOutTime)">确定</button>
        </div>
</div>

</body>
</html>