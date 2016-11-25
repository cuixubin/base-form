package com.dlshouwen.core.base.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.system.model.Limit;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.core.system.model.UserAttr;


/**
 * 登陆
 * @author 刘精诚
 * @since 2013-7-15 13:48:10
 */
@Component("loginDao")
public class LoginDao extends BaseDao {
	
	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	/**
	 * 根据用户编号获取用户信息
	 * @param userCode 用户编号
	 * @return 用户对象
	 * @throws Exception 抛出全部异常
	 */
	public User getUserInfo(String userCode) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select u.*, d.dept_name, uc.user_name creator_name, ue.user_name editor_name ");
		sql.append("from core_user u ");
		sql.append("left join core_dept d on u.dept_id=d.dept_id ");
		sql.append("left join core_user uc on u.creator=uc.user_id ");
		sql.append("left join core_user ue on u.editor=ue.user_id ");
		sql.append("where u.user_code=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<User>(User.class), userCode);
	}

	/**
	 * 查询当前用户的在线数量
	 * @param user_id 用户编号
	 * @return 当前在线用户的数量
	 * @throws Exception 抛出全部异常
	 */
	public int getUserLoginCount(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from core_login_log where login_user_id=? and is_logout<>?");
		return this.queryForInt(sql.toString(), userId, "1");
	}

	/**
	 * 查询当前IP的在线数量
	 * @param ip 登录机的IP地址
	 * @return 当前IP在线的数量
	 * @throws Exception 抛出全部异常
	 */
	public int getIpLoginCount(String ip) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from core_login_log where ip=? and is_logout<>?");
		return this.queryForInt(sql.toString(), ip, "1");
	}

	/**
	 * 获取用户参数信息
	 * @param user_id 用户编号
	 * @return 用户参数对象
	 * @throws Exception
	 */
	public UserAttr getUserAttrById(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ua.* from core_user_attr ua where ua.user_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<UserAttr>(UserAttr.class), userId);
	}
	
	/**
	 * 获取皮肤信息
	 * @param skinId 皮肤变好
	 * @return 皮肤信息
	 * @throws Exception 抛出全部异常
	 */
	public Map<String, Object> getSkinInfoById(String skinId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.* from core_skin s where s.skin_id=?");
		return this.queryForMap(sql.toString(), skinId);
	}

	/**
	 * 获取快捷方式列表
	 * @param userId 用户编号
	 * @return 快捷方式列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Limit> getShortcutList(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.* from core_limit l where l.limit_id in (");
		sql.append("	select usl.limit_id from core_user_shortcut_limit usl where usl.user_id=? ");
		sql.append(") order by l.sort");
		return this.queryForList(sql.toString(), new ClassRowMapper<Limit>(Limit.class), userId);
	}
	
	/**
	 * 获取用户的所有权限对象
	 * @param userId 用户编号
	 * @return 权限列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Limit> getLimitList(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.* from core_limit l where l.limit_id in (");
		sql.append("	select lr.limit_id from core_limit_role lr where lr.role_id in (");
		sql.append("		select ur.role_id from core_user_role ur where ur.user_id=?");
		sql.append("	)");
		sql.append(") order by l.sort");
		return this.queryForList(sql.toString(), new ClassRowMapper<Limit>(Limit.class), userId);
	}
	
	/**
	 * 获取所有权限对象，针对用户判断是否包含权限
	 * @param userId 用户编号
	 * @return 权限列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getLimitInfoList(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.url, case when t.limit_id is null then '0' else '1' end is_has_limit ");
		sql.append("from core_limit l ");
		sql.append("left join (");
		sql.append("	select lr.limit_id from core_limit_role lr where lr.role_id in (");
		sql.append("		select ur.role_id from core_user_role ur where ur.user_id=?");
		sql.append("	)");
		sql.append(") t on l.limit_id=t.limit_id");
		return this.queryForList(sql.toString(), userId);
	}

}
