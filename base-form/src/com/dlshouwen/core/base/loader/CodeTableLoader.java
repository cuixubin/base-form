package com.dlshouwen.core.base.loader;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dlshouwen.core.base.config.CONFIG;

/**
 * 码表加载器
 * @author 大连首闻科技有限公司
 * @version 2013-7-12 16:21:33
 */
@SuppressWarnings("unchecked")
public class CodeTableLoader {
	
	/**
	 * 加载码表
	 * <p>将码表加载至Application区域中，格式为Map<type, Map<key, value>>，其中type表示码表类别编号，key表示码表编号，value表示码表值
	 * @param dataSource 加载码表使用的数据源
	 * @throws Exception 抛出全部异常
	 */
	public static void load(DataSource dataSource) throws Exception {
//		定义JdbcTemplate对象
		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		日志定义
		Logger logger = Logger.getLogger(CodeTableLoader.class);
//		记录开始日志
		logger.info("正在加载码表信息，请稍后...");
//		查询码表结果列表
		List<Map<String, Object>> codeTableList = jt.queryForList(CONFIG.CODE_TABLE_SQL);
//		定义外层Map用于存储码表，定义内层Map用于存储码表内层每个Type的值
		Map<String, Object> codeTableMap = new LinkedHashMap<String, Object>();
		Map<String, Object> codeTableTypeMap = null;
//		定义类型
		String codeTableType = "";
//		遍历码表结果列表
		for(Map<String, Object> nowCodeTableMap : codeTableList){
//			获取类型
			codeTableType = MapUtils.getString(nowCodeTableMap, "c_type");
//			如果外层codeTableMap中存在了此属性
			if(codeTableMap.containsKey(codeTableType)){
//				获取属性
				codeTableTypeMap = (Map<String, Object>)MapUtils.getObject(codeTableMap, codeTableType);
//				加入到当前CodeTableTypeMap中
				codeTableTypeMap.put(MapUtils.getString(nowCodeTableMap, "c_key"), MapUtils.getString(nowCodeTableMap, "c_value"));
//			如果外层codeTableMap中不存在此属性
			}else{
//				实例化类型码表
				codeTableTypeMap = new LinkedHashMap<String, Object>();
//				加入到当前CodeTableTypeMap中
				codeTableTypeMap.put(MapUtils.getString(nowCodeTableMap, "c_key"), MapUtils.getString(nowCodeTableMap, "c_value"));
//				加入到当前codeTableMap中
				codeTableMap.put(codeTableType, codeTableTypeMap);
			}
		}
//		将码表放入Application域中
		CONFIG.SERVLET_CONTEXT.removeAttribute(CONFIG.CODE_TABLE);
		CONFIG.SERVLET_CONTEXT.setAttribute(CONFIG.CODE_TABLE, codeTableMap);
//		记录码表成功日志
		logger.info("码表加载完成，共加载 "+codeTableList.size()+" 条数据。");
	}
	
}
