package com.dlshouwen.core.base.dao;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

/**
 * 皮肤功能
 * @author 大连首闻科技有限公司
 * @version 2016-5-11 15:03:33
 */
@Component("skinDao")
public class SkinDao extends BaseDao {
	
	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}

	/**
	 * 设置用户皮肤信息
	 * @param userId 用户编号
	 * @param skinInfo 皮肤信息
	 * @return 影响的记录条数
	 * @throws Exception
	 */
	public int setUserSkinInfo(String userId, String skinInfo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_user_attr set skin_info=? where user_id=?");
		return this.update(sql.toString(), skinInfo, userId);
	}
	
}
