/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.jspc.zjpc.model;

import java.util.Date;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 专家评测
 * @author xlli
 */
public class Expreview {
    
    
    @Length(min=0, max=40, message="评审内码长度必须在0-40之间")
    private String id ;
    
    @Length(min=0, max=40, message="教师编号长度必须在0-40之间")
    private String user_id ;
    
    @Length(min=0, max=40, message="教师姓名长度必须在0-40之间")
    private String user_name ;
    
    
    @Length(min=0, max=2, message="资质意向长度必须在0-2之间")
    private String qualified ;
    
    @Length(min=0, max=40, message="评审人名称长度必须在0-40之间")
    private String review_name ;
    
    @NotBlank(message="状态不能为空")
    @Length(min=0, max=2, message="状态长度必须在0-2之间")
    private String status ;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_date ;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date review_date ;
    
    @Length(min=0, max=200, message="备注长度必须在0-200之间")
    private String note ;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the create_date
     */
    public Date getCreate_date() {
        return create_date;
    }

    /**
     * @param create_date the create_date to set
     */
    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    /**
     * @return the review_date
     */
    public Date getReview_date() {
        return review_date;
    }

    /**
     * @param review_date the review_date to set
     */
    public void setReview_date(Date review_date) {
        this.review_date = review_date;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the review_name
     */
    public String getReview_name() {
        return review_name;
    }

    /**
     * @param review_name the review_name to set
     */
    public void setReview_name(String review_name) {
        this.review_name = review_name;
    }

    /**
     * @return the qualified
     */
    public String getQualified() {
        return qualified;
    }

    /**
     * @param qualified the qualified to set
     */
    public void setQualified(String qualified) {
        this.qualified = qualified;
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
