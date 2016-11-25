package com.dlshouwen.core.system.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.DateUtils;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.core.system.model.User;
import com.dlshouwen.core.system.model.UserAttr;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

/**
 * 用户管理
 *
 * @author 大连首闻科技有限公司
 * @version 2013-8-8 17:15:45
 */
@Component("userDao")
public class UserDao extends BaseDao {

    /**
     * 注入数据源
     *
     * @param dataSource 数据源对象
     */
    @Resource(name = "defaultDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    /**
     * 获取用户列表数据
     *
     * @param pager 分页对象
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    public void getUserList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select u.*, d.dept_name, uc.user_name creator_name, ue.user_name editor_name ");
        sql.append("from core_user u ");
        sql.append("left join core_dept d on u.dept_id=d.dept_id ");
        sql.append("left join core_user uc on u.creator=uc.user_id ");
        sql.append("left join core_user ue on u.editor=ue.user_id ");
        GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
    }

    /**
     * 获取用户列表数据
     *
     * @throws Exception 抛出全部异常
     */
    public List<User> getUserList() throws Exception {
        String sql = "select u.* from core_user u ";
        return this.queryForList(sql, new ClassRowMapper<User>(User.class));
    }

    /**
     * 执行新增用户
     *
     * @param user 需要被新增的用户对象
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int insertUser(User user) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into core_user (user_id, dept_id, team_id, user_code, user_name, password, ");
        sql.append("valid_type, identity, sex, card_type, card_id, birthday, work_date, imgpath, folk, degree, qualified, graduateSchool, ");
        sql.append("phone, email, address, ");
        sql.append("remark, creator, create_time, editor, edit_time) ");
        sql.append("values (${user_id }, ${dept_id }, ${team_id }, ${user_code }, ${user_name }, ${password }, ");
        sql.append("${valid_type }, ${identity }, ${sex }, ${card_type }, ${card_id }, ${birthday }, ${work_date }, ${imgpath }, ${folk }, ${degree }, ${qualified}, ${graduateSchool}, ");
        sql.append("${phone }, ${email }, ${address }, ");
        sql.append("${remark }, ${creator }, ${create_time }, ${editor }, ${edit_time })");
        return this.updateObject(sql.toString(), user);
    }

    /**
     * 批量插入用户数据
     *
     * @param users 用户集合
     * @return
     * @throws Exception
     */
    public int[] batchInsertUsers(final List<User> users) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into core_user (user_id, dept_id, team_id, user_code, user_name, password, ");
        sql.append("valid_type, sex, card_type, card_id, birthday, work_date, folk, degree, qualified, graduateSchool, ");
        sql.append("phone, email, address, ");
        sql.append("remark, creator, create_time, editor, edit_time, identity) ");
        sql.append("values (?, ?, ?, ?, ?, ?, ");
        sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
        sql.append("?, ?, ?, ?, ");
        sql.append("?, ?, ?, ?, ?)");
        
        BatchPreparedStatementSetter ps = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                User user = users.get(i);
                ps.setString(1, user.getUser_id());ps.setString(2, user.getDept_id());
                ps.setString(3, user.getTeam_id());ps.setString(4, user.getUser_code());
                ps.setString(5, user.getUser_name());ps.setString(6, user.getPassword());
                ps.setString(7, user.getValid_type());ps.setString(8, user.getSex());
                ps.setString(9, user.getCard_type());ps.setString(10, user.getCard_id());
                ps.setDate(11, DateUtils.switchUDateToSDate(user.getBirthday()));
                ps.setDate(12, DateUtils.switchUDateToSDate(user.getWork_date()));
                ps.setString(13, user.getFolk());ps.setString(14, user.getDegree());
                ps.setString(15, user.getQualified());ps.setString(16, user.getGraduateSchool());
                ps.setString(17, user.getPhone());ps.setString(18, user.getEmail());
                ps.setString(19, user.getAddress());ps.setString(20, user.getRemark());
                ps.setString(21, user.getCreator());ps.setDate(22, DateUtils.switchUDateToSDate(user.getCreate_time()));
                ps.setString(23, user.getEditor());ps.setDate(24, DateUtils.switchUDateToSDate(user.getEdit_time()));
                ps.setString(25, user.getIdentity());
            }

            public int getBatchSize() {
                return users.size();
            }

        };
        return this.batchUpdate(sql.toString(), users, ps);

    }
    
    /**
     * 批量插入用户附属信息
     * @param uAttr
     * @return
     * @throws Exception 
     */
    public int[] batchInsertUserAttr(final List<UserAttr> uAttr) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into core_user_attr (user_id, skin_info, is_show_shortcut, is_background_float, background_float_speed) ");
        sql.append("values (?, ?, ?, ?, ?)");
        
         BatchPreparedStatementSetter ps = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UserAttr uattr = uAttr.get(i);
                ps.setString(1, uattr.getUser_id());ps.setString(2, uattr.getSkin_info());
                ps.setString(3, uattr.getIs_show_shortcut());ps.setString(4, uattr.getIs_background_float());
                ps.setInt(5, uattr.getBackground_float_speed());
            }

            public int getBatchSize() {
                return uAttr.size();
            }

        };
        return this.batchUpdate(sql.toString(), uAttr, ps);
    }
    
    /**
     * 批量插入用户数据
     *
     * @param users 用户集合
     * @return
     * @throws Exception
     */
    public int[] batchUpdateUsrs(final List<User> users) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update core_user set edit_time=?, dept_id=?, team_id=?, user_code=?, user_name=?, password=?, ");
        sql.append("valid_type=?, sex=?, card_type=?, card_id=?, birthday=?, work_date=?, folk=?, degree=?, qualified=?, ");
        sql.append("graduateSchool=?, phone=?, email=?, address=?, remark=?, creator=?, create_time=?, editor=?, identity=? ");
        sql.append("where user_id=?");
        
        BatchPreparedStatementSetter ps = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                User user = users.get(i);
                ps.setDate(1, DateUtils.switchUDateToSDate(user.getEdit_time()));
                ps.setString(2, user.getDept_id());
                ps.setString(3, user.getTeam_id());ps.setString(4, user.getUser_code());
                ps.setString(5, user.getUser_name());ps.setString(6, user.getPassword());
                ps.setString(7, user.getValid_type());ps.setString(8, user.getSex());
                ps.setString(9, user.getCard_type());ps.setString(10, user.getCard_id());
                ps.setDate(11, DateUtils.switchUDateToSDate(user.getBirthday()));
                ps.setDate(12, DateUtils.switchUDateToSDate(user.getWork_date()));
                ps.setString(13, user.getFolk());ps.setString(14, user.getDegree());
                ps.setString(15, user.getQualified());ps.setString(16, user.getGraduateSchool());
                ps.setString(17, user.getPhone());ps.setString(18, user.getEmail());
                ps.setString(19, user.getAddress());ps.setString(20, user.getRemark());
                ps.setString(21, user.getCreator());ps.setDate(22, DateUtils.switchUDateToSDate(user.getCreate_time()));
                ps.setString(23, user.getEditor());ps.setString(24, user.getIdentity());
                ps.setString(25, user.getUser_id());
            }

            public int getBatchSize() {
                return users.size();
            }

        };
        return this.batchUpdate(sql.toString(), users, ps);
    }

    /**
     * 设置用户默认快捷方式
     *
     * @param userId 用户编号
     * @return 影响的记录条数
     * @throws Exception
     */
    public int setUserDefaultShortcutLimit(String userId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into core_user_shortcut_limit (user_id, limit_id) ");
        sql.append("select ?, limit_id from core_default_shortcut_limit");
        return this.update(sql.toString(), userId);
    }

    /**
     * 获取用户详细信息
     *
     * @param userId 用户编号
     * @return 用户对象
     * @throws Exception 抛出全部异常
     */
    public User getUserById(String userId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select u.*, d.dept_name, ct.team_name, uc.user_name creator_name, ue.user_name editor_name ");
        sql.append("from core_user u ");
        sql.append("left join core_dept d on u.dept_id=d.dept_id ");
        sql.append("left join core_team ct on u.team_id=ct.team_id ");
        sql.append("left join core_user uc on u.creator=uc.user_id ");
        sql.append("left join core_user ue on u.editor=ue.user_id ");
        sql.append("where u.user_id=?");
        return this.queryForObject(sql.toString(), new ClassRowMapper<User>(User.class), userId);
    }

    /**
     * 获取用户ID信息
     *
     * @param userCode 用户编号
     * @return 用户对象
     * @throws Exception 抛出全部异常
     */
    public User getUserByCode(String userCode) throws Exception {
        String sql = "select u.* from core_user u where u.user_code =?";
        return this.queryForObject(sql, new ClassRowMapper<User>(User.class), userCode);
    }

    /**
     * 执行更新用户
     *
     * @param user 用户对象
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int updateUser(User user) throws Exception {
        StringBuffer sql = new StringBuffer();
        StringBuilder str = new StringBuilder();
        updateSql(str, User.class, user);
        sql.append("update core_user set ").append(str);
        sql.append("where user_id = ${user_id }");
        return this.updateObject(sql.toString(), user);
    }

    /**
     * 执行删除用户
     *
     * @param userIds 用户编号列表
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int deleteUser(String userIds) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from core_user where user_id in (").append(SqlUtils.getArgsKey(userIds, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(userIds, ",").toArray());
    }

    /**
     * 重置用户密码
     *
     * @param userId 用户编号
     * @param password 密码
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int resetPassword(String userId, String password) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update core_user set password=? where user_id=?");
        return this.update(sql.toString(), password, userId);
    }

    /**
     * 获取用户关联的角色数据
     *
     * @param pager 分页对象
     * @param request 请求对象
     * @param response 响应对象
     * @param userId 用户编号
     * @return 角色列表
     * @throws Exception 抛出全部异常
     */
    public void getUserRoleList(Pager pager, HttpServletRequest request,
            HttpServletResponse response, String userId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select r.*, s.system_name, uc.user_name creator_name, ue.user_name editor_name, ");
        sql.append("case when ur.user_id is null then 0 else 1 end checked ");
        sql.append("from core_role r ");
        sql.append("left join core_system s on r.system_id=s.system_id ");
        sql.append("left join core_user uc on r.creator=uc.user_id ");
        sql.append("left join core_user ue on r.editor=ue.user_id ");
        sql.append("left join core_user_role ur on r.role_id=ur.role_id and ur.user_id=?");
        GridUtils.queryForGrid(this, sql.toString(), pager, request, response, userId);
    }

    /**
     * 删除用户的原角色列表
     *
     * @param userId 用户编号
     * @param roleIds 角色编号列表
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int deleteUserRoleRelation(String userId, String roleIds) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from core_user_role where user_id=? and role_id in (").append(SqlUtils.getArgsKey(roleIds, ",")).append(")");
        List<Object> args = SqlUtils.getArgsValue(roleIds, ",");
        args.add(0, userId);
        return this.update(sql.toString(), args.toArray());
    }

    /**
     * 新增用户角色关系
     *
     * @param userId 用户编号
     * @param roleIds 角色编号列表
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int insertUserRoleRelation(String userId, String roleIds) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into core_user_role (user_id, role_id) ");
        sql.append("(select ?, role_id from core_role where role_id in (").append(SqlUtils.getArgsKey(roleIds, ",")).append("))");
        List<Object> args = SqlUtils.getArgsValue(roleIds, ",");
        args.add(0, userId);
        return this.update(sql.toString(), args.toArray());
    }

    /**
     * 获取用户参数信息
     *
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
     * 执行更新用户参数
     *
     * @param userAttr 用户参数对象
     * @return 影响的记录条数
     * @throws Exception
     */
    public int updateUserAttr(UserAttr userAttr) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update core_user_attr set skin_info=${skin_info }, is_show_shortcut=${is_show_shortcut }, ");
        sql.append("default_is_background_float=${is_background_float }, default_background_float_speed=${background_float_speed } ");
        sql.append("where user_id=${user_id }");
        return this.updateObject(sql.toString(), userAttr);
    }

    /**
     * 新建用户参数
     *
     * @param userAttr 用户参数对象
     * @return 影响的记录条数
     * @throws Exception
     */
    public int insertUserAttr(UserAttr userAttr) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into core_user_attr (user_id, skin_info, is_show_shortcut, is_background_float, background_float_speed) ");
        sql.append("values (${user_id }, ${skin_info }, ${is_show_shortcut }, ${is_background_float }, ${background_float_speed })");
        return this.updateObject(sql.toString(), userAttr);
    }

    /**
     * 获取用户的快捷方式菜单
     *
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
     * 更新用户快捷方式菜单
     *
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
     * 选择用户获取用户列表数据（按部门）
     *
     * @return 用户列表数据
     * @throws Exception
     */
    public List<Map<String, Object>> getSelectUserDataByDept() throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from (");
        sql.append("	select u.user_id id, u.user_name name, concat('dept_', u.dept_id) pid, 0 sort, 'user' type, u.* ");
        sql.append("	from core_user u ");
        sql.append("	union all ");
        sql.append("	select concat('dept_', d.dept_id) id, d.dept_name name, concat('dept_', d.pre_dept_id) pid, d.sort, 'dept' type, ");
        sql.append("	null user_id, null dept_id, null team_id, null user_code, null user_name, null password, null valid_type, null identity, ");
        sql.append("	null sex, null card_type, null card_id, null birthday, null work_date, null imgpath, null folk, null degree, null qualified, null graduateSchool, ");
        sql.append("	null phone, null email, null address, null remark, null creator, null create_time, null editor, null edit_time ");
        sql.append("	from core_dept d ");
        sql.append(") t order by t.sort");
        return this.queryForList(sql.toString());
    }

}
