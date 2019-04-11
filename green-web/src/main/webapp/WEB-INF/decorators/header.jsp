<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="yj" uri="http://www.rajaev.com" %>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>绿色共享运力平台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="http://www.jq22.com/jquery/font-awesome.4.6.0.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/build/css/app.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>

 <div class="layui-layout layui-layout-admin kit-layout-admin">

     <%--表头部分--%>
    <div class="layui-header">
        <div class="layui-logo"><img src="${pageContext.request.contextPath}/build/images/icon.png" width="30" style="margin-right:6px;"/>绿色共享运力平台</div>
        <div class="layui-logo kit-logo-mobile"><img src="${pageContext.request.contextPath}/build/images/icon.png" width="30" style="margin-right:6px;"/></div>

        <ul class="layui-nav layui-layout-right kit-nav" lay-filter="kitNavbar" kit-navbar>
            <li class="layui-nav-item">
                <a href="javascript:;" style="color:#009E94;" class="layui-nav-box">
                    <img src="${pageContext.request.contextPath}/build/images/person.png" class="layui-nav-img"><yj:user/>
                </a>
            </li>
            <li class="layui-nav-item"><a href="${MAIN_NUME}" style="color:#009F95;"><i class="fa fa-sign-out" aria-hidden="true"></i> 返回</a></li>
        </ul>
    </div>

    <%--菜单部分     --%>
    <div class="layui-side layui-bg-black kit-side">
        <div class="layui-side-scroll">
            <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
            <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>

                <yj:security grantRoles="ROLE_GREEN_CARRIER,ROLE_GREEN_CARRIER_RENT,ROLE_GREEN_FINANCE,ROLE_GREEN_ADMIN">
                <%--租赁管理--%>
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;"><i class="fa fa-columns" aria-hidden="true"></i><span> 租赁管理</span></a>
                     <dl class="layui-nav-child">
                        <dd>
                            <yj:security grantRoles="ROLE_GREEN_CARRIER">
                                 <a href="javascript:;" data-url="${pageContext.request.contextPath}/carrier/zupincontactcarrieroverview.html" data-icon="fa-file" data-title="我的租赁合同" kit-target data-id='21'><i class="fa fa-file" aria-hidden="true"></i><span> 我的租赁合同</span></a>
                            </yj:security>
                            <yj:security grantRoles="ROLE_GREEN_ADMIN,ROLE_GREEN_CARRIER_RENT,ROLE_GREEN_FINANCE">
                                <a href="javascript:;" data-url="${pageContext.request.contextPath}/contact/zupincontactoverview.html" data-icon="fa-folder" data-title="租赁合同管理" kit-target data-id='22'><i class="fa fa-folder" aria-hidden="true"></i><span> 租赁合同管理</span></a>
                            </yj:security>
                            <yj:security grantRoles="ROLE_GREEN_CARRIER_RENT,ROLE_GREEN_FINANCE,ROLE_GREEN_CARRIER">
                                <a href="javascript:;" data-url="${pageContext.request.contextPath}/carrier/paymentmanagement.html" data-icon="fa-money" data-title="待支付" kit-target data-id='23'><i class="fa fa-money" aria-hidden="true"></i><span> 待支付</span></a>
                            </yj:security>
                        </dd>
                    </dl>
                </li>
                </yj:security>

                <yj:security grantRoles="ROLE_GREEN_CONTACT_SALE,ROLE_GREEN_CONTACT_FINANCE,ROLE_GREEN_ADMIN">
                    <%--租赁管理--%>
                    <li class="layui-nav-item layui-nav-itemed">
                        <a class="" href="javascript:;"><i class="fa fa-columns" aria-hidden="true"></i><span> 销售管理</span></a>
                        <dl class="layui-nav-child">
                            <dd>
                                <%--<yj:secuexclude excludeRoles="ROLE_GREEN_CONTACT_SALE,ROLE_GREEN_CONTACT_FINANCE,ROLE_GREEN_ADMIN">
                                    <a href="javascript:;" data-url="${pageContext.request.contextPath}/contact/xiaoshoucontactforcarrieroverview.html" data-icon="fa-file" data-title="我的销售合同" kit-target data-id='71'><i class="fa fa-file" aria-hidden="true"></i><span> 我的销售合同</span></a>
                                </yj:secuexclude>--%>
                                <yj:security grantRoles="ROLE_GREEN_CONTACT_SALE,ROLE_GREEN_ADMIN">
                                    <a href="javascript:;" data-url="${pageContext.request.contextPath}/contact/xiaoshoucontactoverview.html" data-icon="fa-folder" data-title="销售合同管理" kit-target data-id='72'><i class="fa fa-folder" aria-hidden="true"></i><span> 销售合同管理</span></a>
                                </yj:security>
                                <yj:security grantRoles="ROLE_GREEN_CONTACT_FINANCE">
                                            <a href="javascript:;" data-url="${pageContext.request.contextPath}/carrier/xiaoshoupaymentmanagement.html" data-icon="fa-money" data-title="待支付" kit-target data-id='23'><i class="fa fa-money" aria-hidden="true"></i><span> 待支付</span></a>
                                </yj:security>
                            </dd>
                        </dl>
                    </li>
                </yj:security>

                <%--账户管理部分--%>
                <yj:security grantRoles="ROLE_GREEN_FINANCE,ROLE_GREEN_CONTACT_FINANCE,ROLE_GREEN_CARRIER,ROLE_GREEN_ADMIN">
                    <li class="layui-nav-item layui-nav-itemed">
                        <a class="" href="javascript:;"><i class="fa fa-list" aria-hidden="true"></i><span> 账户管理</span></a>
                        <dl class="layui-nav-child">
                            <dd>
                                <yj:security grantRoles="ROLE_GREEN_CARRIER">
                                    <a href="javascript:;" data-url="${pageContext.request.contextPath}/carrier/payordercarrieroverview.html" data-icon="fa-retweet" data-title="我的账户" kit-target data-id='52'><i class="fa fa-retweet" aria-hidden="true"></i><span> 我的账户</span></a>
                                </yj:security>
                                <yj:security grantRoles="ROLE_GREEN_FINANCE,ROLE_GREEN_CONTACT_FINANCE,ROLE_GREEN_ADMIN">
                                    <a href="javascript:;" data-url="${pageContext.request.contextPath}/carrier/payordercarrieroverview.html" data-icon="fa-retweet" data-title="我的账户" kit-target data-id='52'><i class="fa fa-retweet" aria-hidden="true"></i><span> 我的账户</span></a>
                                    <a href="javascript:;" data-url="${pageContext.request.contextPath}/carrier/carrierbalanceinfomanagement.html" data-icon="fa-picture-o" data-title="客户列表" kit-target data-id='51'><i class="fa fa-picture-o" aria-hidden="true"></i><span> 客户列表</span></a>
                                </yj:security>
                            </dd>
                        </dl>
                    </li>
                </yj:security>

            </ul>
        </div>
    </div>

    <%--内容部分--%>
    <div class="layui-body" id="container">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">内容加载中,请稍等...</div>
    </div>
    <div class="layui-footer">
         销售热线:028-68267000&nbsp;|&nbsp;服务热线:400-688-9819&nbsp;|&nbsp;蜀ICP备15003379号-2
    </div>
</div>

<script src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
<script>
    var message;
    layui.config({
        base: '${pageContext.request.contextPath}/build/js/'
    }).use(['app', 'message'], function() {
        var app = layui.app,
            $ = layui.jquery,
            layer = layui.layer;
        //将message设置为全局以便子页面调用
        message = layui.message;
        //主入口
        app.set({
            type: 'iframe'
        }).init();

    });
</script>

