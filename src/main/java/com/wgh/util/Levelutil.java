package com.wgh.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2018/4/25.
 */
@Slf4j
public class Levelutil {
    public  final  static  String SEPARATOR=".";
    public  final  static  String ROOT="0";
    public  static  String calcuteLevel(String parentLevel,int parentId){
            //StringUtils.isBlank(parentLevel
            if(StringUtils.isEmpty(parentLevel)){
               return  ROOT;

            }else{
                log.info("得到的父亲层级："+StringUtils.join(parentLevel,SEPARATOR,parentId)+"",StringUtils.join(parentLevel,SEPARATOR,parentId));
                return  StringUtils.join(parentLevel,SEPARATOR,parentId);
            }
    }
}
