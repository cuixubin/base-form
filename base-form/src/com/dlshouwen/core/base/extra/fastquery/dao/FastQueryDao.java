package com.dlshouwen.core.base.extra.fastquery.dao;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;

/**
 * 快速查询
 * @author 大连首闻科技有限公司
 * @version 2013-8-8 17:15:11
 */
@Component("fastQueryDao")
public class FastQueryDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}

}
