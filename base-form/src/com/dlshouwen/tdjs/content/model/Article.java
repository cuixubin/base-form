/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.tdjs.content.model;

import java.util.Date;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 文章实体类
 *
 * @author cui
 */
public class Article {

    private String article_id;

    /**
     * 文章标题
     */
    @NotBlank(message = "文章标题不能为空")
    @Length(min = 2, max = 100, message = "文章标题长度必须在2-50字符之间")
    private String title;

    /**
     * 文章摘要
     */
    @Length(min = 0, max = 200, message = "文章摘要长度必须在0-200字符之间")
    private String tabloid;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章状态：启用-停用
     */
    @NotBlank(message = "文章状态不能为空")
    @Length(min = 0, max = 2, message = "文章状态长度必须在0-2之间")
    private String status;

    /**
     * 置顶字段
     */
    @Length(min = 0, max = 2, message = "文章置顶字段必须在0-2之间")
    private String topset;

    /**
     * 所属栏目
     */
    @NotBlank(message = "所属栏栏目不能为空")
    @Length(min = 0, max = 40, message = "所属栏栏目不能为空")
    private String channel_id;

    /**
     * 所用模板
     */
    private String templet;

    /**
     * 文章出处
     */
    private String provenance;

    /**
     * 文章发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publish_time;

    /**
     * 文章创建者
     */
    @Length(min = 0, max = 80, message = "文章创建者必须在0-80之间")
    private String creator;

    /**
     * 发布人姓名
     */
    @Length(min = 2, max = 20, message = "发布人姓名必须在2-20之间")
    @NotBlank(message = "文章发布人不能为空")
    private String publisher;

    /**
     * 文章创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * 文章修改者
     */
    @Length(min = 0, max = 40, message = "编辑人长度必须在0-40之间")
    private String editor;

    /**
     * 文章修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date edit_time;

     /**
     * 审核人姓名
     */
    @Length(min = 0, max = 20, message = "审核人姓名在2-20之间")
    private String reviewer;
    
    
      /**
     * 文章审核时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date review_time;
    
    /**
     * 文章类型：文章的类型 1 通过 2 待审批 3 拒绝 4 草稿
     */
    @Length(min = 0, max = 3, message = "文章类型长度必须在0-3之间")
    private String article_type;
    
    
    /**
     * 团队id
     * @return 
     */
    private String team_id;
    
    /**
     * 文章权限
     */
    private String article_limit;
    
    /**
     * 评审意见
     */
    private String check_comment;

    public String getCheck_comment() {
        return check_comment;
    }

    public void setCheck_comment(String check_comment) {
        this.check_comment = check_comment;
    }
    
    
    

    public String getArticle_limit() {
        return article_limit;
    }

    public void setArticle_limit(String article_limit) {
        this.article_limit = article_limit;
    }
    
    

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }
    
    
    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public Date getReview_time() {
        return review_time;
    }

    public void setReview_time(Date review_time) {
        this.review_time = review_time;
    }

    public String getArticle_type() {
        return article_type;
    }

    public void setArticle_type(String article_type) {
        this.article_type = article_type;
    }

    
    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTabloid() {
        return tabloid;
    }

    public void setTabloid(String tabloid) {
        this.tabloid = tabloid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTopset() {
        return topset;
    }

    public void setTopset(String topset) {
        this.topset = topset;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getTemplet() {
        return templet;
    }

    public void setTemplet(String templet) {
        this.templet = templet;
    }

    public String getProvenance() {
        return provenance;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public Date getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Date publish_time) {
        this.publish_time = publish_time;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Date getEdit_time() {
        return edit_time;
    }

    public void setEdit_time(Date edit_time) {
        this.edit_time = edit_time;
    }

//	===================================
//	附属字段
//	===================================
    /**
     * 创建人姓名
     */
    private String creator_name;
    /**
     * 编辑人姓名
     */
    private String editor_name;
    /**
     * 所属栏目名
     */
    private String channel_name;
    //团队名称
    private String team_name;

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }
    
    

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getEditor_name() {
        return editor_name;
    }

    public void setEditor_name(String editor_name) {
        this.editor_name = editor_name;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }
}
