/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.wzgl.content.dao;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.wzgl.content.model.Article;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.stereotype.Component;

/**
 * 文章dao
 * @author cui
 */
@Component("articleDao")
public class ArticleDao extends BaseDao {
        /**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
        
        /**
	 * 获取文章列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	public void getArticleList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*, uc.user_name creator_name, ue.user_name editor_name, wc.channel_name channel_name ");
		sql.append("from wzgl_article a ");
		sql.append("left join core_user uc on a.creator=uc.user_id ");
		sql.append("left join core_user ue on a.editor=ue.user_id ");
                sql.append("left join wzgl_channel wc on wc.channel_id=a.channel_id ");
		sql.append("order by publish_time desc");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
	}
        
        /**
	 * 根据主键获取文章
	 * @param articleId 文章主键
	 * @return 文章对象
	 * @throws Exception 抛出全部异常
	 */
	public Article getArticleById(String articleId) throws Exception {
		StringBuffer sql = new StringBuffer();
                sql.append("select a.*, uc.user_name creator_name, ue.user_name editor_name, wc.channel_name channel_name ");
		sql.append("from wzgl_article a ");
		sql.append("left join core_user uc on a.creator=uc.user_id ");
		sql.append("left join core_user ue on a.editor=ue.user_id ");
		sql.append("left join wzgl_channel wc on wc.channel_id=a.channel_id ");
		sql.append("where a.article_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<Article>(Article.class), articleId);
	}
        
        /**
	 * 新增文章
	 * @param article 文章对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertArticle(Article article) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into wzgl_article ("
                        + "article_id, title, tabloid, content, status, topset, channel_id, templet, "
                        + "provenance, publish_time, publisher, creator, create_time, editor, edit_time) ");
		sql.append("values ("
                        + "${article_id }, ${title }, ${tabloid }, ${content }, ${status }, ${topset }, ${channel_id }, ${templet }, "
                        + "${provenance }, ${publish_time }, ${publisher }, ${creator }, ${create_time }, ${editor }, ${edit_time }"
                        + ")");
		return this.updateObject(sql.toString(), article);
	}
        
        /**
	 * 更新文章
	 * @param article 文章对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int updateArticle(Article article) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update wzgl_article set "
                        + "title=${title }, content=${content }, status=${status }, editor=${editor }, edit_time=${edit_time }, "
                        + "tabloid=${tabloid }, topset=${topset }, channel_id=${channel_id }, templet=${templet }, provenance=${provenance }, "
                        + "publish_time=${publish_time }, publisher=${publisher }, creator=${creator }, create_time=${create_time } ");
		sql.append("where article_id=${article_id }");
		return this.updateObject(sql.toString(), article);
	}
        
        /**
	 * 删除文章
	 * @param articleIds 文章主键列表
	 * @throws Exception 抛出全部异常
	 */
	public int deleteArticel(String articleIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from wzgl_article where article_id in (").append(SqlUtils.getArgsKey(articleIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(articleIds, ",").toArray());
	}
        
        /**
	 * 启用文章
	 * @param articleIds 文章主键列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int openArticle(String articleIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update wzgl_article set status='1' where article_id in (").append(SqlUtils.getArgsKey(articleIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(articleIds, ",").toArray());
	}

	/**
	 * 禁用文章
	 * @param articleIds 文章主键列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int closeArticle(String articleIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update wzgl_article set status='0' where article_id in (").append(SqlUtils.getArgsKey(articleIds, ",")).append(")");
		return this.update(sql.toString(), SqlUtils.getArgsValue(articleIds, ",").toArray());
	}
}
