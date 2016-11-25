/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.jspc.zjpc.dao;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.jspc.zjpc.model.Expreview;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.stereotype.Component;

/**
 * 专家评审
 * @author xlli
 */
@Component("expreviewDao")
public class ExpreviewDao extends BaseDao{
    
        /**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
        
        /**
         * 获取评审列表(专家)
         * @param pager 分页对象
         * @param request 请求对象
         * @param response 响应对象
         * @throws Exception 抛出全部异常
         */
        public void getExpreviewList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer();
                //需要添加资质
		sql.append(" select * ");
		sql.append(" from jspc_expreview where status not in (3,4)");
		sql.append(" order by create_date desc");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}
        /**
         * 获取评审列表(管理员)
         * @param pager 分页对象
         * @param request 请求对象
         * @param response 响应对象
         * @throws Exception 抛出全部异常
         */
        public void getExpreviewListForAdmin(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer();
                //需要添加资质
		sql.append(" select * ");
		sql.append(" from jspc_expreview");
		sql.append(" order by create_date desc");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}
        
        /**
         * 通过id查找
         * @param id 编号
         * @return 评审对象
         * @throws Exception 抛出全部异常
         */
        public Expreview getExpreviewById(String id) throws Exception{
            
            //要执行的sql
            String sql  = " SELECT * FROM jspc_expreview WHERE id = ?";
            
            return this.queryForObject(sql, new ClassRowMapper<Expreview>(Expreview.class), id);
        }
        
        /**
         * 通过用户编号查找  返回表格形式
         * @param user_id 用户编号
         * @param pager 分页对象
         * @param request 请求对象
         * @param response 响应对象
         * @throws Exception 抛出全部异常
         */
        public void getExpreviewByUserId(String user_id,Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
            
            //要执行的sql
            String sql  = " SELECT * FROM jspc_expreview WHERE user_id = '"+user_id+"' order by create_date desc ";
            
            GridUtils.queryForGrid(this, sql, pager, request, response);
        }
        
        /**
         * 根据用户查找
         * @param user_id 用户编号
         * @return 评审对象
         * @throws Exception 抛出全部异常
         */
        public Expreview getExpreviewByUserId(String user_id) throws Exception{
            
            //要执行的sql
            String sql  = "SELECT * FROM jspc_expreview  WHERE user_id = ?";
            
            return this.queryForObject(sql, new ClassRowMapper<Expreview>(Expreview.class), user_id);
        }
        
        /**
         * 评审操作
         * @param expreview 要更新的对象
         * @return 影响的行数
         * @throws Exception 抛出所有异常
         */
	public int updateExpreview(Expreview expreview) throws Exception {
		StringBuffer sql = new StringBuffer();
                sql.append("update jspc_expreview set status = ${status}, review_date = ${review_date},review_name = ${review_name},"
                        + "note = ${note} where id = ${id}");
		return this.updateObject(sql.toString(), expreview);
	}
        
        /**
         * 删除评审
         * @param id 评审编号
         * @return 影响的行数
         * @throws Exception 抛出全部异常
         */
        public int deleteExpreview(String id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from jspc_expreview where id in (").append(SqlUtils.getArgsKey(id, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(id, ",").toArray());
	}
        
        /**
         * 添加评审
         * @param expreview 评审对象
         * @return 影响的行数
         * @throws Exception 抛出全部异常
         */
        public int insertExpreview(Expreview expreview) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into jspc_expreview (id, user_id, user_name, qualified, status, create_date ) ");
		sql.append("values (${id }, ${user_id }, ${user_name }, ${qualified }, ${status }, ${create_date} ) ");
		return this.updateObject(sql.toString(), expreview);
	}

        
    
}
