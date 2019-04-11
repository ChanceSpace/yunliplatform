package com.yajun.green.common.utils;

import com.yajun.green.common.log.ApplicationLog;
import org.springframework.util.StringUtils;

/**
 * Created by LiuMengKe on 2018/1/13.
 */
public class ContactLogUtil {

    //用于大的活动 方法的开始
    public  static  String addLine(String log){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.hasText(log)){
            builder.append("===============================").append(log).append("=================================");
            return builder.toString();
        }else {
            return null;
        }
    }

    //中等日志
    public  static  void addShortLine(Class clz,String log){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.hasText(log)){
            builder.append("===============").append(log).append("==============");
            ApplicationLog.info(clz,builder.toString());
        }
    }


    //中等日志
    public  static  void addActionLine(Class clz,String log){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.hasText(log)){
            builder.append("===============").append(log);
            ApplicationLog.info(clz,builder.toString());
        }
    }



    public  static  void addLineAndLog(Class clz,String log){
        ApplicationLog.info(clz,addLine(log));
    }

    public  static  void addErrorLineAndLog(Class clz,String log){
        ApplicationLog.error(clz,addLine(log));
    }


}
