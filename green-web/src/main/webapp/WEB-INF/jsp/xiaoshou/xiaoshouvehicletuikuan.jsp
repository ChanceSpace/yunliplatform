<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<body>
<div style="margin:30px 0 30px ">
    <form action="${pageContext.request.contextPath}/contact/overxiaoshoucontactvehicle.html" id="tuikuanForm" name="basic_validate" novalidate="novalidate" class="layui-form" method="post">
        <input type="hidden" value="${xiaoShouContact.uuid}" name="contactUuid">
        <input type="hidden" value="${vehicleNumber}" name="vehicleNumber">
        <c:if test="${xiaoShouContact.xiaoShouType == 'XS_QK'}">
            <td style="vertical-align: top">
                销售合计：${selectModule.saleNumber}辆
            </td>
            <td style="vertical-align: top">
                全款
            </td>

            <td style="vertical-align: top">
                实际销售价格/台：${selectModule.salePrice} &nbsp;&nbsp;定金/台：${selectModule.dingJin}&nbsp;&nbsp;尾款（扣除定金）/台：${selectModule.weiKuan}
            </td>
        </c:if>
        <c:if test="${xiaoShouContact.xiaoShouType == 'XS_ANJIE'}">
            <td style="vertical-align: top">
                    ${selectModule.saleNumber}辆
            </td>
            <td style="vertical-align: top">
                按揭
            </td>
            <td style="vertical-align: top">
                实际销售价格/台：${selectModule.salePrice} &nbsp;&nbsp;定金/台：${selectModule.dingJin}&nbsp;&nbsp;首付（扣除定金）/台：${selectModule.shouFu}&nbsp;&nbsp;尾款/台：${selectModule.weiKuan}&nbsp;&nbsp;最大贷款年限：${selectModule.maxAnJieYear}
            </td>
        </c:if>
        其他扣费项目
        <input type="text" onkeyup="num(this)" onbeforepaste="num(this)" name="otherFee">
        <input type="submit" value="提交">
    </form>
</div>
<script>

    function num(obj) {
        obj.value = obj.value.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
        obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字
        obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个, 清除多余的
        obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
    }
</script>
</body>
</html>
