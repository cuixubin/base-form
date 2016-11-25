/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.tdjs.channel.model;

import com.dlshouwen.core.base.Annotation.ExportDescrip;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author admin
 */
public class Channel {
 /**
     * 栏目id
     */
    private String channel_id ;
    /**
     * 栏目名称
     */
    private String channel_name ;
    /**
     * 栏目描述
     */
    private String channel_description ;
    /**
     * 栏目排序
     */
    private Integer channel_seq ;
    /**
     * 栏目父级栏目
     */
    private String channel_parent;
    /**
     * 栏目状态（1、启用 0、不启用）
     */
    private String channel_state ;
    /**
     * 栏目创建人
     */
    private String channel_createUser ;
    /**
     * 栏目创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date channel_createDate ;
    /**
     * 栏目更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date channel_updateDate ;
    /**
     * 栏目url
     */
    private String channel_url ;
    /**
     * 栏目类型（1：网站栏目;2:首页栏目；3：团队栏目）
     */
    private String channel_type ;
    
    private String team_id ;

    /**
     * 子集栏目
     */
    private List<Channel> children ;

    /**
     * 父级栏目
     */
    private Channel parentChannel ;
    
   
    

    /**
     * 栏目图片的ID
     */
    private String channel_picUrl ;

    private String channel_edituser ;

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    
   
    
    public String getChannel_edituser() {
        return channel_edituser;
    }

    public void setChannel_edituser(String channel_edituser) {
        this.channel_edituser = channel_edituser;
    }
    
    

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getChannel_description() {
        return channel_description;
    }

    public void setChannel_description(String channel_description) {
        this.channel_description = channel_description;
    }

    public Integer getChannel_seq() {
        return channel_seq;
    }

    public void setChannel_seq(Integer channel_seq) {
        this.channel_seq = channel_seq;
    }

    public String getChannel_parent() {
        return channel_parent;
    }

    public void setChannel_parent(String channel_parent) {
        this.channel_parent = channel_parent;
    }

    public String getChannel_state() {
        return channel_state;
    }

    public void setChannel_state(String channel_state) {
        this.channel_state = channel_state;
    }

    public String getChannel_createUser() {
        return channel_createUser;
    }

    public void setChannel_createUser(String channel_createUser) {
        this.channel_createUser = channel_createUser;
    }

    public Date getChannel_createDate() {
        return channel_createDate;
    }

    public void setChannel_createDate(Date channel_createDate) {
        this.channel_createDate = channel_createDate;
    }

    public Date getChannel_updateDate() {
        return channel_updateDate;
    }

    public void setChannel_updateDate(Date channel_updateDate) {
        this.channel_updateDate = channel_updateDate;
    }

    public String getChannel_url() {
        return channel_url;
    }

    public void setChannel_url(String channel_url) {
        this.channel_url = channel_url;
    }

    public String getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
    }

    public List<Channel> getChildren() {
        return children;
    }

    public void setChildren(List<Channel> children) {
        this.children = children;
    }

    public Channel getParentChannel() {
        return parentChannel;
    }

    public void setParentChannel(Channel parentChannel) {
        this.parentChannel = parentChannel;
    }

    public String getChannel_picUrl() {
        return channel_picUrl;
    }

    public void setChannel_picUrl(String channel_picUrl) {
        this.channel_picUrl = channel_picUrl;
    }


    //	===================================
//	附属字段
//	===================================
    private String creator_name;
    private String editor_name;
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

}
