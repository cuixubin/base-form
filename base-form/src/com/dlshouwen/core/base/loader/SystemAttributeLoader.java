package com.dlshouwen.core.base.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dlshouwen.core.base.config.CONFIG;

/**
 * 系统参数加载器
 * @author 大连首闻科技有限公司
 * @version 2013-7-12 16:21:42
 */
public class SystemAttributeLoader {
	
	/**
	 * 加载系统参数
	 * <p>将系统参数加载到Application区域中，格式为Map<attrCode, Map<attr>>，其中attrCode为参数编号，attr表示参数Map对象，即core_attr表中某行数据
	 * @param dataSource 加载系统参数使用的数据源
	 * @throws Exception 抛出全部异常
	 */
	public static void load(DataSource dataSource) throws Exception {
//		定义JdbcTemplate对象
		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		日志定义
		Logger logger = Logger.getLogger(SystemAttributeLoader.class);
//		记录开始日志
		logger.info("正在加载系统参数，请稍后...");
//		查询系统参数结果列表
		List<Map<String, Object>> systemAttributeList = jt.queryForList(CONFIG.SYSTEM_ATTRIBUTE_SQL);
//		定义外层Map用于存储系统参数
		Map<String, Object> systemAttributes = new HashMap<String, Object>();
//		遍历码表结果列表
		for(Map<String, Object> systemAttribute : systemAttributeList){
//			参数名为attr_code，对象为参数信息对象
			systemAttributes.put(MapUtils.getString(systemAttribute, "attr_code"), systemAttribute);
		}
//		将码表放入Application域中
		CONFIG.SERVLET_CONTEXT.removeAttribute(CONFIG.SYSTEM_ATTRIBUTE);
		CONFIG.SERVLET_CONTEXT.setAttribute(CONFIG.SYSTEM_ATTRIBUTE, systemAttributes);
//		记录码表成功日志
		logger.info("系统加载完成，共加载 "+systemAttributeList.size()+" 条数据。");
	}
	
}
