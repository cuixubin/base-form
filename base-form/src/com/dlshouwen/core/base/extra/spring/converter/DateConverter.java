package com.dlshouwen.core.base.extra.spring.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * 日期转换器
 * @author 大连首闻科技有限公司
 * @version 2013-8-11 17:35:01
 */
public class DateConverter implements Converter<String, Date> {
	
	/** 日期格式化类，格式为yyyy-MM-dd */
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/** 时间格式化类，格式为yyyy-MM-dd HH:mm:ss */
	private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 日期转换
	 * @param date 需要转换的日期字符串
	 * @return 转换后的日期对象
	 */
	public Date convert(String date) {
		if (!StringUtils.hasText(date)) {
			return null;
		}
		if (date.indexOf(":") == -1 && date.length() == 10) {
			try {
				return dateFormat.parse(date);
			} catch (ParseException e) {
				throw new RuntimeException("日期格式出错");
			}
		}else if (date.indexOf(":") != -1 && date.length() == 19) {
				try {
					return timeFormat.parse(date);
				} catch (ParseException e) {
					throw new RuntimeException("日期格式出错");
				}
		}else{
			throw new RuntimeException("日期格式出错");
		}
	}

}
