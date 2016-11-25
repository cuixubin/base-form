/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.team.model;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 团队实体
 *
 * @author cui
 */
public class Team {

    @Length(min = 0, max = 40, message = "团队编号长度必须在0-40之间")
    private String team_id;

    @NotBlank(message = "上级团队编号不能为空")
    @Length(min = 0, max = 40, message = "上级编号长度必须在0-40之间")
    private String pre_team_id;

    @NotBlank(message = "团队名称不能为空")
    @Length(min = 2, max = 40, message = "团队名称长度必须在2-40之间")
    private String team_name;

    @Length(min = 0, max = 40, message = "负责人长度必须在2-40之间")
    private String principal;

    @Length(min = 0, max = 20, message = "负责人电话长度必须在0-20之间")
    private String principal_phone;

    private int sort;

    @Length(min = 0, max = 200, message = "备注长度必须在0-200之间")
    private String remark;

    @Length(min = 0, max = 40, message = "创建人长度必须在0-40之间")
    private String creator;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    @Length(min = 0, max = 40, message = "编辑人长度必须在0-40之间")
    private String editor;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date edit_time;
    
    @Length(min=0, max=500, message="路径长度必须在0-500之间")
    private String team_path;

     
     
    public String getTeam_path() {
        return team_path;
    }

    public void setTeam_path(String team_path) {
        this.team_path = team_path;
    }
     
    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getPre_team_id() {
        return pre_team_id;
    }

    public void setPre_team_id(String pre_team_id) {
        this.pre_team_id = pre_team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPrincipal_phone() {
        return principal_phone;
    }

    public void setPrincipal_phone(String principal_phone) {
        this.principal_phone = principal_phone;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
    
//===================================
//	附属字段
//===================================
    /** 创建人姓名 */
    private String creator_name;
    /** 编辑人姓名 */
    private String editor_name;
    
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
