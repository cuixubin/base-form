/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.tdjs.album.model;

import com.dlshouwen.core.base.Annotation.Attached;
import java.util.Date;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 相册
 *
 * @author yangtong
 */
public class Album {

    private String album_id = null;
    @NotBlank(message = "相册名称不能为空")
    @Length(min = 0, max = 40, message = "相册名称长度必须在0-50之间")
    private String album_name= null;
    private String album_description= null;
    private Date album_createdate= null;
    private Date album_updatedate= null;
    private String album_createuser= null;
    private String album_coverpath= null;
    private String album_flag= null;
    private String album_createuserbyid= null;
    private int album_pic_total;
    
    //团队id
    private String team_id;

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }
    
    

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAlbum_description() {
        return album_description;
    }

    public void setAlbum_description(String album_description) {
        this.album_description = album_description;
    }

    public Date getAlbum_createdate() {
        return album_createdate;
    }

    public void setAlbum_createdate(Date album_createdate) {
        this.album_createdate = album_createdate;
    }

    public Date getAlbum_updatedate() {
        return album_updatedate;
    }

    public void setAlbum_updatedate(Date album_updatedate) {
        this.album_updatedate = album_updatedate;
    }

    public String getAlbum_createuser() {
        return album_createuser;
    }

    public void setAlbum_createuser(String album_createuser) {
        this.album_createuser = album_createuser;
    }

    public String getAlbum_coverpath() {
        return album_coverpath;
    }

    public void setAlbum_coverpath(String album_coverpath) {
        this.album_coverpath = album_coverpath;
    }

    public String getAlbum_flag() {
        return album_flag;
    }

    public void setAlbum_flag(String album_flag) {
        this.album_flag = album_flag;
    }

    public String getAlbum_createuserbyid() {
        return album_createuserbyid;
    }

    public void setAlbum_createuserbyid(String album_createuserbyid) {
        this.album_createuserbyid = album_createuserbyid;
    }

    public int getAlbum_pic_total() {
        return album_pic_total;
    }

    public void setAlbum_pic_total(int album_pic_total) {
        this.album_pic_total = album_pic_total;
    }
    
    //添加附属字段
    @Attached
    private String team_name;

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }
    
    
}
