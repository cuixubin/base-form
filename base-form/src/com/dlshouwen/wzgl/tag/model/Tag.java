/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.wzgl.tag.model;

import java.util.Date;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author admin
 */
public class Tag {

    //标签id
    private String tag_id;

    //标签名称
    @NotBlank(message = "标签名称不能为空")
    @Length(min = 1, max = 100, message = "标签名称长度必须在1-500字符之间")
    private String tag_name;

    //标签格式说明
    private String tag_styledescription;

    //标签路径
    private String tag_url;

    //标签添加时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tag_addtime;

    //标签添加人
    private String tag_adduser;

    //标签修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tag_edittime;

    //标签修改人
    private String tag_edituser;

    //标签状态
    private String tag_state;

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTag_styledescription() {
        return tag_styledescription;
    }

    public void setTag_styledescription(String tag_styledescription) {
        this.tag_styledescription = tag_styledescription;
    }

    public String getTag_adduser() {
        return tag_adduser;
    }

    public void setTag_adduser(String tag_adduser) {
        this.tag_adduser = tag_adduser;
    }

    public String getTag_edituser() {
        return tag_edituser;
    }

    public void setTag_edituser(String tag_edituser) {
        this.tag_edituser = tag_edituser;
    }

    public String getTag_state() {
        return tag_state;
    }

    public void setTag_state(String tag_state) {
        this.tag_state = tag_state;
    }

    public String getTag_url() {
        return tag_url;
    }

    public void setTag_url(String tag_url) {
        this.tag_url = tag_url;
    }

    public Date getTag_addtime() {
        return tag_addtime;
    }

    public void setTag_addtime(Date tag_addtime) {
        this.tag_addtime = tag_addtime;
    }

    public Date getTag_edittime() {
        return tag_edittime;
    }

    public void setTag_edittime(Date tag_edittime) {
        this.tag_edittime = tag_edittime;
    }
    
    
    
    
}
