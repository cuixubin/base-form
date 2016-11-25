/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.tdjs.content.dao;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.tdjs.content.model.Article;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 文章dao
 *
 * @author cui
 */
@Component("tdjsArticleDao")
public class ArticleDao extends BaseDao {

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
     * 获取文章列表
     *
     * @param pager 分页对象
     * @param request 请求对象
     * @param response 响应对象
     * @param selChannelId 所属栏目ID
     * @throws Exception 抛出全部异常
     */
    public void getArticleList(Pager pager, HttpServletRequest request, HttpServletResponse response, String selChannelId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select a.*, uc.user_name creator_name, ue.user_name editor_name, wc.channel_name channel_name, team.team_name team_name ");
        sql.append("from tdjs_article a ");
        sql.append("left join core_user uc on a.creator=uc.user_id ");
        sql.append("left join core_user ue on a.editor=ue.user_id ");
        sql.append("left join tdjs_channel wc on wc.channel_id=a.channel_id ");
        sql.append("left join core_team team  on team.team_id = a.team_id ");
        if(StringUtils.isNotEmpty(selChannelId)) {
            sql.append("where a.channel_id = '" + selChannelId + "' ");
        }
        sql.append("order by edit_time desc");
        GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
    }

    /**
     * 获取审核文章列表
     *
     * @param pager 分页对象
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 抛出全部异常
     */
    public void getCheckArticleList(Pager pager, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select a.*, uc.user_name creator_name, ue.user_name editor_name, wc.channel_name channel_name, team.team_name team_name ");
        sql.append("from tdjs_article a ");
        sql.append("left join core_user uc on a.creator=uc.user_id ");
        sql.append("left join core_user ue on a.editor=ue.user_id ");
        sql.append("left join tdjs_channel wc on wc.channel_id=a.channel_id ");
        sql.append("left join core_team team  on team.team_id = a.team_id ");
        sql.append("where a.article_type != 4 ");
        sql.append("order by edit_time desc");
        GridUtils.queryForGrid(this, sql.toString(), pager, request, response);
    }

    /**
     * 按条件获取文章列表
     *
     * @param pager 分页对象
     * @param request 请求对象
     * @param response 响应对象
     * @param ifManager 是否为管理者
     * @param teamId 团队id
     * @param userId 用户id
     * @param selChannelId 所属栏目ID
     * @throws Exception 抛出全部异常
     */
    public void getArticleList(Pager pager, HttpServletRequest request, HttpServletResponse response,
            boolean ifManager, String teamId, String userId, String selChannelId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select a.*, uc.user_name creator_name, ue.user_name editor_name, wc.channel_name channel_name, team.team_name team_name ");
        sql.append("from tdjs_article a ");
        sql.append("left join core_user uc on a.creator=uc.user_id ");
        sql.append("left join core_user ue on a.editor=ue.user_id ");
        sql.append("left join tdjs_channel wc on wc.channel_id=a.channel_id ");
        sql.append("left join core_team team  on team.team_id = a.team_id ");
        if (ifManager) {
            sql.append("where a.team_id=? ");
            if(StringUtils.isNotEmpty(selChannelId)) {
                sql.append("and a.channel_id ='" + selChannelId + "' ");
            }
            sql.append("order by edit_time desc");
            GridUtils.queryForGrid(this, sql.toString(), pager, request, response, teamId);
        } else {
            sql.append("where a.team_id=? and a.creator=? ");
            if(StringUtils.isNotEmpty(selChannelId)) {
                sql.append("and a.channel_id ='" + selChannelId + "' ");
            }
            sql.append("order by edit_time desc");
            GridUtils.queryForGrid(this, sql.toString(), pager, request, response, teamId, userId);
        }

    }

    /**
     * 按条件获取审核文章列表
     *
     * @param pager 分页对象
     * @param request 请求对象
     * @param response 响应对象
     * @param ifManager 是否为管理者
     * @param teamId 团队id
     * @param userId 用户id
     * @throws Exception 抛出全部异常
     */
    public void getCheckArticleList(Pager pager, HttpServletRequest request, HttpServletResponse response,
            boolean ifManager, String teamId, String userId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select a.*, uc.user_name creator_name, ue.user_name editor_name, wc.channel_name channel_name, team.team_name team_name ");
        sql.append("from tdjs_article a ");
        sql.append("left join core_user uc on a.creator=uc.user_id ");
        sql.append("left join core_user ue on a.editor=ue.user_id ");
        sql.append("left join tdjs_channel wc on wc.channel_id=a.channel_id ");
        sql.append("left join core_team team  on team.team_id = a.team_id ");
        if (ifManager) {
            sql.append("where a.team_id=? and a.article_type != 4 ");
            sql.append("order by edit_time desc");
            GridUtils.queryForGrid(this, sql.toString(), pager, request, response, teamId);
        } else {
            sql.append("where a.team_id=? and a.creator=? and a.article_type != 4 ");
            sql.append("order by edit_time desc");
            GridUtils.queryForGrid(this, sql.toString(), pager, request, response, teamId, userId);
        }

    }

    /**
     * 根据主键获取文章
     *
     * @param articleId 文章主键
     * @return 文章对象
     * @throws Exception 抛出全部异常
     */
    public Article getArticleById(String articleId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select a.*, uc.user_name creator_name, ue.user_name editor_name, wc.channel_name channel_name, team.team_name ");
        sql.append("from tdjs_article a ");
        sql.append("left join core_user uc on a.creator=uc.user_id ");
        sql.append("left join core_user ue on a.editor=ue.user_id ");
        sql.append("left join tdjs_channel wc on wc.channel_id=a.channel_id ");
        sql.append("left join core_team team  on team.team_id = a.team_id ");
        sql.append("where a.article_id=?");
        return this.queryForObject(sql.toString(), new ClassRowMapper<Article>(Article.class), articleId);
    }

    /**
     * 新增文章
     *
     * @param article 文章对象
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int insertArticle(Article article) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into tdjs_article ("
                + "article_id, title, tabloid, content, status, topset, channel_id, templet, "
                + "provenance, publish_time, publisher, creator, create_time, editor, edit_time, article_type, reviewer ,review_time,team_id,article_limit,check_comment ) ");
        sql.append("values ("
                + "${article_id }, ${title }, ${tabloid }, ${content }, ${status }, ${topset }, ${channel_id }, ${templet }, "
                + "${provenance }, ${publish_time }, ${publisher }, ${creator }, ${create_time }, ${editor }, ${edit_time }, ${article_type},${reviewer}, ${review_time},${team_id},${article_limit},${check_comment}  "
                + ")");
        return this.updateObject(sql.toString(), article);
    }

    /**
     * 更新文章
     *
     * @param article 文章对象
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int updateArticle(Article article) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_article set "
                + "title=${title }, content=${content }, status=${status }, editor=${editor }, edit_time=${edit_time }, team_id = ${team_id},article_limit=${article_limit}, "
                + "tabloid=${tabloid }, topset=${topset }, channel_id=${channel_id }, templet=${templet }, provenance=${provenance }, "
                + "publish_time=${publish_time }, publisher=${publisher }, creator=${creator }, create_time=${create_time },reviewer=${reviewer} ,review_time=${review_time},article_type=${article_type},check_comment=${check_comment} ");
        sql.append("where article_id=${article_id }");
        return this.updateObject(sql.toString(), article);
    }

    /**
     * 删除文章
     *
     * @param articleIds 文章主键列表
     * @throws Exception 抛出全部异常
     */
    public int deleteArticel(String articleIds) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from tdjs_article where article_id in (").append(SqlUtils.getArgsKey(articleIds, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(articleIds, ",").toArray());
    }

    /**
     * 启用文章
     *
     * @param articleIds 文章主键列表
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int openArticle(String articleIds) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_article set status='1' where article_id in (").append(SqlUtils.getArgsKey(articleIds, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(articleIds, ",").toArray());
    }

    /**
     * 禁用文章
     *
     * @param articleIds 文章主键列表
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int closeArticle(String articleIds) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_article set status='0' where article_id in (").append(SqlUtils.getArgsKey(articleIds, ",")).append(")");
        return this.update(sql.toString(), SqlUtils.getArgsValue(articleIds, ",").toArray());
    }

    /**
     * 文章审核通过
     *
     * @param articleType
     * @param articleId
     * @return
     */
    public int ArtTypePass(String reviewer, Date review_time, String articleId, String check_comment) {
        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_article set article_type ='1',reviewer = ? ,review_time =?,check_comment=? where article_id = ?");
        return this.update(sql.toString(), reviewer, review_time, check_comment, articleId);
    }

    //文章审核未通过
    public int ArtTypereRefuse(String reviewer, Date review_time, String articleId, String check_comment) {
        StringBuffer sql = new StringBuffer();
        sql.append("update tdjs_article set article_type = '3',reviewer = ? ,review_time =?,check_comment=?  where article_id = ?");
        return this.update(sql.toString(), reviewer, review_time, check_comment, articleId);
    }
    
}
