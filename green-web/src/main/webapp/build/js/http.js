var jsonP = false;
var url = "http://192.168.2.196:8080/";
function httpPost(fullUrl, body, success, error,useJsonP) {
	if(jsonP) {
		$.ajax({
			dataType: "jsonp",
			jsonp: "methodName",
			url: fullUrl,
			contentType: "application/json; charset=utf-8",
			data: encodeURI(JSON.stringify(body)),
			async: true,
			error: function(data) {
				error(data);
			},
			success: function(data) {
				if(data.status ==1000){
					success(data);
				}
			}
		});
	} else {
			$.ajax({
				datType : "json",
				url: fullUrl,
				type : 'POST',
				async: true,
				data: encodeURI(JSON.stringify(body)),
				error: function(data) {
					error(data);
				},
				success: function(data) {
					if(data.status ==1000){
						success(data);
					}
				}
			});
	}
}



function sendRequest(cmdID, body, success, error) {
	
	httpPost(url+cmdID, body, success, error,jsonP);
}

