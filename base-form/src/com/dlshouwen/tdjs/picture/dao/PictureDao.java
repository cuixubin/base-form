/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.tdjs.picture.dao;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.tdjs.picture.model.Picture;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 大图管理
 *
 * @author xlli
 */
@Component("TdjsPictureDao")
public class PictureDao extends BaseDao {

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
     * 获取图片列表
     *
     * @param pager 分页对象
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    public void getPictureList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select tp.*, team.team_name ");
        sql.append("from tdjs_picture tp ");
        sql.append("left join core_team team on team.team_id = tp.team_id ");
        sql.append("order by create_time desc");
        GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
    }

    /**
     * 根据条件获取图片列表
     *
     * @param pager 分页对象
     * @param request 请求对象
     * @param response 响应对象
     * @param teamId 团队id
     * @param userId 用户id
     * @throws Exception 抛出全部异常
     */
    public void getPictureList(Pager pager, HttpServletRequest request, HttpServletResponse response, 
            String teamId, String userId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select tp.*, team.team_name ");
        sql.append("from tdjs_picture tp ");
        sql.append("left join core_team team on team.team_id = tp.team_id ");
        if(StringUtils.isNotEmpty(userId)) {
            if(StringUtils.isNotEmpty(teamId)) {
                sql.append("where tp.team_id = ? and tp.user_id = ? ");
                sql.append("order by create_time desc");
                GridUtils.queryForGrid(this, sql.toString(), pager, request, response, teamId, userId);
            }
        }else {
            sql.append("where tp.team_id = ? ");
            sql.append("order by create_time desc");
            GridUtils.queryForGrid(this, sql.toString(), pager, request, response, teamId);
        }
    }

    /**
     * 通过ID查找图片
     *
     * @param pictureId 图片ID
     * @return 图片对象
     * @throws Exception 抛出全部异常
     */
    public Picture getPictureById(String pictureId) throws Exception {

        //要执行的sql
        String sql = " SELECT * FROM tdjs_picture tp left join core_team team  on team.team_id = tp.team_id WHERE picture_id = ?";

        return this.queryForObject(sql, new ClassRowMapper<Picture>(Picture.class), pictureId);
    }

    /**
     * 通过ID查找图片
     *
     * @param pictureId 图片ID
     * @return 图片对象
     * @throws Exception 抛出全部异常
     */
    public List<Picture> getPictureByAlbumId(String albumId) throws Exception {

        //要执行的sql
        String sql = " SELECT * FROM tdjs_picture tp WHERE tp.album_id = ?";

        return this.queryForList(sql, new ClassRowMapper<Picture>(Picture.class), albumId);
    }

    /**
     * 添加图片
     *
     * @param pic 图片对象
     * @return 影响的行数
     * @throws Exception 抛出全部异常
     */
    public int insertPicture(Picture pic) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into tdjs_picture (picture_id, picture_name, description, create_time, user_id, user_name, album_id, flag, ");
        sql.append("path, `order`, `show`,team_id ) ");
        sql.append("values (${picture_id }, ${picture_name }, ${description }, ${create_time }, ${user_id }, ${user_name}, ${album_id}, ${flag }, ");
        sql.append("${path }, ${order }, ${show },${team_id} )");
        return this.updateObject(sql.toString(), pic);
    }

    /**
     * 更新图片
     *
     * @param pic 图片对象
     * @return 影响的记录数
     * @throws Exception 抛出全部异常
     */
    public int updatePicture(Picture pic) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_picture set picture_name=${picture_name }, description=${description }, update_time=${update_time}, ");
        sql.append("album_id=${album_id}, flag=${flag }, path=${path }, `order`=${order }, `show`=${show } ,team_id=${team_id} ");
        sql.append("where picture_id=${picture_id }");
        return this.updateObject(sql.toString(), pic);
    }

    /**
     * 删除图片
     *
     * @param pictureId 图片ID列表
     * @return 影响的行数
     * @throws Exception 抛出全部异常
     */
    public int deletePicture(String pictureId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from tdjs_picture where picture_id in (").append(SqlUtils.getArgsKey(pictureId, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(pictureId, ",").toArray());
    }

    /**
     * 展示图片
     *
     * @param pictureIds 图片列表编号
     * @return 影响的行数
     */
    public int showPicture(String pictureIds) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_picture set `show`='1' where picture_id in (").append(SqlUtils.getArgsKey(pictureIds, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(pictureIds, ",").toArray());

    }

    /**
     * 隐藏图片
     *
     * @param pictureIds 图片列表编号
     * @return 影响的行数
     */
    public int hidePicture(String pictureIds) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_picture set `show`='0' where picture_id in (").append(SqlUtils.getArgsKey(pictureIds, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(pictureIds, ",").toArray());

    }

    //为相册设置封面
    public int updatePicDescription(String picId, String pic_description) {
        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_picture set description =? where picture_id=? ");
        return this.update(sql.toString(), pic_description, picId);
    }
    
    //图片排序

    /**
     *
     * @param order
     * @param picId
     * @return
     */
    public int[] updatePicOrder(List<Object[]> args){
        //定义个数组，长度和传递来的参数的长度一样
        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_picture  set `order` =? where  picture_Id =?");
        
        return this.batchUpdate(sql.toString(), args);
    }
}
