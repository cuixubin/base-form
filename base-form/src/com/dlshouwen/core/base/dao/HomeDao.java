package com.dlshouwen.core.base.dao;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

/**
 * 框架首页
 *
 * @author 大连首闻科技有限公司
 * @version 2013-7-16 18:59:36
 */
@Component("homeDao")
public class HomeDao extends BaseDao {

    /**
     * 注入数据源
     *
     * @param dataSource 数据源对象
     */
    @Resource(name = "defaultDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
    //home页面上网站管理

    public List<Map<String, Object>> getContentArtForHome() {
        StringBuffer sql = new StringBuffer();
        sql.append("select  ta.album_coverpath , wza.* ");
        sql.append("from wzgl_article wza ");
        sql.append("left join wzgl_album ta on ta.album_id = wza.article_id ");
        sql.append("where  wza.`status` ='1' order by wza.create_time desc  ");
        return this.queryForList(sql.toString());
    }
    /**网站管理后台最新新闻展示
     * 
     * @param newsNumber 要显示的新闻条数
     * @return 
     */
    public List<Map<String, Object>> getContentArtForHome(String newsNumber) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT album.album_coverpath,article.* ");
        sql.append("FROM wzgl_article article LEFT JOIN wzgl_album album ");
        sql.append("ON article.article_id = album.album_id ");
        sql.append("WHERE album.album_coverpath != NULL OR album.album_coverpath != '' ");
        sql.append("ORDER BY article.topset DESC, article.publish_time DESC ");
        sql.append("LIMIT 0," + newsNumber);
        return this.queryForList(sql.toString());
    }
    //home 页面上团队建设栏目 下的文章

    public List<Map<String, Object>> getNewArtForHome() {
        StringBuffer sql = new StringBuffer();
        sql.append("select  ta.album_coverpath , tda.* ");
        sql.append("from tdjs_article tda ");
        sql.append("left join tdjs_album ta on ta.album_id = tda.article_id ");
        //权限公开 状态启用 审批状态 通过的5条信息
        sql.append("where tda.article_limit ='1' and tda.`status` ='1' and tda.article_type = '1' order by tda.review_time desc limit 0,5 ");
        return this.queryForList(sql.toString());
    }
}
