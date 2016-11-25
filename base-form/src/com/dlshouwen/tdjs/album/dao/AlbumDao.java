/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.tdjs.album.dao;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.tdjs.album.model.Album;
import com.dlshouwen.tdjs.picture.model.Picture;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 相册Dao
 *
 * @author yangtong
 */
@Component("TdjsAlbumDao")
public class AlbumDao extends BaseDao {

    //注入数据源
    @Resource(name = "defaultDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    //查询列表
    public void getAlbumList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select ta.*, team.team_name team_name ");
        sql.append("from tdjs_album ta left join core_team team on team.team_id = ta.team_id ");
        sql.append("order by ta.album_createdate desc");
        GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
    }

    //根据用户信息查询列表
    public void getAlbumList(Pager pager, HttpServletRequest request, HttpServletResponse response, 
            String teamId, String userId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select ta.*, team.team_name team_name ");
        sql.append("from tdjs_album ta left join core_team team on team.team_id = ta.team_id ");
        if(StringUtils.isNotEmpty(userId)) {
            if(StringUtils.isNotEmpty(teamId)) {
                sql.append("where ta.team_id = ? and ta.album_createuser = ? ");
                sql.append("order by ta.album_createdate desc");
                GridUtils.queryForGrid(this, sql.toString(), pager, request, response, teamId, userId);
            }
        }else {
            sql.append("where ta.team_id = ? ");
            sql.append("order by ta.album_createdate desc");
            GridUtils.queryForGrid(this, sql.toString(), pager, request, response, teamId);
        }
    }

    /**
     * 新增相册
     *
     * @param album 相册对象
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int insertAlbum(Album album) throws Exception {
        StringBuffer sql = new StringBuffer();
        StringBuilder into = new StringBuilder();
        StringBuilder values = new StringBuilder();
        insertSql(into, values, Album.class);
        sql.append("insert into tdjs_album (").append(into.toString()).append(") ");
        sql.append("values (").append(values.toString()).append(")");
        return this.updateObject(sql.toString(), album);
    }

    /**
     * 根据主键获取相册
     *
     * @param albumId 相册主键
     * @return 相册对象
     * @throws Exception 抛出全部异常
     */
    public Album getAlbumById(String albumId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select ta.*, team.team_name team_name ");
        sql.append("from tdjs_album ta left join core_team team on team.team_id = ta.team_id where ta.album_id =?");
        return this.queryForObject(sql.toString(), new ClassRowMapper<Album>(Album.class), albumId);
    }

    //更新相册
    public int updateAlbum(Album album) throws Exception {
        StringBuilder sql = new StringBuilder();
        StringBuilder str = new StringBuilder();
        updateSql(str, Album.class, album);
        sql.append("update tdjs_album set ").append(str);

        sql.append("where album_id=${album_id }");
        return this.updateObject(sql.toString(), album);
    }

    //删除相册
    public int deleteAlbum(String albumIds) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from tdjs_album where album_id in (").append(SqlUtils.getArgsKey(albumIds, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(albumIds, ",").toArray());
    }

    //根据相册id查询图片

    public List<Picture> findPicByAlbumId(String albumId) {
        StringBuffer sql = new StringBuffer();
        RowMapper<Picture> rm = ParameterizedBeanPropertyRowMapper.newInstance(Picture.class);
        sql.append("select * from tdjs_picture where album_id =? order by `order` asc ");
        return this.query(sql.toString(), rm, albumId);
    }

    //为相册设置封面
    public int setAlbCover(String albumId, String albCoverpath) {
        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_album set album_coverpath =? where album_id=? ");
        return this.update(sql.toString(),albCoverpath, albumId);
    }
    
      /**
       * 根据相册id 删除图片
       * @param albumId
       * @return
       * @throws Exception 
       */  
      public int deletePicByAlbId(String albumId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from tdjs_picture where album_id in (").append(SqlUtils.getArgsKey(albumId, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(albumId, ",").toArray());
    }
    
}
