package com.yajun.green.common.domain;


import com.yajun.green.common.utils.CHDateUtils;
import com.yajun.green.common.utils.CHStringUtils;
import com.yajun.green.common.utils.JodaUtils;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:02
 */
public abstract class Document extends EntityBase {

    private MultipartFile file;
    //上传到服务器的文件名
    private String uploadFileName;
    //存放到服务器的文件名
    private String actualFileName;

    private String actualFilePath;
    //上传时间
    private DateTime uploadTime;

    public Document() {
    }

    public Document(MultipartFile file, String actualFilePath) {
        this.file = file;
        this.uploadFileName = file != null ? file.getOriginalFilename() : "";
        String[] tokens = StringUtils.delimitedListToStringArray(uploadFileName, ".");
        this.actualFileName = CHDateUtils.getSimpleDateFormatForFile(new Date()) + "_" + CHStringUtils.getRandomString(10) + "." + tokens[tokens.length -1];
        this.uploadTime = JodaUtils.currentTime();
    }

    public void changeFile(MultipartFile file) {
        this.file = file;
        this.uploadFileName = file != null ? file.getOriginalFilename() : "";
        String[] tokens = StringUtils.delimitedListToStringArray(uploadFileName, ".");
        this.actualFileName = CHDateUtils.getSimpleDateFormatForFile(new Date()) + "_" + CHStringUtils.getRandomString(10) + "." + tokens[tokens.length -1];
        this.uploadTime = JodaUtils.currentTime();
    }

    public void resetActualFileName() {
        String[] tokens = StringUtils.delimitedListToStringArray(uploadFileName, ".");
        this.actualFileName = CHDateUtils.getSimpleDateFormatForFile(new Date()) + "_" + CHStringUtils.getRandomString(10) + "." + tokens[tokens.length -1];
    }

    public static String getFileSize(String size) {
        double howManyMByte = Double.valueOf(size) / 1024 / 1024;
        return String.format("%.2f", howManyMByte);
    }

    
    /***********************************************GET/SET******************************************************/

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getActualFileName() {
        return actualFileName;
    }

    public void setActualFileName(String actualFileName) {
        this.actualFileName = actualFileName;
    }

    public String getActualFilePath() {
        return actualFilePath;
    }

    public void setActualFilePath(String actualFilePath) {
        this.actualFilePath = actualFilePath;
    }

    public DateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(DateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
}
