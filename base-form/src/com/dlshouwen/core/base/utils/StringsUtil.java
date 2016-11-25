/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.base.utils;

import java.util.List;

/**
 * 字符串处理相关 公共类
 * @author yangtong
 */
public class StringsUtil {
    /**
     * 按规定的分隔符分割集合
     * @param stringList  集合
     * @param sep  分割符
     * @return 
     */
    public static String listToString(List<Object> stringList,String sep){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (Object string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
}
