package com.dlshouwen.core.tools.dao;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;

/**
 * 截图
 * @author 大连首闻科技有限公司
 * @version 2015-10-19 09:18:30
 */
@Component("screenshotDao")
public class ScreenshotDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
}