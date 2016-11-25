/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.base.patch;

import java.util.Date;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 修复JsonArray转换对象时格式问题
 * 定义日期格式类型常量
 * 使用方式：
 * 
 *  JSONArray.fromObject(obj, new DateJsonConfig(dataPattern).getJsonConfig()) 
 * 
 * @author cuixubin
 */
public class DateJsonConfig {
    /** 日期格式化对象，格式为yyyy-MM-dd'T'HH:mm:ss */
    private String defaultPattern = "yyyy-MM-dd HH:mm:ss";
    private JsonValueProcessor jsonProcessor;
    private JsonConfig jsonConfig;
    
    public DateJsonConfig() {
        jsonProcessor = new DateJsonValueProcessor(defaultPattern);
        jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, jsonProcessor);
    }
    
    /**
     * 根据设定的日期格式生成配置信息
     * @param formatPattern 设定的日期格式
     */
    public DateJsonConfig(String formatPattern) {
        if(null == formatPattern || "".equals(formatPattern)) {
            formatPattern = defaultPattern;
        }
        jsonProcessor = new DateJsonValueProcessor(formatPattern);
        jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, jsonProcessor);
    }

    public JsonConfig getJsonConfig() {
        return jsonConfig;
    }

    public void setJsonConfig(JsonConfig jsonConfig) {
        this.jsonConfig = jsonConfig;
    }
    
}
