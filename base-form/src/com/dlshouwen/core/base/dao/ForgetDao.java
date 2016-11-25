package com.dlshouwen.core.base.dao;

import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.system.model.User;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

/**
 * 登陆
 *
 * @author 刘精诚
 * @since 2013-7-15 13:48:10
 */
@Component("forgetDao")
public class ForgetDao extends BaseDao {

    /**
     * 注入数据源
     *
     * @param dataSource 数据源对象
     */
    @Resource(name = "defaultDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    public User findByLoginNameAndEmail(String loginname, String email) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select u.*");
        sql.append("from core_user u ");
        sql.append("where u.user_code=? and u.email=?");
        return this.queryForObject(sql.toString(), new ClassRowMapper<User>(User.class), loginname, email);
    }

    public User findByNameAndPhone(String loginName, String phoneNum) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select u.*");
        sql.append("from core_user u ");
        sql.append("where u.user_code=? and u.phone=?");
        return this.queryForObject(sql.toString(), new ClassRowMapper<User>(User.class), loginName, phoneNum);
    }

    public User findByLoginName(String loginName) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select u.*");
        sql.append("from core_user u ");
        sql.append("where u.user_code=?");
        return this.queryForObject(sql.toString(), new ClassRowMapper<User>(User.class), loginName);
    }

    public int updateUserPass(User user) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update core_user set password=${password} ");
        sql.append("where user_code=${user_code}");
        return this.updateObject(sql.toString(),user);
    }
}
