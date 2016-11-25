package com.dlshouwen.core.team.dao;
 
import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.core.team.model.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author cui
 */
/** 需要配置@Component注解定义数据库连接组件名称 */
@Component("teamDao")
public class TeamDao extends BaseDao {
        /**
        * 注入数据源
        * @param dataSource 数据源对象
        */
        @Resource(name="defaultDataSource")
        public void setDataSource(DataSource dataSource){
            super.setDataSource(dataSource);
        }
        
        /**
	 * 获取团队列表
	 * @return 团队列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getTeamList() throws Exception {
            StringBuffer sql = new StringBuffer();
            sql.append("select t.* from core_team t order by t.sort");
            return this.queryForList(sql.toString());
	}
        
        /**
	 * 根据条件获取团队列表
	 * @return 团队列表
	 * @throws Exception 抛出全部异常
	 */
	public List<Map<String, Object>> getTeamList(String identity, String teamId) throws Exception {
            StringBuffer sql = new StringBuffer();
            sql.append("select t.* from core_team t ");
            if(StringUtils.isNotEmpty(identity) && identity.equals("1")) {
                if(StringUtils.isEmpty(teamId)) {
                    sql.append("order by t.sort ");
                    return this.queryForList(sql.toString());
                }else {
                    sql.append("where t.team_id=? ");
                    return this.queryForList(sql.toString(), teamId);
                }
            }else if(StringUtils.isNotEmpty(identity) && identity.equals("0") && StringUtils.isNotEmpty(teamId)) {
                sql.append("where t.team_id=?");
                return this.queryForList(sql.toString(), teamId);
            }
            return new ArrayList<Map<String, Object>>();
	}
        
        /**
	 * 获取团队名称列表
	 * @return 团队名称列表
	 * @throws Exception 抛出全部异常
	 */
	public List<String> getTeamNameList() throws Exception {
            StringBuffer sql = new StringBuffer();
            sql.append("select CONCAT(t.team_name,'____',t.team_id) from core_team t where t.pre_team_id != 'top' order by t.sort");
            return this.queryForList(sql.toString(), String.class);
	}
        
        /**
	 * 获取团队信息
	 * @param teamId 团队编号
	 * @return 团队信息
	 * @throws Exception 抛出全部异常
	 */
	public Team getTeamById(String teamId) throws Exception {
            StringBuffer sql = new StringBuffer();
            sql.append("select t.*, uc.user_name creator_name, ue.user_name editor_name ");
            sql.append("from core_team t ");
            sql.append("left join core_user uc on t.creator=uc.user_id ");
            sql.append("left join core_user ue on t.editor=ue.user_id ");
            sql.append("where t.team_id=?");
            return this.queryForObject(sql.toString(), new ClassRowMapper<Team>(Team.class), teamId);
	}
        
        /**
	 * 新增团队
	 * @param team 团队对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertTeam(Team team) throws Exception {
             StringBuffer sql = new StringBuffer();
            sql.append("insert into core_team (team_id, pre_team_id, team_name, principal, principal_phone, ");
            sql.append("sort, remark, creator, create_time, editor, edit_time,team_path) ");
            sql.append("values (${team_id }, ${pre_team_id }, ${team_name }, ${principal }, ${principal_phone }, ");
            sql.append("${sort }, ${remark }, ${creator }, ${create_time }, ${editor }, ${edit_time },${team_path})");
        return this.updateObject(sql.toString(), team);
	}
        
        /**
	 * 更新团队
	 * @param team 团队对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateTeam(Team team) throws Exception {
            StringBuilder sql = new StringBuilder(); 
            StringBuilder str = new StringBuilder();
            updateSql(str, Team.class, team);
            sql.append("update core_team set ").append(str);
            sql.append("where team_id=${team_id }");
            return this.updateObject(sql.toString(), team);
	}
	
	/**
	 * 获取子菜单个数
	 * @param teamId 团队编号
	 * @return 子菜单个数
	 * @throws Exception 抛出全部异常
	 */
	public int getSubTeamCount(String teamId) throws Exception {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) from core_team o where o.pre_team_id=?");
            return this.queryForInt(sql.toString(), teamId);
	}

	/**
	 * 删除团队
	 * @param teamIds 团队编号列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int deleteTeam(String teamIds) throws Exception {
            StringBuffer sql = new StringBuffer();
            sql.append("delete from core_team where team_id in (").append(SqlUtils.getArgsKey(teamIds, ",")).append(")");
            return this.update(sql.toString(), SqlUtils.getArgsValue(teamIds, ",").toArray());
	}

	/**
	 * 团队是否包含人员
	 * @param teamId 团队编号
	 * @return 是否包含人员
	 * @throws Exception 抛出全部异常
	 */
	public boolean getTeamIsHaveUser(String teamId) throws Exception {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(*) from core_team_user where team_id=?");
            return this.queryForInt(sql.toString(), teamId)>0?true:false;
	}
}
