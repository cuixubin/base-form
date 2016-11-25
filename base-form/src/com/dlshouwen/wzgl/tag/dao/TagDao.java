/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.wzgl.tag.dao;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.wzgl.tag.model.Tag;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author admin
 */
@Component("TagDao")
public class TagDao extends BaseDao {

    //注入数据源
    @Resource(name = "defaultDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    //查询列表
    public void getTagList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from wzgl_tag order by tag_addtime  ");
        GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
    }

    /**
     * 新增标签
     *
     * @param tag 标签对象
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int insertTag(Tag tag) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into wzgl_tag (tag_id,tag_name,tag_styledescription,tag_url,tag_addtime,tag_adduser,tag_edittime,tag_edituser,tag_state) ");
        sql.append("values (${tag_id }, ${tag_name }, ${tag_styledescription }, ${tag_url }, ${tag_addtime }, ${tag_adduser }, ${tag_edittime },${tag_edituser },${tag_state } )");
        return this.updateObject(sql.toString(), tag);
    }

    /**
	 * 根据主键获取标签
	 * @param tagId 标签主键
	 * @return 标签对象
	 * @throws Exception 抛出全部异常
	 */
	public Tag getTagById(String tagId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from wzgl_tag  where tag_id =?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<Tag>(Tag.class), tagId);
	}
    
        
        //更新标签
        public int updateTag(Tag tag) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update wzgl_tag set tag_name=${tag_name }, tag_styledescription=${tag_styledescription },tag_url=${tag_url }, tag_addtime=${tag_addtime }, tag_adduser=${tag_adduser } ,tag_edituser=${tag_edituser }, tag_edituser=${tag_edituser }, tag_state=${tag_state } ");
                sql.append("where tag_id=${tag_id }");
		return this.updateObject(sql.toString(), tag);
	}
    
        //删除标签
        public int deleteTag(String tagIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from wzgl_tag where tag_id in (").append(SqlUtils.getArgsKey(tagIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(tagIds, ",").toArray());
	}
        
        //开启标签
        public int openTag(String tagIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update wzgl_tag set tag_state='1' where tag_id in (").append(SqlUtils.getArgsKey(tagIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(tagIds, ",").toArray());
	}
           //关闭标签
        public int closeTag(String tagIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update wzgl_tag set tag_state='0' where tag_id in (").append(SqlUtils.getArgsKey(tagIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(tagIds, ",").toArray());
	}
}
