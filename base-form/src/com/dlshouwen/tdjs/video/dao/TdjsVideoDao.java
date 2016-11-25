/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.tdjs.video.dao;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.tdjs.video.model.Video;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 视频Dao
 *
 * @author yangtong
 */
@Repository
public class TdjsVideoDao extends BaseDao {

    //注入数据源
    @Resource(name = "defaultDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    //查询所有团队的视频记录
    public void getVideoList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select tv.*, team.team_name from tdjs_video tv ");
        sql.append("left join core_team team  on team.team_id = tv.team_id ");
        sql.append("order by vd_uploaddate desc ");
        GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
    }

    //根据条件查询视频列表
    public void getVideoList(Pager pager, HttpServletRequest request, HttpServletResponse response, 
            String teamId, String userId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select tv.*, team.team_name from tdjs_video tv ");
        sql.append("left join core_team team  on team.team_id = tv.team_id ");
        if(StringUtils.isNotEmpty(userId)) {
            if(StringUtils.isNotEmpty(teamId)) {
                sql.append("where tv.team_id = ? and tv.vd_uploaduser = ? ");
                sql.append("order by vd_uploaddate desc ");
                GridUtils.queryForGrid(this, sql.toString(), pager, request, response, teamId, userId);
            }
        }else {
            sql.append("where tv.team_id = ? ");
            sql.append("order by vd_uploaddate desc ");
            GridUtils.queryForGrid(this, sql.toString(), pager, request, response, teamId);
        }
    }

    /**
     * 新增视频
     *
     * @param video 视频对象
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int insertVideo(Video video) throws Exception {
        StringBuffer sql = new StringBuffer();
        StringBuilder into = new StringBuilder();
        StringBuilder values = new StringBuilder();
        insertSql(into, values, Video.class);
        sql.append("insert into tdjs_video (").append(into.toString()).append(") ");
        sql.append("values (").append(values.toString()).append(")");
        return this.updateObject(sql.toString(), video);
    }

    /**
     * 根据主键获取视频
     *
     * @param videoId 视频主键
     * @return 视频对象
     * @throws Exception 抛出全部异常
     */
    public Video getVideoById(String videoId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from tdjs_video tv " );
        sql.append("left join core_team team on team.team_id = tv.team_id ");
        sql.append("where tv.vd_id =?");
        return this.queryForObject(sql.toString(), new ClassRowMapper<Video>(Video.class), videoId);
    }

    //更新视频
    public int updateVideo(Video video) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("update tdjs_video set vd_name=${vd_name},vd_isdisplay=${vd_isdisplay},vd_description=${vd_description},vd_updateuser=${vd_updateuser},vd_updatedate=${vd_updatedate},vd_order=${vd_order},team_id=${team_id}  ");
        sql.append("where vd_id=${vd_id }");
        return this.updateObject(sql.toString(), video);
    }

    //删除视频
    public int deleteVideo(String videoIds) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from tdjs_video where vd_id in (").append(SqlUtils.getArgsKey(videoIds, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(videoIds, ",").toArray());
    }

    //开启视频
    public int openVideo(String videoIds) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_video set vd_isdisplay='1' where vd_id in (").append(SqlUtils.getArgsKey(videoIds, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(videoIds, ",").toArray());
    }
    //关闭视频

    public int closeVideo(String videoIds) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_video set vd_isdisplay='0' where vd_id in (").append(SqlUtils.getArgsKey(videoIds, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(videoIds, ",").toArray());
    }
     //发布视频
    public int updatePublishVideo(Video video) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("update tdjs_video set vd_status=${vd_status},vd_savepath=${vd_savepath},vd_thumbnailspath=${vd_thumbnailspath}");
        sql.append("where vd_id=${vd_id }");
        return this.updateObject(sql.toString(), video);
    }
    
}
