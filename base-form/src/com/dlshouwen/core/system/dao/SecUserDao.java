package com.dlshouwen.core.system.dao;

import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;

/**
 * 用户管理
 *
 * @author 大连首闻科技有限公司
 * @version 2013-8-8 17:15:45
 */
@Component("secUserDao")
public class SecUserDao extends BaseDao {

    /**
     * 注入数据源
     *
     * @param dataSource 数据源对象
     */
    @Resource(name = "securityDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
    /**
     * 根据登录名和密码进行查询
     * @param loginName
     * @param pass
     * @return 
     */
    public boolean queryUserByLoginNameAndPass(String loginName, String pass) {
        String sql = "select count(u.id) from sec_users u where u.loginname = '" + loginName + "' and u.password = '" + pass + "'";
        Integer number = this.queryForIntNoLog(sql);
        if (null != number && number > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据登录名和code查询权限表中用户是否存在
     *
     * @param loginName
     * @param code
     * @return true存在，false不存在
     */
    public boolean queryUserByLoginNameAndCode(String loginName, String code) {
        String sql = "select count(u.id) from sec_users u where u.loginname = '" + loginName + "' and u.code = '" + code + "'";
        Integer number = this.queryForIntNoLog(sql);
        if (null != number && number > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据登录名查询权限表中用户是否存在
     *
     * @param loginName
     * @return true存在，false不存在
     */
    public boolean queryUserByLoginName(String loginName) {
        String sql = "select count(u.id) from sec_users u where u.loginname = '" + loginName + "'";
        Integer number = this.queryForIntNoLog(sql);
        if (null != number && number > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 执行新增用户
     *
     * @param user 需要被新增的用户对象
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public void insertUser(Map user) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into sec_users (name, loginname, password, isactive, note, editman, edittime, code, phone, email) ");
        sql.append("values ('" + user.get("name") + "', '" + user.get("loginname") + "', '" + user.get("password") + "', " + user.get("isactive") + ", '"
                + user.get("note") + "', '" + user.get("editman") + "', now(), '" + user.get("code") + "', '" + user.get("phone") + "', '" + user.get("email") + "')");
        this.executeNoLog(sql.toString());
    }

    /**
     * 执行更新用户
     *
     * @param user 需要被新增的用户对象
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public void updateUser(Map user) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update sec_users u set ");
        sql.append("name='" + user.get("name") + "', isactive='" + user.get("isactive") + "', editman='" + user.get("editman") 
                + "', edittime=now(), code='" + user.get("code") + "', phone='" + user.get("phone") + "', email='" + user.get("email") + "' ");
        sql.append("where u.LOGINNAME='" + user.get("loginname") + "'");
        this.executeNoLog(sql.toString());
    }
}
