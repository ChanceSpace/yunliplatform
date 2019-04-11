//电话验证
function checkMobile(mobile){
    if(!(/^1[34578]\d{9}$/.test(mobile))){
        return false;
    }
    return true;
}

function checkTelephone(tel){
    var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
    if(isPhone.test(tel)){
        return true;
    }
    return false;
}

 //图片验证
function fileValidate(obj, id, files){
    var isAllow = false;
    if(files && files[0]){
        var fileData = files[0];
        //图片的尺寸
        var size = files[0].size/1024;
        //读取图片数据
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload=function(){
                var width = image.width;
                var height = image.height;
            };
            image.src= data;
        };
        reader.readAsDataURL(fileData);
        $("#previewPic").text(files[0].name);
        if(size > 512) {
            jQuery("#image_help").css("display","block");
            jQuery("#image_right").val("NO");
        } else {
            jQuery("#image_help").css("display","none");
            jQuery("#image_right").val("YES");
        }
    }else{
        //IE下使用滤镜来处理图片尺寸控制
        //文件name中IE下是完整的图片本地路径
        var input = $("picture");
        input.select();
        //确保IE9下，不会出现因为安全问题导致无法访问
        input.blur();
        var src = document.selection.createRange().text;
        var img = $('<img style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);width:300px;visibility:hidden;"  />').appendTo('body').getDOMNode();
        img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
        var width = img.offsetWidth;
        var height = img.offsetHeight;

    }
}