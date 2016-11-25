/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.wzgl.picture.model;

import java.util.Date;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 图片vo
 * @author xlli
 */
public class Picture {
    
    @Length(min=0, max=40, message="图片ID长度必须在0-40之间")
    private String picture_id ;
    
    @NotBlank(message="图片名称不能为空")
    @Length(min=0, max=100, message="图片名称长度必须在0-50之间")
    private String picture_name ;
    
    @Length(min=0, max=400, message="图片描述长度必须在0-200之间")
    private String description ;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date create_time ;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date update_time ;
    
    @Length(min=0, max=40, message="创建人编号长度必须在0-40之间")
    private String user_id ;
    
    @Length(min=0, max=40, message="创建人姓名长度必须在0-40之间")
    private String user_name ;
    
    @Length(min=0, max=100, message="图片名称长度必须在0-40之间")
    private String album_id ;
    
    @NotBlank(message="类型不能为空")
    @Length(min=0, max=2, message="类型长度必须在0-2之间")
    private String flag ;
    
    @NotBlank(message="请上传一张图片后保存")
    @Length(min=0, max=500, message="路径长度必须在0-500之间")
    private String path ;
    
   
    private int order ;
    
    @NotBlank(message="状态不能为空")
    @Length(min=0, max=2, message="是否展示长度必须在0-2之间")
    private String show ;
  

    /**
     * @return the picture_id
     */
    public String getPicture_id() {
        return picture_id;
    }

    /**
     * @param picture_id the picture_id to set
     */
    public void setPicture_id(String picture_id) {
        this.picture_id = picture_id;
    }

    /**
     * @return the picture_name
     */
    public String getPicture_name() {
        return picture_name;
    }

    /**
     * @param picture_name the picture_name to set
     */
    public void setPicture_name(String picture_name) {
        this.picture_name = picture_name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the create_time
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * @param create_time the create_time to set
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * @return the update_time
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * @param update_time the update_time to set
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * @return the user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * @return the show
     */
    public String getShow() {
        return show;
    }

    /**
     * @param show the show to set
     */
    public void setShow(String show) {
        this.show = show;
    }

    /**
     * @return the album_id
     */
    public String getAlbum_id() {
        return album_id;
    }

    /**
     * @param album_id the album_id to set
     */
    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    
    
}
