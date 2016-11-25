package com.dlshouwen.core.base.patch;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * JSON-LIB 补丁，修复Date转换错误，应用其开放接口进行修复
 * @author 大连首闻科技有限公司
 * @version 2015-12-2 14:11:35
 */
public class DateJsonValueProcessor implements JsonValueProcessor {
        /** 日期格式化对象，格式为yyyy-MM-dd'T'HH:mm:ss */
        private String formatPattern = "yyyy-MM-dd HH:mm:ss";

        public DateJsonValueProcessor() {
        }
        
        /**
         * 
         * @param formatPattern 日期格式默认为为yyyy-MM-dd'T'HH:mm:ss
         */
        public DateJsonValueProcessor(String formatPattern) {
            this.formatPattern = formatPattern;
        }
        
        
        
	/**
	 * 处理数组的值
	 * @param value 数据对象
	 * @param jsonConfig json配置信息
	 * @return 处理后的对象
	 */
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return this.process(value);
	}

	/**
	 * 处理对象的值
	 * @param key 键
	 * @param value 值
	 * @param jsonConfig json配置信息
	 * @return 处理后的对象
	 */
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return this.process(value);
	}

	/**
	 * 处理Date类型返回的Json数值
	 * @param value 需要处理的对象
	 * @return 处理后的对象
	 */
	private Object process(Object value) {
		if (value == null) {
			return "";
		} else if (value instanceof Date) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern, Locale.CHINA);
			return simpleDateFormat.format(value);
		} else {
			return value.toString();
		}
	}
	
}