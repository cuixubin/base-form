/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.tdjs.channel.dao;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;

import com.dlshouwen.tdjs.channel.model.Channel;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author admin
 */
@Component("TdjsChannelDao")
public class ChannelDao extends BaseDao {

    //注入数据源
    @Resource(name = "defaultDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    //查询栏目的列表
    public List<Map<String, Object>> getChannelList() throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select bc.* ");
        sql.append("from tdjs_channel bc ");
        sql.append("left join core_team team on team.team_id=bc.team_id ");
        sql.append("order by bc.channel_seq ");
        return this.queryForList(sql.toString());
    }
    
    /**
     * 根据用户身份和团队id获取栏目记录
     * @param identity 用户身份
     * @param teamId 团队id
     * @return 
     */
    public List<Map<String, Object>> getRecordsByIdentityAndTeamId(String identity, String teamId) {
        StringBuffer sql = new StringBuffer();
        sql.append("select bc.* ");
        sql.append("from tdjs_channel bc ");
        if(StringUtils.isNotEmpty(identity) && identity.equals("1")) {
            if(StringUtils.isEmpty(teamId)) {
                sql.append("order by bc.channel_seq ");
                return this.queryForList(sql.toString());
            }else {
                sql.append("where bc.team_id =? ");
                sql.append("order by bc.channel_seq ");
                return this.queryForList(sql.toString(), teamId);
            }
        }else if(StringUtils.isNotEmpty(identity) && identity.equals("0") && StringUtils.isNotEmpty(teamId)) {
            sql.append("where bc.team_id =? ");
            sql.append("order by bc.channel_seq ");
            return this.queryForList(sql.toString(), teamId);
        }else {
            sql.append("where 1>2");
        }
        
        return this.queryForList(sql.toString());
    }

    //新增栏目
    public int insertChannel(Channel channel) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into tdjs_channel ");
        sql.append("(channel_id,channel_name,channel_description,channel_seq,channel_parent,channel_state,channel_createuser,channel_createdate,channel_updatedate,channel_url,channel_type,channel_picUrl,channel_edituser,team_id) ");
        sql.append("values(${channel_id},${channel_name},${channel_description},${channel_seq},${channel_parent},${channel_state},${channel_createUser},${channel_createDate},${channel_updateDate},${channel_url},${channel_type},${channel_picUrl},${channel_edituser},${team_id}) ");
        return this.updateObject(sql.toString(), channel);
    }

    //编辑栏目
    public int updateChannel(Channel channel) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_channel set ");
        sql.append("channel_id=${channel_id},channel_name=${channel_name},channel_description=${channel_description},channel_seq=${channel_seq}, ");
        sql.append("channel_parent=${channel_parent},channel_state=${channel_state},channel_createuser=${channel_createUser},channel_createdate=${channel_createDate}, ");
        sql.append("channel_updatedate=${channel_updateDate},channel_url=${channel_url},channel_type=${channel_type},channel_picUrl=${channel_picUrl},channel_edituser=${channel_edituser},team_id=${team_id} ");
        sql.append("where channel_id=${channel_id}");
        return this.updateObject(sql.toString(), channel);
    }

    //子栏目个数
    public int getSubChannelCount(String channelId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(1) from tdjs_channel bc where bc.channel_parent=? ");
        return this.queryForInt(sql.toString(), channelId);
    }

    //根据id 查询栏目
    public Channel getChannelById(String channelId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select bc.* , uc.user_name creator_name, ue.user_name editor_name, team.team_name ");
        sql.append("from tdjs_channel bc ");
        sql.append("left join core_user uc on bc.channel_createUser=uc.user_id ");
        sql.append("left join core_user ue on bc.channel_edituser=ue.user_id ");
        sql.append("left join core_team team on team.team_id=bc.team_id ");
        sql.append("where bc.channel_id=?");

        return this.queryForObject(sql.toString(), new ClassRowMapper<Channel>(Channel.class), channelId);
    }

    //删除栏目，异常全部抛出去
    public int deleteChannel(String channelIds) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from tdjs_channel where channel_id in (").append(SqlUtils.getArgsKey(channelIds, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(channelIds, ",").toArray());
    }

    //查询栏目下是否有内容
    public boolean getChannelArticle(String channelId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) from tdjs_article where channel_id=? ");
        return this.queryForInt(sql.toString(), channelId) > 0 ? true : false;

    }

    //查询一级栏目
    public List<Map<String, Object>> getFirstChannel() throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select tc.* ");
        sql.append("from tdjs_channel tc ");
        sql.append("where tc.channel_parent = 'top' ");
        return this.queryForList(sql.toString());
    }
    
    /**
     * 根据团队id 查询团队下边的栏目
     * @param teamId
     * @return
     * @throws Exception 
     */
      public List<Map<String, Object>> getChannelListByTeamId(String teamId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select bc.* ");
        sql.append("from tdjs_channel bc ");
        sql.append("where bc.team_id=? ");
        return this.queryForList(sql.toString(),teamId);
    }
    
    
    
}
