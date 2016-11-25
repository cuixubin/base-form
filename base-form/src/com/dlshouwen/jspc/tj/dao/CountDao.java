/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.jspc.tj.dao;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.stereotype.Component;

/**
 * 统计Dao
 *
 * @author haohao
 */
@Component("countDao")
public class CountDao extends BaseDao {

    //注入数据源
    @Resource(name = "defaultDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    //查询列表
    public void getCountList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select cu.user_name user_name,ct.team_name team_name,jj.name name,jj.createDate createDate,jj.`level` level1,jj.grade grade,jj.attach attach,jj.creator creator " +
                    "from jspc_evalitem jj " +
                    "left join core_user cu on cu.user_id = jj.creator " +
                    "left join core_team ct on cu.team_id = ct.team_id  order by jj.createDate desc");
        GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
    }
    /**
     * 获取统计列表数据
     * @param dataType
     * @param params
     * @return
     * @throws Exception 
     */
    public List<Map<String, Object>> getChartDataList(String dataType, Map<String, Object> params) throws Exception {
                StringBuffer sql = new StringBuffer();
		
                String sql1 = (String) params.get("sqlString");
                String params1 =  (String) params.get("params");
                if(sql1 == null || sql1.trim().length()==0){
                    sql1 = "jspc_evalitem";
                }
                String[] obj = {};
                if(params1 != null && params1.trim().length()>0){
                    obj = params1.split(",");
                }
//		按团队
		if("team".equals(dataType)){
			sql.append("select IFNULL(cct.team_name,'空') category_field_name, count(*) data_count ");
			sql.append("from (").append(sql1).append(") j");
			sql.append("	left join core_user u on u.user_id = j.creator ");
			sql.append("	left join core_team cct on u.team_id = cct.team_id ");
			sql.append("group by cct.team_id");
		}
//		按发布年份
		if("create_year".equals(dataType)){
			sql.append("select date_format(j.createDate, '%Y') category_field_name, count(*) data_count ");
			sql.append("from (").append(sql1).append(") j");
			sql.append("	left join core_user u on u.user_id = j.creator ");
			sql.append("	left join core_team cct on u.team_id = cct.team_id ");
			sql.append("group by date_format(j.createDate, '%Y') order by date_format(j.createDate, '%Y')");
		}
//		按等级
		if("level".equals(dataType)){
			sql.append("select IFNULL(j.level1,'空') category_field_name, count(*) data_count ");
			sql.append("from (").append(sql1).append(") j");
			sql.append("	left join core_user u on u.user_id = j.creator ");
			sql.append("	left join core_team cct on u.team_id = cct.team_id ");
			sql.append("group by j.level1 order by j.level1");
		}
//		按级别
		if("grade".equals(dataType)){
			sql.append("select IFNULL(j.grade,'空') category_field_name, count(*) data_count ");
			sql.append("from (").append(sql1).append(") j");
			sql.append("	left join core_user u on u.user_id = j.creator ");
			sql.append("	left join core_team cct on u.team_id = cct.team_id ");
			sql.append("group by j.grade order by j.grade");
		}
//		按人员分布
		if("person".equals(dataType)){
			sql.append("select IFNULL(u.user_name,'空') category_field_name, count(*) data_count ");
			sql.append("from (").append(sql1).append(") j");
			sql.append("	left join core_user u on u.user_id = j.creator ");
			sql.append("	left join core_team cct on u.team_id = cct.team_id ");
			sql.append("group by u.user_id ");
		}
		return this.queryForList(sql.toString(), obj);
	}
}
