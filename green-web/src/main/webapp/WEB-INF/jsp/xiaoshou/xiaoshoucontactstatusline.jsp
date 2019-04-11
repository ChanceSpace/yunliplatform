<%--合同详情上面的 那个状态线--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<c:if test="${xiaoShouContact.xiaoShouType == 'B_BAOYUE'}">
    <fieldset class="layui-elem-field">
        <legend><i class="layui-icon">&#xe658;</i>合同状态 -> 销售:${xiaoShouContact.saleManName}&nbsp;&nbsp;编号:${xiaoShouContact.contactNumber}</legend>
        <div class="layui-row">
            <div class="layui-container">
                <div class="ya_status_box ">
                    <div class="layui-col-md3">
                        <div class="status_list <c:if test="${xiaoShouContact.contactStatus== 'S_CREATED'||xiaoShouContact.contactStatus== 'S_PROCESSING'||xiaoShouContact.contactStatus== 'S_FINISHED'}">active</c:if>" id="contactStatus1">
                            <div class="status_list_title">已创建</div>
                            <div class="status_list_img"></div>
                            <div class="status_list_bar">
                                <div class="bar_proess"></div>
                            </div>
                            <div class="status_list_subtitle">合同需确认执行</div>
                        </div>
                    </div>
                    <div class="layui-col-md3">
                        <div class="status_list <c:if test="${xiaoShouContact.contactStatus== 'S_PROCESSING'||xiaoShouContact.contactStatus== 'S_FINISHED'}">active</c:if>" id="contactStatus2">
                            <div class="status_list_title" >执行中</div>
                            <div class="status_list_img"></div>
                            <div class="status_list_bar">
                                <div class="bar_proess"></div>
                            </div>
                            <div class="status_list_subtitle">合同执行中</div>
                        </div>
                    </div>
                    <div class="layui-col-md3">
                        <div class="status_list <c:if test="${xiaoShouContact.contactStatus== 'S_ENDBUTNOTOVER'||xiaoShouContact.contactStatus== 'S_FINISHED'}">active</c:if> " id="contactStatus3">
                            <div class="status_list_title" >自动扣费结束</div>
                            <div class="status_list_img"></div>
                            <div class="status_list_bar">
                                <div class="bar_proess"></div>
                            </div>
                            <div class="status_list_subtitle">合同自动扣费结束</div>
                        </div>
                    </div>
                    <div class="layui-col-md3">
                        <div class="status_list <c:if test="${xiaoShouContact.contactStatus== 'S_FINISHED'}">active</c:if> " id="contactStatus4">
                            <div class="status_list_title" >已结束</div>
                            <div class="status_list_img"></div>
                            <div class="status_list_bar">
                            </div>
                            <div class="status_list_subtitle">合同已结束</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </fieldset>
</c:if>--%>


<%--合同详情上面的 那个状态线--%>
<%--<c:if test="${xiaoShouContact.xiaoShouType == 'B_RIJIE'}">--%>
    <fieldset class="layui-elem-field">
        <legend><i class="layui-icon">&#xe658;</i>合同状态 -> 销售:${xiaoShouContact.saleManName}&nbsp;&nbsp;编号:${xiaoShouContact.contactNumber}</legend>
        <div class="layui-row">
            <div class="layui-container">
                <div class="ya_status_box ">
                    <div class="layui-col-md4">
                        <div class="status_list <c:if test="${xiaoShouContact.contactStatus== 'S_CREATED'||xiaoShouContact.contactStatus== 'S_PROCESSING'||xiaoShouContact.contactStatus== 'S_FINISHED'}">active</c:if>" id="rijie_contactStatus1">
                            <div class="status_list_title">已创建</div>
                            <div class="status_list_img"></div>
                            <div class="status_list_bar">
                                <div class="bar_proess"></div>
                            </div>
                            <div class="status_list_subtitle">合同需确认执行</div>
                        </div>
                    </div>
                    <div class="layui-col-md4">
                        <div class="status_list <c:if test="${xiaoShouContact.contactStatus== 'S_PROCESSING'||xiaoShouContact.contactStatus== 'S_FINISHED'}">active</c:if>" id="rijie_contactStatus2">
                            <div class="status_list_title" >执行中</div>
                            <div class="status_list_img"></div>
                            <div class="status_list_bar">
                                <div class="bar_proess"></div>
                            </div>
                            <div class="status_list_subtitle">合同执行中</div>
                        </div>
                    </div>
                    <div class="layui-col-md4">
                        <div class="status_list <c:if test="${xiaoShouContact.contactStatus== 'S_FINISHED'}">active</c:if> " id="rijie_contactStatus3">
                            <div class="status_list_title" >已结束</div>
                            <div class="status_list_img"></div>
                            <div class="status_list_bar">
                            </div>
                            <div class="status_list_subtitle">合同已结束</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </fieldset>
<%--
</c:if>--%>
