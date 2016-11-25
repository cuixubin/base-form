/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.team.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 团队成员中间表实体
 * @author cui
 */
public class TeamUser {
    @Length(min = 0, max = 40, message = "记录编号长度必须在0-40之间")
    private String team_user_id;

    @NotBlank(message = "团队编号不能为空")
    @Length(min = 0, max = 40, message = "团队编号长度必须在0-40之间")
    private String team_id;
    
    @NotBlank(message = "团队编号不能为空")
    @Length(min = 0, max = 40, message = "团队编号长度必须在0-40之间")
    private String user_id;
    
    @NotNull(message = "排序号不能为空")
    private int sort;

    public String getTeam_user_id() {
        return team_user_id;
    }

    public void setTeam_user_id(String team_user_id) {
        this.team_user_id = team_user_id;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
