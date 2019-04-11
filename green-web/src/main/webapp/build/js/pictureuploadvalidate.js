/**
 * Created by Administrator on 2017/12/15.
 *      验证上传图片大小
 */

//可以上传返回true 图片大小不符合条件 false
function validatepicturesize(contactFile,targetsize) {

    var type = contactFile[0].files[0].type;
    var extStart = type.lastIndexOf("/");
    //取得后缀并进行大写
    var speType = type.substring(extStart,type.length).toUpperCase();
    //console.log(speType);
    var isImage = (speType == "/BMP" || speType== "/PNG" || speType == "/GIF" || speType == "/JPG" || speType== "/JPEG");

    //限制图片类型的文件大小不能超过1M 其他类型的文件不做限制
    if(contactFile[0].files[0].size>targetsize&& isImage==true){
        return false;
    }else {
        return true;
    }

}

//图片预览
function yulanpicture(file) {
    var url = null;
    if(window.createObjectURL != undefined) {
        url = window.createObjectURL(file)
    } else if(window.URL != undefined) {
        url = window.URL.createObjectURL(file)
    } else if(window.webkitURL != undefined) {
        url = window.webkitURL.createObjectURL(file)
    }
    return url

}
