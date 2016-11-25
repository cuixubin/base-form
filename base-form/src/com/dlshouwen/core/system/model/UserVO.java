/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.system.model;

import com.dlshouwen.core.base.utils.DateUtils;
import com.dlshouwen.jspc.zjpc.model.Expreview;
import org.apache.commons.lang.StringUtils;

/**
 * 用户的数据传输对象实体
 * @author cuixubin
 */
public class UserVO {
    /** 原始用户实体 */
    private User user;
    /** 专家评审结果实体 */
    private Expreview expreview;
    /** 工龄 */
    private String workYears;
    
    public UserVO() {}
    
    public UserVO(User user, Expreview expreview) {
        this.user = user;
        this.expreview = expreview;
        if(this.user != null && this.user.getWork_date() != null) {
            this.workYears = DateUtils.getYearMonthDayToNow(this.user.getWork_date());
        }
        if(StringUtils.isEmpty(this.workYears)) {
            this.workYears = "无";
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Expreview getExpreview() {
        return expreview;
    }

    public void setExpreview(Expreview expreview) {
        this.expreview = expreview;
    }
    
    public String getWorkYears() {
        return workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

}
