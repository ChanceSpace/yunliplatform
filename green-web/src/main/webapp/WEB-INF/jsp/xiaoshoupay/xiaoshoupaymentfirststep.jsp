<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="yj" uri="http://www.rajaev.com" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>绿色共享运力平台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/autocomplete/jquery.autocomplete.css" />
</head>

<div class="payStyle_container">
    <p class="title">绿色共享运力平台收银台</p>

    <div class="nav_box">
        <div class="nav_left">
            <img src="${pageContext.request.contextPath}/build/images/sure.png" width="80" />
        </div>
        <div class="nav_right">
            <c:if test="${totalFee>=0}">
                <p>您正在缴费<span class="pay_price">${totalFee}</span>，请确认！</p>
                <p style="margin-top:10px;">用户：<span class="font-FFB800">${chengYun.name}</span></p>
            </c:if>
            <c:if test="${totalFee<=0}">
                <p>您正在退款<span class="pay_price">${totalFee}</span>，请确认！</p>
                <p style="margin-top:10px;">用户：<span class="font-FFB800">${chengYun.name}</span></p>
            </c:if>
        </div>
    </div>

    <div class="pay_con">
        <div class="subtitle">请选择支付方式</div>
        <div class="layui-row">

            <%--付款流程--%>
            <c:if test="${totalFee >= 0}">
                <div class="layui-col-md3">
                    <div class="online_list active" data-id="O_AILI">
                        <img src="${pageContext.request.contextPath}/build/images/zhifubao.png" width="120" />
                    </div>
                </div>
                <div class="layui-col-md3">
                    <div class="online_list" data-id="O_WEBCHART">
                        <img src="${pageContext.request.contextPath}/build/images/weixin.png" width="120" />
                    </div>
                </div>
                <c:if test="${totalFee <= chengYun.currentBalance}">
                    <div class="layui-col-md3">
                        <div class="online_list yue_pay" data-id="O_OFFLINE">
                            <img src="${pageContext.request.contextPath}/build/images/yue.png" width="120" />
                            <div class="yue">账户余额
                                <P>￥${chengYun.currentBalance}</P>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${totalFee > chengYun.currentBalance}">
                    <div class="layui-col-md3">
                        <div class="online_list yue_pay1" data-id="O_OFFLINE">
                            <img src="${pageContext.request.contextPath}/build/images/yue.png" width="120" />
                            <div class="yue">账户余额不足
                                <P>￥${chengYun.currentBalance}</P>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:if>

             <%--退款流程--%>
            <c:if test="${totalFee < 0}">
                <div class="layui-col-md3">
                    <div class="online_list yue_pay active" data-id="O_OFFLINE">
                        <img src="${pageContext.request.contextPath}/build/images/yue.png" width="120" />
                        <div class="yue">账户余额
                            <P>￥${chengYun.currentBalance}</P>
                        </div>
                    </div>
                </div>
            </c:if>

        </div>

        <form id="payment_form" class="lineDown layui-form" action="${pageContext.request.contextPath}/carrier/xiaoshoupaymentonlinefirst.html" <c:if test="${totalFee >= 0}"> style="display:none;" </c:if> method="post" enctype="multipart/form-data">
            <input type="hidden" name="payOrderNumber" value="${payOrderNumber}"/>
            <input type="hidden" name="itemUuids" value="${itemUuids}"/>
            <input type="hidden" name="totalFee" value="${totalFee}"/>
            <input type="hidden" name="chengYunUuid" value="${chengYun.uuid}"/>
            <input type="hidden" name="actorUuid" value="${actorUuid}"/>

            <c:if test="${totalFee >= 0}">
                <input type="hidden" id="orderWay" name="orderWay" value="O_AILI"/>
            </c:if>
            <c:if test="${totalFee < 0}">
                <input type="hidden" id="orderWay" name="orderWay" value="O_OFFLINE"/>
            </c:if>

            <div class="layui-form-item">
                <label class="layui-form-label">单据编号</label>
                <div class="layui-input-block">
                     <input type="text" id="jiaoYiNumber" name="jiaoYiNumber" placeholder="请输入单据编号" class="layui-input">
                    <label id="jiaoYiNumber_help" class="error" style="display: none; color: red;">提示:单据编号信息不能为空</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">单据文件</label>
                <div class="layui-input-block">
                    <div class="files_style" style="border:1px solid #e6e6e6;">
                        <div class="label_file" style="margin:4px 10px;">选择文件</div>
                        <input type="file" id="fuJians" name="fuJians" multiple="true" class="layui-input" onchange="filesFun(this,files)" style="left:10px;top:4px;"/>
                        <div id="previewPic" style="left:100px;top:9px;">请选择文件</div>
                    </div>
                    <label id="fuJians_help" class="error" style="display: none; color: red;">提示:单据文件不能为空</label>
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block">
                    <textarea placeholder="请输入备注" name="orderNote" class="layui-textarea"></textarea>
                </div>
            </div>
        </form>

        <div class="pay_btn">
            <c:if test="${totalFee >= 0}">
                <button class="layui-btn layui-btn-normal layui-btn-big" onclick="beginToPay();">立即支付</button>
            </c:if>
            <c:if test="${totalFee < 0}">
                <button class="layui-btn layui-btn-normal layui-btn-big" onclick="beginToPay();">立即退款</button>
            </c:if>
        </div>
    </div>
</div>

<!-- jQuery 2.2.0 -->
<script src="${pageContext.request.contextPath}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script type="text/javascript">
    var context = '${pageContext.request.contextPath}';

    layui.config({
        base: '${pageContext.request.contextPath}/plugins/layui/modules/'
    });
    //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
    layui.use('jquery', function() {
        var $ = layui.jquery;
        $(".online_list").on("click", function() {
            var self = $(this);
            var $value = self.data("id");
            if(!self.hasClass("active")) {
                $(".online_list").removeClass("active");
                self.addClass("active");
            }
            if(self.hasClass("yue_pay")) {
                $(".lineDown").show();
            } else {
                $(".lineDown").hide();
            }
            if(self.hasClass("yue_pay1")){
                $(".layui-btn").hide();
            } else {
                $(".layui-btn").show();
            }
            jQuery("#orderWay").val($value);

        });
    });

    layui.use(['form'], function() {
        var form = layui.form,
            layer = layui.layer;
    });
    function filesFun(obj,files){
        if(files){
            $("#previewPic").text("共"+files.length+"个文件");
        }else{
            $("#previewPic").text("请选择文件");
        }
    }


    function beginToPay() {
        var validate = true;
        var jiaoYiNumber = jQuery("#jiaoYiNumber").val();
        var orderWay = jQuery("#orderWay").val();

        if(validate) {
            layer.confirm('请确认是否要进行付款？', {
            btn : [ '确定', '取消' ]//按钮
        }, function(index) {
                if('O_OFFLINE' == orderWay) {
                    jQuery("#payment_form").attr('action', context + '/carrier/xiaoshoupaymentoffline.html');
                } else {
                    jQuery("#payment_form").attr('action', context + '/carrier/xiaoshoupaymentonlinefirst.html');
                }

                layer.close(index);
                jQuery("#payment_form").submit();
            });
        }
    }
</script>

</html>