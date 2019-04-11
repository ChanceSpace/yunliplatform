var jsonP = false;
var cmsCode = "200";
var crmCode = "B001";
//var crm_url = "http://192.168.3.23/crm/dispatch/dispatch.action";//scrm.trond.cn
var crm_url = "http://scrm.trond.cn/crm/dispatch/dispatch.action";//scrm.trond.cn

//var zdl_url = "http://192.168.3.36/zdl/dispatch/dispatch.action";//gw.zhuangdl.com
var zdl_url = "http://gw.zhuangdl.com/zdl/dispatch/dispatch.action";//gw.zhuangdl.com
var upload_url = "http://gw.zhuangdl.com/zdl/dispatch/upload.action"; //图片上传地址
var m_url = "http://m.zhuangdl.com/";
/**PC版样式**/
var domain_url = "zhuangdl.com";
var pc_url = "http://www.zhuangdl.com";
layui.define('jquery', function(exports) { //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
	var $ = layui.jquery;
	var obj = {
		"crm_url":crm_url,
		httpPost: function(url, header, body, success, error, successCode, useJsonP) {
			if(useJsonP) {
				$.ajax({
					dataType: "jsonp",
					jsonp: "methodName",
					url: url + "?body=" + JSON.stringify(body) + "&header=" + JSON.stringify(header),
					async: false,
					error: function(data) {
						if(data == null || data.resMsg == null) {
							data = {};
							data.resMsg = "网络连接失败！";
						}
						error(data);
					},
					success: function(data) {
						if(successCode == "" || successCode == null) {
							success(data);
						} else {
							if(data != null && data.resCode == successCode) {
								success(data);
							} else {
								error(data);
							}
						}
					}
				});
			} else {
				$.ajax({
					dataType: "json",
					url: url,
					async: true,
					data: "body=" + JSON.stringify(body) + "&header=" + JSON.stringify(header),
					type: "post",
					error: function(data) {
						if(data == null || data.resMsg == null) {
							data = {};
							data.resMsg = "网络连接失败！";
						}else{
							if(data.resCode == "A006"){
								error(data);
								setTimeout(function(){
									window.location.href="login.html"
								},2000);
							}
							error(data);
						}
						
					},
					success: function(data) {
						if(successCode == "" || successCode == null) {
							success(data);
						} else {
							if(data != null && data.resCode == successCode) {
								success(data);
							} else {
								if(data.resCode == "A006"){
									error(data);
									setTimeout(function(){
										window.location.href="login.html"
									},2000);
								}
								error(data);
							}
						}
					}
				});
			}
		},
		getCmsHeader: function(cmdID) {
			return {
				"cmdID": cmdID,
				"deviceType": "h5",
				"token": sessionStorage.getItem("_token"),
				"uniquelyNo": "896546543218784",
				"roleType": sessionStorage.getItem("_roleType"),
				"userId": sessionStorage.getItem("_userId"),
				"from": sessionStorage.getItem("_from")
			}
		},
		cmsSendPage: function(cmdID, body, success, error) {
			this.httpPost(cms_url, this.getCmsHeader(cmdID), body, success, error, cmdID + cmsCode, jsonP);
		},
		zdlSend: function(cmdID, body, success, error) {
			this.httpPost(zdl_url, this.getCmsHeader(cmdID), body, success, error, cmdID + cmsCode, jsonP);
		}
	};
//输出test接口
exports('gHttp', obj);
});