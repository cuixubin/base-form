package com.dlshouwen.core.base.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.core.system.model.Limit;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.core.system.model.UserAttr;

/**
 * 附属功能
 * @author 刘精诚
 * @since 2013-7-16 18:59:36
 */
@Component("assistDao")
public class AssistDao extends BaseDao {
	
	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	/**
	 * 获取用户详细信息
	 * @param userId 用户编号
	 * @return 用户对象
	 * @throws Exception 抛出全部异常
	 */
	public User getUserById(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select u.*, d.dept_name, uc.user_name creator_name, ue.user_name editor_name ");
		sql.append("from core_user u ");
		sql.append("left join core_dept d on u.dept_id=d.dept_id ");
		sql.append("left join core_user uc on u.creator=uc.user_id ");
		sql.append("left join core_user ue on u.editor=ue.user_id ");
		sql.append("where u.user_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<User>(User.class), userId);
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
	 * 获取用户的快捷方式菜单
	 * @param user_id 用户编号
	 * @return 快捷方式菜单列表
	 * @throws Exception
	 */
	public List<Map<String, Object>> getShortcutLimitList(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select l.*, case when usl.limit_id is not null then true else false end checked ");
		sql.append("from core_limit l ");
		sql.append("left join core_user_shortcut_limit usl on l.limit_id=usl.limit_id and usl.user_id=? ");
		sql.append("where l.limit_type='1' and l.limit_id in (");
		sql.append("	select limit_id from core_limit_role ");
		sql.append("	where role_id in (");
		sql.append("		select role_id from core_user_role where user_id=?");
		sql.append("	)");
		sql.append(") order by l.sort");
		return this.queryForList(sql.toString(), userId, userId);
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
	 * 执行更新用户
	 * @param user 用户对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateUser(User user) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_user set dept_id=${dept_id }, user_name=${user_name }, valid_type=${valid_type }, sex=${sex }, ");
		sql.append("card_type=${card_type }, card_id=${card_id }, birthday=${birthday }, work_date=${work_date }, ");
		sql.append("folk=${folk }, degree=${degree }, phone=${phone }, ");
		sql.append("email=${email }, address=${address }, remark=${remark }, editor=${editor }, edit_time=${edit_time } ");
		sql.append("where user_id=${user_id }");
		return this.updateObject(sql.toString(), user);
	}

	/**
	 * 执行更新用户参数
	 * @param userAttr 用户参数对象
	 * @return 影响的记录条数
	 * @throws Exception
	 */
	public int updateUserAttr(UserAttr userAttr) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_user_attr set skin_info=${skin_info }, is_show_shortcut=${is_show_shortcut },is_background_float=${is_background_float},background_float_speed=${background_float_speed} ");
		sql.append("where user_id=${user_id }");
		return this.updateObject(sql.toString(), userAttr);
	}

	/**
	 * 新建用户参数
	 * @param userAttr 用户参数对象
	 * @return 影响的记录条数
	 * @throws Exception
	 */
	public int insertUserAttr(UserAttr userAttr) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_user_attr (user_id, skin_info, is_show_shortcut) ");
		sql.append("values (${user_id }, ${skin_info }, ${is_show_shortcut })");
		return this.updateObject(sql.toString(), userAttr);
	}

	/**
	 * 更新用户快捷方式菜单
	 * @param user_id 用户编号
	 * @param shortcutLimitIds 快捷方式菜单列表
	 * @throws Exception
	 */
	public void updateUserShortcutLimit(String userId, String shortcutLimitIds) throws Exception {
//		删除原有快捷方式菜单
		StringBuffer sql = new StringBuffer();
		sql.append("delete from core_user_shortcut_limit where user_id=?");
		this.update(sql.toString(), userId);
//		新建快捷方式菜单
		sql = new StringBuffer();
		sql.append("insert into core_user_shortcut_limit (user_id, limit_id) ");
		sql.append("(select ?, limit_id from core_limit l where l.limit_id in (").append(SqlUtils.getArgsKey(shortcutLimitIds, ",")).append("))");
		List<Object> args = SqlUtils.getArgsValue(shortcutLimitIds, ",");
		args.add(0, userId);
		this.update(sql.toString(), args.toArray());
	}
	
	/**
	 * 获取是否匹配原密码
	 * @param userId 用户编号
	 * @param oldPassword 原密码
	 * @return 是否匹配
	 * @throws Exception 抛出全部异常
	 */
	public boolean isOldPassword(String userId, String oldPassword) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from core_user u where u.user_id=? and u.password=?");
		return this.queryForInt(sql.toString(), userId, oldPassword)>0?true:false;
	}

	/**
	 * 更新密码
	 * @param userId 用户编号
	 * @param newPassword 新密码
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int changePassword(String userId, String newPassword) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_user set password=? where user_id=?");
		return this.update(sql.toString(), newPassword, userId);
	}

	/**
	 * 根据用户名获取用户信息
	 * @param user_code 用户名
	 * @return 用户对象
	 * @throws Exception
	 */
	public Map<String, Object> getUserInfo(String userCode) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select u.*, ua.* from sys_user u ");
		sql.append("left join sys_user_attr ua on u.user_id=ua.user_id ");
		sql.append("where u.user_code=?");
		return this.queryForMap(sql.toString(), userCode);
	}
	
}
