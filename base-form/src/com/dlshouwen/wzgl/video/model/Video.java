/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.wzgl.video.model;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 视频
 *
 * @author yangtong
 */
public class Video {

    private String vd_id;
    @NotBlank(message = "视频名称不能为空")
    @Length(min = 0, max = 40, message = "视频名称长度必须在0-50之间")
    private String vd_name;
    private String vd_description;
    private int vd_num;
    private int vd_like;
    private String vd_savepath;
    private String vd_type;
    private String vd_thumbnailspath;
    private String vd_tags;
    private String vd_uploaduser;
    private Date vd_uploaddate;
    private String vd_updateuser;
    private Date vd_updatedate;
    private String vd_status;
    private String vd_grade;
    private String vd_heat;
    private String vd_istop;
    private String vd_isdisplay;
    private String vd_time;
    @NotNull(message="排序号不能为空")
    private int vd_order;
    public String getVd_id() {
        return vd_id;
    }

    public void setVd_id(String vd_id) {
        this.vd_id = vd_id;
    }

    public String getVd_name() {
        return vd_name;
    }

    public void setVd_name(String vd_name) {
        this.vd_name = vd_name;
    }

    public String getVd_description() {
        return vd_description;
    }

    public void setVd_description(String vd_description) {
        this.vd_description = vd_description;
    }

    public int getVd_num() {
        return vd_num;
    }

    public void setVd_num(int vd_num) {
        this.vd_num = vd_num;
    }

    public int getVd_like() {
        return vd_like;
    }

    public void setVd_like(int vd_like) {
        this.vd_like = vd_like;
    }
    
    public String getVd_savepath() {
        return vd_savepath;
    }

    public void setVd_savepath(String vd_savepath) {
        this.vd_savepath = vd_savepath;
    }

    public String getVd_type() {
        return vd_type;
    }

    public void setVd_type(String vd_type) {
        this.vd_type = vd_type;
    }

    public String getVd_thumbnailspath() {
        return vd_thumbnailspath;
    }

    public void setVd_thumbnailspath(String vd_thumbnailspath) {
        this.vd_thumbnailspath = vd_thumbnailspath;
    }

    public String getVd_tags() {
        return vd_tags;
    }

    public void setVd_tags(String vd_tags) {
        this.vd_tags = vd_tags;
    }

    public String getVd_uploaduser() {
        return vd_uploaduser;
    }

    public void setVd_uploaduser(String vd_uploaduser) {
        this.vd_uploaduser = vd_uploaduser;
    }

    public Date getVd_uploaddate() {
        return vd_uploaddate;
    }

    public void setVd_uploaddate(Date vd_uploaddate) {
        this.vd_uploaddate = vd_uploaddate;
    }

    public String getVd_updateuser() {
        return vd_updateuser;
    }

    public void setVd_updateuser(String vd_updateuser) {
        this.vd_updateuser = vd_updateuser;
    }

    public Date getVd_updatedate() {
        return vd_updatedate;
    }

    public void setVd_updatedate(Date vd_updatedate) {
        this.vd_updatedate = vd_updatedate;
    }

    public String getVd_status() {
        return vd_status;
    }

    public void setVd_status(String vd_status) {
        this.vd_status = vd_status;
    }

    public String getVd_grade() {
        return vd_grade;
    }

    public void setVd_grade(String vd_grade) {
        this.vd_grade = vd_grade;
    }

    public String getVd_heat() {
        return vd_heat;
    }

    public void setVd_heat(String vd_heat) {
        this.vd_heat = vd_heat;
    }

    public String getVd_istop() {
        return vd_istop;
    }

    public void setVd_istop(String vd_istop) {
        this.vd_istop = vd_istop;
    }

    public String getVd_isdisplay() {
        return vd_isdisplay;
    }

    public void setVd_isdisplay(String vd_isdisplay) {
        this.vd_isdisplay = vd_isdisplay;
    }

    public String getVd_time() {
        return vd_time;
    }

    public void setVd_time(String vd_time) {
        this.vd_time = vd_time;
    }

    public int getVd_order() {
        return vd_order;
    }

    public void setVd_order(int vd_order) {
        this.vd_order = vd_order;
    } 
}
